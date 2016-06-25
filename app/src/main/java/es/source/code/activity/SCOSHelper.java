package es.source.code.activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.util.ArrayList;
import java.util.List;

import es.source.code.R;

/**
 * Created by Hander on 16/6/25.
 * <p/>
 * Email : hander_wei@163.com
 */
public class SCOSHelper extends AppCompatActivity {

    private Context mContext;

    private Toolbar toolbar;
    private GridView gridView;

    private List<GridItem> items;

    private String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";

    private Intent sentIntent = new Intent(SENT_SMS_ACTION);
    private SmsManager smsManager;

    private PendingIntent sentPI;
    private Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
    private PendingIntent deliverPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scos_helper);

        mContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Help");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
    }

    private void init() {
        gridView = (GridView) findViewById(R.id.grid_helper);
        gridView.setDrawSelectorOnTop(true);
        items = new ArrayList<>();

        items.add(new GridItem("User Protocol", R.drawable.ic_protocol));
        items.add(new GridItem("About System", R.drawable.ic_system));
        items.add(new GridItem("Tel Help", R.drawable.ic_phone));
        items.add(new GridItem("SMS Help", R.drawable.ic_textsms));
        items.add(new GridItem("Email Help", R.drawable.ic_email));

        GridAdapter adapter = new GridAdapter(SCOSHelper.this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String itemName = items.get(position).name;
                switch (itemName) {
                    case "User Protocol":
                        //TODO
                        break;
                    case "About System":
                        //TODO
                        break;
                    case "Tel Help":
                        telHelp();
                        break;
                    case "SMS Help":
                        sendSms();
                        break;
                    case "Email Help":
                        sendEmail();
                        break;
                }
            }
        });
    }

    /**
     * 拨打电话
     */
    private void telHelp() {
        //拨打电话 5554
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "5554"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        startActivity(intent);
    }

    /**
     * 发送求助短信
     */
    private void sendSms() {
        smsManager = SmsManager.getDefault();
        sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(mContext, "Help SMS send success!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        deliverPI = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);
        this.registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //TODO
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));

        smsManager.sendTextMessage("5554", null, "test scos helper", sentPI, deliverPI);
    }

    /**
     * 发送求助邮件，在子线程中执行
     */
    private void sendEmail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BackgroundMail.newBuilder(mContext)
                        .withUsername("username@gmail.com")//Sender Email
                        .withPassword("password12345")
                        .withMailto("hander_wei@163.com")//Receiver Email
                        .withSubject("This is the subject")
                        .withBody("This is the body")
                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(mContext, "Email send success!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                            @Override
                            public void onFail() {
                                //TODO
                            }
                        })
                        .send();
            }
        }).start();
    }


    public class GridItem {
        String name;
        int icon;

        public GridItem(String name, int icon) {
            this.name = name;
            this.icon = icon;
        }
    }

    public class GridAdapter extends BaseAdapter {
        private Context context;

        public GridAdapter(Context context) {
//            this.items.clear();
            this.context = context;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView;
            if (convertView == null) {
                gridView = inflater.inflate(R.layout.item_grid, parent, false);
                TextView nameTv = (TextView) gridView.findViewById(R.id.grid_text);
                ImageView iconIv = (ImageView) gridView.findViewById(R.id.grid_image);
                GridItem item = items.get(position);
                nameTv.setText(item.name);
                iconIv.setImageResource(item.icon);
            } else {
                gridView = convertView;
            }
            return gridView;
        }
    }
}

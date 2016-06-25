package es.source.code.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.source.code.R;
import es.source.code.model.User;

/**
 * Created by Hander on 16/6/10.
 * <p/>
 * Email : hander_wei@163.com
 */
public class MainScreen extends AppCompatActivity {

    private static final String LOG_TAG = MainScreen.class.getSimpleName();

    private Toolbar toolbar;
    private GridView gridView;
    private boolean full = true;

    private List<GridItem> items;

    public static final String USER_NAME = "user_name";
    public static final String PASS_WORD = "password";
    public static final String IS_OLD = "is_old";

    public static final String PRFS_NAME = "SCOS";
    public static final String KEY_LOGIN_STATE = "loginState";

    private User user;

    /**
     * 判断登录状态
     *
     * @return 是否登录
     */
    private boolean isLogin() {
        SharedPreferences prfs = getSharedPreferences(PRFS_NAME, MODE_PRIVATE);
        int loginState = prfs.getInt(KEY_LOGIN_STATE, 0);
        return loginState == 1 ? true : false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (text != null) {
            if (text.equals("FromEntry")) {
                full = true;
                user = null;
                initGridView(full);
            } else if (text.equals("LoginSuccess")) {
                full = true;
                initGridView(full);
                if (intent.getStringExtra(USER_NAME) != null && intent.getStringExtra(PASS_WORD) != null) {
                    user = new User();
                    user.setOldUser(true);
                    user.setUserName(intent.getStringExtra(USER_NAME));
                    user.setPassword(intent.getStringExtra(PASS_WORD));
                }
            } else if (text.equals("RegisterSuccess")) {
                full = true;
                initGridView(full);
                if (intent.getStringExtra(USER_NAME) != null && intent.getStringExtra(PASS_WORD) != null) {
                    user = new User();
                    user.setOldUser(false);
                    user.setUserName(intent.getStringExtra(USER_NAME));
                    user.setPassword(intent.getStringExtra(PASS_WORD));
                    Toast.makeText(MainScreen.this, "Welcome for new user!" + user.getUserName(), Toast.LENGTH_LONG).show();
                }
            } else {
                full = false;
                user = null;
                initGridView(full);
            }
        }
    }

    private void initGridView(boolean full) {
        gridView = (GridView) findViewById(R.id.grid_main);
        gridView.setDrawSelectorOnTop(true);
        items = new ArrayList<>();
        if (full) {
            items.add(new GridItem("Order", R.drawable.ic_order));
            items.add(new GridItem("List", R.drawable.ic_order_list));
        }
        items.add(new GridItem("Login", R.drawable.ic_login_or_register));
        items.add(new GridItem("Help", R.drawable.ic_action_help));
        GridAdapter adapter = new GridAdapter(MainScreen.this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String itemName = items.get(position).name;
                //TODO Just for test
                if (user == null) {
                    user = new User();
                    user.setOldUser(true);
                    user.setUserName("Test");
                    user.setPassword("Test");
                }
                switch (itemName) {
                    case "Order":
                        if (user != null) {
                            Intent intent = new Intent(MainScreen.this, FoodView.class);
                            intent.putExtra(USER_NAME, user.getUserName());
                            intent.putExtra(PASS_WORD, user.getPassword());
                            intent.putExtra(IS_OLD, user.isOldUser());
                            startActivity(intent);
                        }
                        break;
                    case "List":
                        if (user != null) {
                            Intent intent = new Intent(MainScreen.this, FoodOrderView.class);
                            intent.putExtra(USER_NAME, user.getUserName());
                            intent.putExtra(PASS_WORD, user.getPassword());
                            intent.putExtra(IS_OLD, user.isOldUser());
                            startActivity(intent);
                        }
                        break;
                    case "Help": {
                        Intent intent = new Intent(MainScreen.this, SCOSHelper.class);
                        startActivity(intent);
                    }
                    break;
                    default:
                        Intent intent = new Intent(MainScreen.this, ContainerActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT, itemName);
                        startActivity(intent);
                        break;
                }
            }
        });

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

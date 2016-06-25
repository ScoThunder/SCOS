package es.source.code.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import es.source.code.R;

public class SCOSEntry extends AppCompatActivity {

    protected GestureDetector mGestureDetector;


    public static final String PRFS_NAME = "SCOS";
    public static final String KEY_LOGIN_STATE = "loginState";

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
        setContentView(R.layout.entry);

        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getRawX() - e2.getRawX() > 200) {
                    //跳转到MainScreen
                    Intent intent = new Intent(SCOSEntry.this, MainScreen.class);
                    intent.putExtra(Intent.EXTRA_TEXT, "FromEntry");
                    startActivity(intent);
                    overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}

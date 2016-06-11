package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import es.source.code.R;

public class SCOSEntry extends AppCompatActivity {

    protected GestureDetector mGestureDetector;

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

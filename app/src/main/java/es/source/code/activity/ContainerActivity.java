package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import es.source.code.R;
import es.source.code.fragment.HelpFragment;
import es.source.code.fragment.LoginOrRegister;
import es.source.code.fragment.OrderFragment;
import es.source.code.fragment.OrderListFragment;

/**
 * Created by Hander on 16/6/17.
 * <p/>
 * Email : hander_wei@163.com
 */
public class ContainerActivity extends AppCompatActivity implements LoginOrRegister.OnBtnClicked {

    private Toolbar toolbar;
    private String title;

    public static final String USER_NAME = "user_name";
    public static final String PASS_WORD = "password";
    public static final String IS_OLD = "is_old";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (title) {
            case "Order":
                ft.replace(R.id.container, new OrderFragment());
                break;
            case "List":
                ft.replace(R.id.container, new OrderListFragment());
                break;
            case "Login":
                ft.replace(R.id.container, new LoginOrRegister());
                break;
            case "Help":
                ft.replace(R.id.container, new HelpFragment());
                break;
        }

        ft.commit();

    }

    @Override
    public void onReturnBtnClicked(String msg) {
        Intent intent = new Intent(ContainerActivity.this, MainScreen.class);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(intent);
    }

    @Override
    public void onLoginBtnClicked(String msg, String userName, String password) {
        Intent intent = new Intent(ContainerActivity.this, MainScreen.class);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.putExtra(IS_OLD, true);
        intent.putExtra(USER_NAME, userName);
        intent.putExtra(PASS_WORD, password);
        startActivity(intent);
    }

    @Override
    public void onSignUpBtnClicked(String msg, String userName, String password) {
        Intent intent = new Intent(ContainerActivity.this, MainScreen.class);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.putExtra(IS_OLD, false);
        intent.putExtra(USER_NAME, userName);
        intent.putExtra(PASS_WORD, password);
        startActivity(intent);
    }
}

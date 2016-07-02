package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.R;
import es.source.code.fragment.ColdDishesFragment;
import es.source.code.fragment.DrinksFragment;
import es.source.code.fragment.HotDishesFragment;
import es.source.code.fragment.SeaFoodFragment;
import es.source.code.model.User;
import es.source.code.service.ServerObserverService;

/**
 * Created by Hander on 16/6/17.
 * <p/>
 * Email : hander_wei@163.com
 */
public class FoodView extends AppCompatActivity {

    private static final String LOG_TAG = FoodView.class.getSimpleName();

    public static final String USER_NAME = "user_name";
    public static final String PASS_WORD = "password";
    public static final String IS_OLD = "is_old";

    public static final String IS_ORDERED = "is_ordered";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private User user = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        Intent intent = getIntent();
        user.setUserName(intent.getStringExtra(USER_NAME));
        user.setPassword(intent.getStringExtra(PASS_WORD));
        user.setOldUser(intent.getBooleanExtra(IS_OLD, true));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ColdDishesFragment(), "Cold Dishes");
        adapter.addFragment(new HotDishesFragment(), "Hot Dishes");
        adapter.addFragment(new SeaFoodFragment(), "Sea Food");
        adapter.addFragment(new DrinksFragment(), "Drinks");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_food_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ordered:
                if (user != null) {
                    Intent intent = new Intent(FoodView.this, FoodOrderView.class);
                    intent.putExtra(USER_NAME, user.getUserName());
                    intent.putExtra(PASS_WORD, user.getPassword());
                    intent.putExtra(IS_OLD, user.isOldUser());
                    intent.putExtra(IS_ORDERED, true);
                    startActivity(intent);
                }
                break;
            case R.id.menu_check_list:
                if (user != null) {
                    Intent intent = new Intent(FoodView.this, FoodOrderView.class);
                    intent.putExtra(USER_NAME, user.getUserName());
                    intent.putExtra(PASS_WORD, user.getPassword());
                    intent.putExtra(IS_OLD, user.isOldUser());
                    intent.putExtra(IS_ORDERED, false);
                    startActivity(intent);
                }
                break;
            case R.id.menu_request_service:
                break;
            case R.id.menu_start_update:
                if (item.getTitle().equals(getResources().getString(R.string.menu_start_update))) {
                    //1）启动 ServerObserverService 服务，并向 ServerObserverService 发送
                    // 信息 Message 属性 what 值为 1
                    Intent intent = new Intent(FoodView.this, ServerObserverService.class);
                    intent.putExtra(Intent.EXTRA_TEXT, "1");
                    startService(intent);
                    //3）“启动实时更新”Action 被点击后，将 Action 标签修改为“停止实时更新”
                    item.setTitle(R.string.menu_stop_update);
                } else {
                    //4）当在 FoodView 界面中点击“停止实时更新”Action 时，向
                    // ServerObserverService 发送 Message 属性 what 值为 0
                    Intent intent = new Intent(FoodView.this, ServerObserverService.class);
                    intent.putExtra(Intent.EXTRA_TEXT, "0");
                    startService(intent);

                    item.setTitle(R.string.menu_start_update);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 2 ） 在 FoodView 中 新 建 sMessageHandler 对 象 ， 重 写 方 法
     * handleMessage()；当传入的 Message 属性 what 值为 10 时，解析该 Message
     * 携带菜品库存信息（菜名称，库存量），并根据该值，更新 FoodView 菜
     * 项信息
     */
    private Handler sMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    String foodName = (String) msg.obj;//菜名
                    int remain = msg.arg1;//库存
                    //TODO some magic code
                    break;
            }
        }
    };

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList;
        private List<String> mFragmentTitleList;
        private Map<Integer, String> mFragmentTags;

        private FragmentManager fm;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
            List<Fragment> fragments = fm.getFragments();
            if (fragments != null && fragments.size() > 0) {
                for (Fragment f : fragments) {
                    fm.beginTransaction().remove(f).commit();
                }
            }
            mFragmentList = new ArrayList<>();
            mFragmentTitleList = new ArrayList<>();
            mFragmentTags = new HashMap<>();
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = super.instantiateItem(container, position);
            if (object instanceof Fragment) {
                Fragment fragment = (Fragment) object;
                String tag = fragment.getTag();
                mFragmentTags.put(position, tag);
            }
            return object;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            String tag = mFragmentTags.get(position);
            if (tag != null) {
                fragment = fm.findFragmentByTag(tag);
            }
            if (fragment == null) {
                fragment = mFragmentList.get(position);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

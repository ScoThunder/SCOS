package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
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
        }
        return super.onOptionsItemSelected(item);
    }

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

package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.R;
import es.source.code.fragment.HelpFragment;
import es.source.code.fragment.LoginOrRegister;
import es.source.code.fragment.OrderFragment;
import es.source.code.fragment.OrderListFragment;

/**
 * Created by Hander on 16/6/10.
 * <p/>
 * Email : hander_wei@163.com
 */
public class MainScreen extends AppCompatActivity implements LoginOrRegister.OnBtnClicked {

    private static final String LOG_TAG = MainScreen.class.getSimpleName();

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.ic_order,
            R.drawable.ic_order_list,
            R.drawable.ic_login_or_register,
            R.drawable.ic_action_help
    };

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
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (text.equals("FromEntry")) {
            setFullTab();
        } else {
            setPartTab();
        }
    }

    private void setFullTab() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setPartTab() {
        setupViewPager2(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons2();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OrderFragment(), "Order");
        adapter.addFragment(new OrderListFragment(), "List");
        adapter.addFragment(new LoginOrRegister(), "Login");
        adapter.addFragment(new HelpFragment(), "Help");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPager2(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new LoginOrRegister(), "Login");
        adapter.addFragment(new HelpFragment(), "Help");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    private void setupTabIcons2() {
        tabLayout.getTabAt(0).setIcon(tabIcons[2]);
        tabLayout.getTabAt(1).setIcon(tabIcons[3]);
    }

    @Override
    public void onBtnClicked(String msg) {
        switch (msg) {
            case "LoginSuccess":
                setFullTab();
                break;
            case "LoginFailed":
                setPartTab();
                break;
            case "Return":
                setPartTab();
                break;
        }
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

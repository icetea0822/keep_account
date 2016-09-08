package com.shengzidong.keepacounts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.shengzidong.keepacounts.fragment.AccountFragment;
import com.shengzidong.keepacounts.fragment.CountFragment;
import com.shengzidong.keepacounts.fragment.SettingFragment;
import com.shengzidong.keepacounts.fragment.WatchFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    ViewPager vp;
    MyFragmentAdapter adapter;
    Button btn_account, btn_watch, btn_count, btn_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = (ViewPager) findViewById(R.id.vp);
        adapter = new MyFragmentAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);

        initViews();
    }

    private void initViews() {
        btn_account = (Button) findViewById(R.id.btn_account);
        btn_watch = (Button) findViewById(R.id.btn_watch);
        btn_count = (Button) findViewById(R.id.btn_count);
        btn_setting = (Button) findViewById(R.id.btn_setting);

        btn_account.setOnClickListener(this);
        btn_watch.setOnClickListener(this);
        btn_count.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position=0;
        switch (view.getId()){
            case R.id.btn_account:
                position=0;
                break;
            case R.id.btn_watch:
                position=1;
                break;
            case R.id.btn_count:
                position=2;
                break;
            case R.id.btn_setting:
                position=3;
                break;
        }
        vp.setCurrentItem(position);
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new AccountFragment();
                    break;
                case 1:
                    fragment = new WatchFragment();
                    break;
                case 2:
                    fragment = new CountFragment();
                    break;
                case 3:
                    fragment = new SettingFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}

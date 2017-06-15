package com.atguigu.shoppingmall.home.acyivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.shoppingmall.Community.fragment.CommunityFragment;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.ShoppingCart.fragment.ShoppingCartFragment;
import com.atguigu.shoppingmall.Type.fragment.TypeFragment;
import com.atguigu.shoppingmall.User.fragment.UserFragment;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.home.fragment.HomeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    private ArrayList<BaseFragment> fragments;
    private int position;

    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initFragment();

        initListener();
    }

    private void initListener() {

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }

                Fragment currentFragment = fragments.get(position);
                switchFragment(currentFragment);
            }
        });

        rgMain.check(R.id.rb_home);
    }

    private void switchFragment(Fragment currentFragment) {
        if (tempFragment != currentFragment) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!currentFragment.isAdded()) {
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }

                ft.add(R.id.fl_main, currentFragment);

            } else {
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }

                ft.show(currentFragment);
            }

            ft.commit();


            tempFragment = currentFragment;

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //得到id
        int  checkId = intent.getIntExtra("checkId",R.id.rb_home);
        switch (checkId){
            case R.id.rb_home:
                //切换到主页面
                rgMain.check(R.id.rb_home);
                break;
            case R.id.rb_cart:
                //切换到购物车
                rgMain.check(R.id.rb_cart);
                break;
        }
    }


    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }
}
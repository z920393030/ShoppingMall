package com.atguigu.shoppingmall.ShoppingCart.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shoppingmall.base.BaseFragment;


public class ShoppingCartFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();

        Log.e("TAG","购物车的数据被初始化了...");
        textView.setText("购物车内容");
    }
}

package com.atguigu.shoppingmall.Type.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shoppingmall.base.BaseFragment;


public class TypeFragment extends BaseFragment {
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
        Log.e("TAG","分类的数据被初始化了...");
        textView.setText("分类内容");
    }
}

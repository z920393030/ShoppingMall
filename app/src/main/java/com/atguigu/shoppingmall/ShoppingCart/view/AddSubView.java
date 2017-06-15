package com.atguigu.shoppingmall.ShoppingCart.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;

public class AddSubView extends LinearLayout implements View.OnClickListener {
    private final Context mContext;
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;

    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View.inflate(context, R.layout.add_sub_view,AddSubView.this);
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        tv_value = (TextView) findViewById(R.id.tv_value);

        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        if(attrs != null){
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.AddSubView);
            int value = tintTypedArray.getInt(R.styleable.AddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.AddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.AddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberAddBackground);
            if (addDrawable != null) {
                iv_add.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.AddSubView_numberSubBackground);
            if (subDrawable != null) {
                iv_sub.setImageDrawable(subDrawable);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_sub){
            subNumber();
        }else if(v.getId()==R.id.iv_add){
            addNumber();
        }
    }

    private void subNumber() {
        if(value > minValue){
            value--;
        }
        setValue(value);

        if(listener != null){
            listener.numberChange(value);
        }

    }

    private void addNumber() {
        if(value < maxValue){
            value++;
        }
        setValue(value);
        if(listener != null){
            listener.numberChange(value);
        }

    }

    public interface OnNumberChangeListener {
        public void numberChange(int value);
    }

    private OnNumberChangeListener listener;

    public void setOnNumberChangeListener(OnNumberChangeListener l){//l是MyOnNumberChangeListener的实例
        this.listener = l;
    }
}

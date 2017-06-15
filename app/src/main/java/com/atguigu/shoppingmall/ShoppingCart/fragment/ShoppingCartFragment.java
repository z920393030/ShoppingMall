package com.atguigu.shoppingmall.ShoppingCart.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.ShoppingCart.adapter.ShoppingCartAdapter;
import com.atguigu.shoppingmall.ShoppingCart.pay.PayResult;
import com.atguigu.shoppingmall.ShoppingCart.pay.SignUtils;
import com.atguigu.shoppingmall.ShoppingCart.utils.CartStorage;
import com.atguigu.shoppingmall.app.MyApplication;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.home.acyivity.MainActivity;
import com.atguigu.shoppingmall.home.bean.GoodsBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShoppingCartFragment extends BaseFragment {

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    @BindView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.checkbox_delete_all)
    CheckBox checkboxDeleteAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @BindView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;

    ShoppingCartAdapter adapter;
    private ArrayList<GoodsBean> datas;
    private static final  int ACTION_EDIT = 1;
    private static final  int ACTION_COMPLETE = 2;

    @Override
    public View initView() {
        Log.e(TAG, "购物车Fragment初始化ui了");
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        ButterKnife.bind(this, view);

        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int) tvShopcartEdit.getTag();
                if(action ==ACTION_EDIT){
                    showDelete();
                }else{
                    hideDelete();
                }

            }
        });


        return view;
    }

    private void hideDelete() {
        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_EDIT);
        llCheckAll.setVisibility(View.VISIBLE);
        llDelete.setVisibility(View.GONE);

        if(adapter != null){
            adapter.checkAll_none(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }


    }

    private void showDelete() {
        tvShopcartEdit.setText("完成");
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        llCheckAll.setVisibility(View.GONE);
        llDelete.setVisibility(View.VISIBLE);

        if(adapter != null){
            adapter.checkAll_none(false);
            adapter.checkAll();
        }

    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "购物车Fragment数据绑定了");
        ArrayList<GoodsBean> allData = (ArrayList<GoodsBean>) CartStorage.getInstance(MyApplication.getContext()).getAllData();
        for(int i = 0; i < allData.size(); i++){
            Log.e("TAG",""+allData.get(i).toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideDelete();
        datas = CartStorage.getInstance(mContext).getAllData();
        if(datas != null && datas.size() >0){
            adapter  = new ShoppingCartAdapter(mContext,datas,checkboxAll,tvShopcartTotal,checkboxDeleteAll);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            llEmptyShopcart.setVisibility(View.GONE);

            MyOnItemClickListener itemClickListener = new MyOnItemClickListener();
            adapter.setOnItemClickListener(itemClickListener);

            adapter.checkAll();
        }else{
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }

    class MyOnItemClickListener implements ShoppingCartAdapter.OnItemClickListener{

        @Override
        public void onItemClick(View view, int position) {

            GoodsBean  goodsBean = datas.get(position);
            boolean isChecked = goodsBean.isChecked();
            goodsBean.setChecked(!isChecked);

            adapter.showTotalPrice();
            adapter.notifyItemChanged(position);

            adapter.checkAll();

        }
    }


    @OnClick({R.id.tv_shopcart_edit, R.id.checkbox_all, R.id.btn_check_out, R.id.checkbox_delete_all, R.id.btn_delete, R.id.btn_collection, R.id.tv_empty_cart_tobuy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
                Toast.makeText(mContext, "编辑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_all:
                boolean isChecked = checkboxAll.isChecked();

                adapter.checkAll_none(isChecked);

                adapter.showTotalPrice();
                break;
            case R.id.btn_check_out:
                pay(view);
                break;
            case R.id.checkbox_delete_all:
                isChecked = checkboxDeleteAll.isChecked();

                adapter.checkAll_none(isChecked);

                adapter.showTotalPrice();
                break;
            case R.id.btn_delete:
                adapter.deleteData();
                adapter.checkAll();
                showEempty();
                break;
            case R.id.btn_collection:
                Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_empty_cart_tobuy:
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("checkedid",R.id.rb_home);
                startActivity(intent);
                break;
        }
    }


    // 商户PID
    public static final String PARTNER = "2088911876712776";
    // 商户收款账号
    public static final String SELLER = "chenlei@atguigu.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMpjX58B45FWljDZ\n" +
            "3MhEf/ZqJiYiXtGDwx8ne74jSSYztfdC67YKtnx6T0afJLNZxWY4MME3bwgJ366c\n" +
            "Ky2EypBI+uC50p8k0HfiMSjM0Uw5onQK+tpFr00cNJIU+MC1txS2cNGMnqa8jzUn\n" +
            "QMbQJ4nC/k5sZqG/hWJ31jyY1LUrAgMBAAECgYBQH0EmuN+3lPjGhClUm1GxYtqR\n" +
            "dD/nX+tqNP5XCq8V0ZjzN2oLWnheFSm3Qp+L5Tkxu4MJqwFoxEfqX2b0kUKDYYOj\n" +
            "VBLAQwYLb1otKyisIoNUyeihIW/Iuc0HDDEbuJLOyjp6fR3pZKzHBtbgbZKx/2sW\n" +
            "zqvVe8cXsow+bjGhkQJBAPicWMBTQjMBodH7eKBFdgGeXk5CVSKeCi7mjIzJd/QQ\n" +
            "+yM5bNa69gejOsao/9TO2+oLZ22WtW0APYNpLa4/XskCQQDQZ1MAWrQpU6HIrXf/\n" +
            "aoDJy/LLsd6KnONb6qR1P5NVpiVJcB8pbafmSOsi9FtgoVWQ6Kh68WP7zZBKtrdA\n" +
            "3CpTAkBvz8luvkNgs1Q3H3VyB6t6MOLTKawmhJOp4lDGJPpJD7YTcolLzgxHBRez\n" +
            "L2DYMizgBz7+H7D7FAeWaaB85M1ZAkBAaN63SlFKQIXM/wgKUcFSHQ2CNQwBrTF7\n" +
            "tA52Clsf6oS1qMiIxlJRExJRMFKZj+NhMPb5YGe1aDgDT2tdyW3vAkAP/SASgTwT\n" +
            "Dqem0cZenq/vcnuWEPldxssaJJwUXC2FgR9tEnrWYnhFY8f4VxKDxvuNnbUIiv3L\n" +
            "/coNQ57KGWRH";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKY1+fAeORVpYw2dzIRH/2aiYm\n" +
            "Il7Rg8MfJ3u+I0kmM7X3Quu2CrZ8ek9GnySzWcVmODDBN28ICd+unCsthMqQSPrg\n" +
            "udKfJNB34jEozNFMOaJ0CvraRa9NHDSSFPjAtbcUtnDRjJ6mvI81J0DG0CeJwv5O\n" +
            "bGahv4Vid9Y8mNS1KwIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };


    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
//                            finish();
                        }
                    }).show();
            return;
        }
        String orderInfo = getOrderInfo("硅谷商城", "硅谷商城商品的详细描述", adapter.getTotalPrice()+"");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //子线程
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    private void showEempty() {
        if(datas == null || datas.size()==0){
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }
}


package com.atguigu.shoppingmall.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.home.adapter.HomeAdapter;
import com.atguigu.shoppingmall.home.bean.HomeBean;
import com.atguigu.shoppingmall.utils.ConstantsUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.atguigu.shoppingmall.R.id.ib_top;


public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_search_home)
    TextView tvSearchHome;
    @BindView(R.id.tv_message_home)
    TextView tvMessageHome;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.ib_top)
    ImageButton ibTop;
    Unbinder unbinder;
    private HomeBean.ResultBean resultBean;
    private HomeAdapter adapter;

    @Override
    public View initView() {
        Log.e("TAG", "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.tv_search_home, R.id.tv_message_home, ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search_home:
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_message_home:
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
                break;
            case ib_top:
                rvHome.scrollToPosition(0);
                break;
        }
    }


    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "主页的数据被初始化了...");
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(ConstantsUtils.HOME_URL)
                .build()
                .execute(new MyStringCallback());
    }


    public class MyStringCallback extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "请求成功失败==" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e("TAG", "请求成功==");
            processData(response);

        }


    }

    private void processData(String json) {
        HomeBean homeBean = JSON.parseObject(json, HomeBean.class);
        Log.e("TAG", "解析成功了==" + homeBean.getResult().getAct_info().get(0).getName());

        adapter = new HomeAdapter(mContext,homeBean.getResult());
        rvHome.setAdapter(adapter);

        LinearLayoutManager liner = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvHome.setLayoutManager(liner);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.GoodsInfoActivity;
import com.atguigu.shoppingmall.home.acyivity.WebViewActivity;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.HomeBean;
import com.atguigu.shoppingmall.home.bean.WebViewBean;
import com.atguigu.shoppingmall.home.utils.GlideImageLoader;
import com.atguigu.shoppingmall.home.view.MyGridView;
import com.atguigu.shoppingmall.utils.ConstantsUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.RotateDownTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;


/**
 * Created by My on 2017/6/12.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    public static final String GOODS_BEAN = "goods_bean";
    public static final String WEBVIEW_BEAN = "webview_bean";
    private final Context context;
    private final HomeBean.ResultBean datas;

    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;

    /**
     * 当前类型
     */
    public int currentType = BANNER;

    private LayoutInflater inflate;


    public HomeAdapter(Context mContext, HomeBean.ResultBean datas) {
        this.context = mContext;
        this.datas = datas;
        inflate = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentType = BANNER;
        } else if (position == CHANNEL) {
            currentType = CHANNEL;
        } else if (position == ACT) {
            currentType = ACT;
        } else if (position == SECKILL) {
            currentType = SECKILL;
        } else if (position == RECOMMEND) {
            currentType = RECOMMEND;
        } else if (position == HOT) {
            currentType = HOT;
        }
        return currentType;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(context, inflate.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(context, inflate.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(context, inflate.inflate(R.layout.act_item, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(context, inflate.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(context, inflate.inflate(R.layout.recommend_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(context, inflate.inflate(R.layout.hot_item, null));
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(datas.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(datas.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(datas.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(datas.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(datas.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(datas.getHot_info());
        }

    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(final List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            List<String> images = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                images.add(ConstantsUtils.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }

            banner.setImages(images)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            if (position < banner_info.size()) {
                                String product_id = "";
                                String name = "";
                                String cover_price = "";
                                if (position == 0) {
                                    product_id = "627";
                                    cover_price = "32.00";
                                    name = "剑三T恤批发";
                                } else if (position == 1) {
                                    product_id = "21";
                                    cover_price = "8.00";
                                    name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
                                } else {
                                    product_id = "1341";
                                    cover_price = "50.00";
                                    name = "【蓝诺】《天下吾双》 剑网3同人本";
                                }
                                String image = banner_info.get(position).getImage();
                                GoodsBean goodsBean = new GoodsBean();
                                goodsBean.setName(name);
                                goodsBean.setCover_price(cover_price);
                                goodsBean.setFigure(image);
                                goodsBean.setProduct_id(product_id);

                                Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                                intent.putExtra(GOODS_BEAN, goodsBean);
                                mContext.startActivity(intent);
                            }
                        }
                    })
                    .start();
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private final Context mContext;
        private GridView gv;

        public ChannelViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gv = (GridView) itemView.findViewById(R.id.gv);

        }

        public void setData(final List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
            ChannelAdapter adapter = new ChannelAdapter(mContext, channel_info);
            gv.setAdapter(adapter);

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HomeBean.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
                    Toast.makeText(mContext, "" + channelInfoBean.getChannel_name(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class ActViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private ViewPager act_viewpager;

        public ActViewHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<HomeBean.ResultBean.ActInfoBean> act_info) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(mContext, act_info);
            act_viewpager.setAdapter(adapter);
            act_viewpager.setPageMargin(20);
            act_viewpager.setPageTransformer(true, new RotateDownTransformer());
            adapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    HomeBean.ResultBean.ActInfoBean actInfoBean = act_info.get(position);
                    WebViewBean webViewBean = new WebViewBean();
                    webViewBean.setName(actInfoBean.getName());
                    webViewBean.setIcon_url(actInfoBean.getIcon_url());
                    webViewBean.setUrl(ConstantsUtils.BASE_URL_IMAGE + actInfoBean.getUrl());

                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(WEBVIEW_BEAN, webViewBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private boolean isFrist = false;

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.countdownview)
        CountdownView countdownview;
        @BindView(R.id.tv_more_seckill)
        TextView tvMoreSeckill;
        @BindView(R.id.rv_seckill)
        RecyclerView rv;
        private Handler mHandler = new Handler();
        private final Context mContext;
        private HomeBean.ResultBean.SeckillInfoBean seckillInfo;

        public SeckillViewHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            ButterKnife.bind(this, itemView);

        }

        public void setData(final HomeBean.ResultBean.SeckillInfoBean seckill_info) {
            this.seckillInfo = seckill_info;
            SeckillViewAdapter adapter = new SeckillViewAdapter(mContext, seckill_info);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            adapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    HomeBean.ResultBean.SeckillInfoBean.ListBean infoBean = seckill_info.getList().get(position);
                    //传递数据
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(infoBean.getName());
                    goodsBean.setCover_price(infoBean.getCover_price());
                    goodsBean.setFigure(infoBean.getFigure());
                    goodsBean.setProduct_id(infoBean.getProduct_id());
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });

            if (!isFrist) {
                isFrist = true;
                long totalTime = Long.parseLong(seckillInfo.getEnd_time()) - Long.parseLong(seckillInfo.getStart_time());
                long curTime = System.currentTimeMillis();
                seckillInfo.setEnd_time((curTime + totalTime + ""));
                startRefreshTime();
            }


        }

        private void startRefreshTime() {

            mHandler.postDelayed(mRefreshTimeRunnable, 10);
        }

        Runnable mRefreshTimeRunnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                if (currentTime >= Long.parseLong(seckillInfo.getEnd_time())) {
                    mHandler.removeCallbacksAndMessages(null);
                } else {
                    countdownview.updateShow(Long.parseLong(seckillInfo.getEnd_time()) - currentTime);
                    mHandler.postDelayed(mRefreshTimeRunnable, 1000);
                }
            }
        };
    }


    private class RecommendViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private GridView gv;

        public RecommendViewHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            gv = (GridView) itemView.findViewById(R.id.gv_recommend);
        }


        public void setData(final List<HomeBean.ResultBean.RecommendInfoBean> recommend_info) {
            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(mContext, recommend_info);

            gv.setAdapter(adapter);

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HomeBean.ResultBean.RecommendInfoBean infoBean = recommend_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(infoBean.getName());
                    goodsBean.setCover_price(infoBean.getCover_price());
                    goodsBean.setFigure(infoBean.getFigure());
                    goodsBean.setProduct_id(infoBean.getProduct_id());
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private class HotViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private MyGridView gvhot;

        public HotViewHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            gvhot = (MyGridView) itemView.findViewById(R.id.gv_hot);
        }

        public void setData(final List<HomeBean.ResultBean.HotInfoBean> hot_info) {

            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext, hot_info);

            gvhot.setAdapter(adapter);
            gvhot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HomeBean.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);

                }
            });
        }
    }
}

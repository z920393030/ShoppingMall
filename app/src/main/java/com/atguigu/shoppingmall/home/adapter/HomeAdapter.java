package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
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
import com.atguigu.shoppingmall.home.bean.HomeBean;
import com.atguigu.shoppingmall.home.utils.GlideImageLoader;
import com.atguigu.shoppingmall.home.view.NoScrollGridView;
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

        public void setData(List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            List<String> images = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                images.add(ConstantsUtils.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }

            banner.setImages(images)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {

                            Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(mContext, "" + actInfoBean.getName(), Toast.LENGTH_SHORT).show();
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
            ButterKnife.bind(this,itemView);

        }

        public void setData(HomeBean.ResultBean.SeckillInfoBean seckill_info) {
            this.seckillInfo = seckill_info;
            SeckillViewAdapter adapter = new SeckillViewAdapter(mContext, seckill_info);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

            adapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(mContext, ""+recommend_info.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class HotViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private NoScrollGridView gvhot;

        public HotViewHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            gvhot = (NoScrollGridView) itemView.findViewById(R.id.gv_hot);
        }

        public void setData(final List<HomeBean.ResultBean.HotInfoBean> hot_info) {

            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext, hot_info);

            gvhot.setAdapter(adapter);
            gvhot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, ""+hot_info.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

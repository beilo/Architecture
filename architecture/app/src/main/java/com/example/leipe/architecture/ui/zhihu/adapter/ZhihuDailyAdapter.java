package com.example.leipe.architecture.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.leipe.architecture.R;
import com.example.leipe.architecture.model.bean.DailyListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 被咯苏州 on 2017/7/23.
 */

public class ZhihuDailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int item_layout = R.layout.item_daily;
    public final int DAILY_ITEM = 2;


    private List<DailyListBean.StoriesBean> mList;
    private List<DailyListBean.TopStoriesBean> mTopList;
    private Context mContext;
    private boolean isBefore = false;


    private ViewPager topViewPager;
    private TopPagerAdapter mAdapter;
    private DailyItemClickListener mClickListener;
    private TopPagerAdapter.TopItemClickListener mTopClickListener;

    public void setmClickListener(DailyItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public void setmTopClickListener(TopPagerAdapter.TopItemClickListener mTopClickListener) {
        this.mTopClickListener = mTopClickListener;
    }

    public ZhihuDailyAdapter(List<DailyListBean.StoriesBean> mList, List<DailyListBean.TopStoriesBean> mTopList, Context mContext) {
        if (mList == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = mList;
        }
        if (mList == null) {
            this.mTopList = new ArrayList<>();
        } else {
            this.mTopList = mTopList;
        }
        this.mContext = mContext;
    }

    public void setmList(DailyListBean bean) {
        if (bean.getStories() == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = bean.getStories();
        }
        if (bean.getTop_stories() == null) {
            this.mTopList = new ArrayList<>();
        } else {
            this.mTopList = bean.getTop_stories();
        }
        notifyDataSetChanged();
    }

    public enum ITEM_TYPE {
        ITEM_TOP,       //滚动栏
        ITEM_DATE,      //日期
        ITEM_CONTENT    //内容
    }


    @Override
    public int getItemViewType(int position) {
        if (!isBefore) {
            if (position == 0) {
                return ITEM_TYPE.ITEM_TOP.ordinal();
            } else if (position == 1) {
                return ITEM_TYPE.ITEM_DATE.ordinal();
            } else {
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
            }
        } else {
            if (position == 0) {
                return ITEM_TYPE.ITEM_TOP.ordinal();
            } else {
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(mContext);
        if (viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            mAdapter = new TopPagerAdapter(mContext, null);
            mAdapter.setmClickListener(new TopPagerAdapter.TopItemClickListener() {
                @Override
                public void onItemClick(int id, boolean isNotTransition) {
                    mTopClickListener.onItemClick(id, isNotTransition);
                }
            });
            return new TopViewHolder(from.inflate(R.layout.item_top, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_DATE.ordinal()) {
            return new DateViewHolder(from.inflate(R.layout.item_date, parent, false));
        }
        return new ContentViewHolder(from.inflate(R.layout.item_daily, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder) {
            final int contentPosition;
            if (isBefore) {
                contentPosition = position - 1;
            } else {
                contentPosition = position - 2;
            }
            ContentViewHolder viewHolder = (ContentViewHolder) holder;
            viewHolder.title.setText(mList.get(contentPosition).getTitle());
            Glide.with(mContext)
                    .load(mList.get(contentPosition).getImages().get(0))
                    .into(viewHolder.image);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(mList.get(contentPosition).getId());
                }
            });
        } else if (holder instanceof TopViewHolder) {
            mAdapter.setmList(mTopList);
            ((TopViewHolder) holder).vpTop.setAdapter(mAdapter);
            topViewPager = ((TopViewHolder) holder).vpTop;
        } else if (holder instanceof DateViewHolder) {
            ((DateViewHolder) holder).tvDate.setText("今日热闻");
        }
    }

    public void changeTopPager(int currentCount) {
        if (!isBefore && topViewPager != null) {
            topViewPager.setCurrentItem(currentCount);
        }
    }

    public interface DailyItemClickListener {
        void onItemClick(int id);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_daily_item_title)
        TextView title;
        @BindView(R.id.iv_daily_item_image)
        ImageView image;
        View itemView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_daily_date)
        TextView tvDate;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vp_top)
        ViewPager vpTop;
        @BindView(R.id.ll_point_container)
        LinearLayout llContainer;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

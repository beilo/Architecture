package com.example.leipe.architecture.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.leipe.architecture.R;
import com.example.leipe.architecture.model.bean.DailyListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 被咯苏州 on 2017/7/27.
 */

public class TopPagerAdapter extends PagerAdapter {
    public static final int TOP_ITEM = 1;

    private List<DailyListBean.TopStoriesBean> mList = new ArrayList<>();
    private Context mContext;
    private TopItemClickListener mClickListener;

    public void setmClickListener(TopItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public TopPagerAdapter(Context context, List<DailyListBean.TopStoriesBean> mList) {
        if (mList == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = mList;
        }
        this.mContext = context;
    }

    public void setmList(List<DailyListBean.TopStoriesBean> mList) {
        if (mList == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = mList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top_pager, container, false);
        ImageView ivImage = (ImageView) view.findViewById(R.id.iv_top_image);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_top_title);
        Glide.with(mContext).load(mList.get(position).getImage()).into(ivImage);
        tvTitle.setText(mList.get(position).getTitle());
        final int id = mList.get(position).getId();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(id, true);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface TopItemClickListener {
        void onItemClick(int id, boolean isNotTransition);
    }
}

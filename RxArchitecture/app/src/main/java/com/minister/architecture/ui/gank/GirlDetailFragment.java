package com.minister.architecture.ui.gank;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 美女图片详情
 * Created by leipe on 2017/9/14.
 */
public class GirlDetailFragment extends BaseSupportFragment {

    public static final String IT_GANK_GRIL_URL = "gank_girl_url";
    public static final String IT_GANK_GRIL_ID = "gank_girl_id";

    @BindView(R.id.iv_girl_detail)
    ImageView img_detail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static GirlDetailFragment newInstance(Bundle bundle){
        GirlDetailFragment fragment = new GirlDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        String imgUrl = getArguments().getString(IT_GANK_GRIL_URL);
        if (imgUrl != null) {
            Glide.with(_mActivity).load(imgUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    img_detail.setImageBitmap(resource);
                }
            });
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_girl_detail, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        setToolbar(toolbar,"图片详情");
        return inflate;
    }

    @OnClick(R.id.iv_girl_detail)
    public void onViewClicked() {
        onBackPressedSupport();
    }



    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }

}

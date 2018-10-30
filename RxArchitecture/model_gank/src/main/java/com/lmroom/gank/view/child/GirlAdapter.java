package com.lmroom.gank.view.child;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lmroom.baselib.base.BaseApplication;
import com.lmroom.baselib.widget.ScaleImageView;
import com.lmroom.gank.R;
import com.lmroom.gank.bean.GankItemBean;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leipe on 2017/9/14.
 */

public class GirlAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {


    public GirlAdapter(@LayoutRes int layoutResId, @Nullable List<GankItemBean> data) {
        super(layoutResId, data);
    }

    /**
     * 在StaggeredGridLayoutManager瀑布流中,当需要依据图片实际相对高度,不断动态设置ImageView的LayoutParams时,
     * 会导致快速滑动状态下产生重新排列,重写getItemViewType并设置StaggeredGridLayoutManager.GAP_HANDLING_NONE解决了这个问题，原因目前未知
     * https://github.com/oxoooo/mr-mantou-android/blob/master/app/src/main/java/ooo/oxo/mr/MainAdapter.java
     *
     * @param position
     * @return int
     */
    @Override
    protected int getDefItemViewType(int position) {
        return Math.round((float) BaseApplication.SCREEN_WIDTH / (float) mData.get(position).getHeight() * 10f);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GankItemBean item) {
        final ScaleImageView imageView = helper.getView(R.id.imageView);
        Glide
                .with(mContext)
                .load(item.getUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageCache.put(item.get_id(), new WeakReference<>(resource));
                        return false;
                    }
                })
                .into(imageView);
    }

    private HashMap<String, WeakReference<Bitmap>> imageCache = new HashMap<>();

    public Bitmap getImageCache(String key) {
        if (key == null || imageCache == null || imageCache.get(key) == null) {
            return null;
        }
        return imageCache.get(key).get();
    }
}

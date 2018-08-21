package com.lmroom.gank.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lmroom.baselib.base.BaseApplication;
import com.lmroom.baselib.util.ResourcesUtil;

/**
 * https://juejin.im/post/5a30fe5a6fb9a045132ab1bf
 */
public class GalleryItemDecoration extends RecyclerView.ItemDecoration {
    // 一页理论消耗距离
    public static int mItemConsumeY = 0;
    public static int mItemConmusemX = 0;
    // 每一个页面默认页边距
    int mPageMargin = 0;
    // 中间页面左右两边的页面可见部分宽度
    int mLeftPageVisibleWidth = 45;

    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int position = parent.getChildAdapterPosition(view);
        final int itemCount = parent.getAdapter().getItemCount();

        onSetHorizontalParams(parent, view, position, itemCount);
    }

    private void onSetHorizontalParams(RecyclerView parent, View itemView, int position, int itemCount) {
        int itemNewWidth = parent.getWidth() - dp2px(4 * mPageMargin + 2 * mLeftPageVisibleWidth);
        int itemNewHeight = parent.getHeight();
        mItemConmusemX = itemNewWidth + dp2px(2 * mPageMargin);
        int leftMargin = position == 0 ? dp2px(mLeftPageVisibleWidth + 2 * mPageMargin) : dp2px(mPageMargin);
        int rightMargin = position == itemCount - 1 ? dp2px(mLeftPageVisibleWidth + 2 * mPageMargin) : dp2px(mPageMargin);


        setLayoutParams(itemView, leftMargin, 0, rightMargin, 0, itemNewWidth, itemNewHeight);
    }

    private void setLayoutParams(View itemView, int left, int top, int right, int bottom, int itemWidth, int itemHeight) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        boolean mMarginChange = false;
        boolean mWidthChange = false;
        boolean mHeightChange = false;

        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            mMarginChange = true;
        }
        if (lp.width != itemWidth) {
            lp.width = itemWidth;
            mWidthChange = true;
        }
        if (lp.height != itemHeight) {
            lp.height = itemHeight;
            mHeightChange = true;
        }

        // 因为方法会不断调用，只有在真正变化了之后才调用
        if (mWidthChange || mMarginChange || mHeightChange) {
            itemView.setLayoutParams(lp);
        }
    }


    private int dp2px(float dipValue) {
        return ResourcesUtil.dp2px(BaseApplication.getInstance(), dipValue);
    }

    /**
     * 获取位置
     *
     * @param mConsumeX      实际消耗距离
     * @param shouldConsumeX 移动一页理论消耗距离
     * @return
     */
    private int getPosition(int mConsumeX, int shouldConsumeX) {
        float offset = (float) mConsumeX / (float) shouldConsumeX;
        int position = Math.round(offset);        // 四舍五入获取位置
        return position;
    }
}

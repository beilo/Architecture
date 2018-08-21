package com.lmroom.gank.view.child;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.commit451.nativestackblur.NativeStackBlur;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.baselib.eventbus.GankEvent;
import com.lmroom.gank.R;
import com.lmroom.gank.bean.GankItemBean;
import com.lmroom.gank.util.AnimManager;
import com.lmroom.gank.util.GalleryItemDecoration;
import com.lmroom.gank.util.GankRxHelp;
import com.lmroom.gank.viewmodel.GankViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;


/**
 * 美女图片列表
 * Created by leipe on 2017/9/14.
 */
@Route(path = "/gank/girl/list")
public class GirlListFragment extends BaseSupportFragment {

    protected LinearLayout llContent;
    private View _mView;
    RecyclerView mRecycler;
    SwipeRefreshLayout mRefresh;

    GankViewModel mGankViewModel;

    GirlAdapter mAdapter;
    LinearSnapHelper snapHelper = new LinearSnapHelper();

    private int mPosition = 0;
    private int mConsumeX = 0;
    AnimManager animManager = new AnimManager();
    private Map<String, Drawable> mTSDraCacheMap = new HashMap<>();
    private static final String KEY_PRE_DRAW = "key_pre_draw";
    /**
     * 获取虚化背景的位置
     */
    private int mLastDraPosition = -1;

    public static GirlListFragment newInstance() {
        GirlListFragment fragment = new GirlListFragment();
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getGirlData();
    }

    private void getGirlData() {
        mDisposable.add(
                mGankViewModel
                        .getGirlList()
                        .compose(GankRxHelp.<List<GankItemBean>>rxScheduler())
                        .subscribe(new Consumer<List<GankItemBean>>() {
                            @Override
                            public void accept(@NonNull final List<GankItemBean> gankItemBeen) throws Exception {
                                Glide.with(_mActivity)
                                        .load(gankItemBeen.get(0).getUrl())
                                        .asBitmap()
                                        .into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                                Bitmap process = NativeStackBlur.process(resource, 25);
                                                BitmapDrawable drawable = new BitmapDrawable(getResources(), process);
                                                llContent.setBackground(drawable);
                                                getData(gankItemBeen, true);
                                            }
                                        });
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                mRefresh.setRefreshing(false);
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                mRefresh.setRefreshing(false);
                            }
                        })
        );
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _mView = inflater.inflate(R.layout.gank_fragment_list, container, false);
        return _mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mGankViewModel = ViewModelProviders.of(this).get(GankViewModel.class);
    }

    private void initView() {
        mRecycler = _mView.findViewById(R.id.products_list);
        mRefresh = _mView.findViewById(R.id.refresh);
        llContent = _mView.findViewById(R.id.ll_content);

        mAdapter = new GirlAdapter(R.layout.gank_list_item_girl, new ArrayList<GankItemBean>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false);
        mRecycler.setLayoutManager(linearLayoutManager);
        snapHelper.attachToRecyclerView(mRecycler);

        mRecycler.addItemDecoration(new GalleryItemDecoration());


        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGirlData();
            }
        });

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onHorizontalScroll(recyclerView, dx);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    setBlurImage(false);
                }
            }
        });
        setBlurImage(false);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDisposable.add(
                                mGankViewModel
                                        .pullUpLoadGirl()
                                        .compose(GankRxHelp.<List<GankItemBean>>rxScheduler())
                                        .subscribe(new Consumer<List<GankItemBean>>() {
                                            @Override
                                            public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                                getData(gankItemBeen, false);
                                                mAdapter.loadMoreComplete();
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(@NonNull Throwable throwable) throws Exception {
                                                Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                        );
                    }
                }, 500);
            }
        }, mRecycler);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GankItemBean item = (GankItemBean) adapter.getItem(position);
                EventBusActivityScope.getDefault(_mActivity).post(new GankEvent.StartGirlDetailEvent(item.get_id(), item.getUrl()));
            }
        });
    }

    private void getData(final List<GankItemBean> gankItemBeanList, final boolean isRefresh) {
        final List<GankItemBean> newList = new ArrayList<>();
        Flowable
                .fromIterable(gankItemBeanList)
                .compose(GankRxHelp.<GankItemBean>rxScheduler())
                .subscribe(new Consumer<GankItemBean>() {
                    @Override
                    public void accept(@NonNull GankItemBean gankItemBeen) throws Exception {
                        newList.add(gankItemBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (isRefresh) {
                            mAdapter.replaceData(newList);
                        } else {
                            mAdapter.addData(newList);
                        }
                        setBlurImage(true);
                    }
                });
    }


    /**
     * 设置背景高斯模糊
     */
    private void setBlurImage(boolean forceUpdate) {
        GirlAdapter adapter = (GirlAdapter) mRecycler.getAdapter();
        final int mCurViewPosition = mPosition;

        boolean isSamePosAndNotUpdate = (mCurViewPosition == mLastDraPosition) && !forceUpdate;

        if (mCurViewPosition == -1 || adapter == null || mRecycler == null || isSamePosAndNotUpdate) {
            return;
        }

        if (mAdapter.getData().size() == 0 || mPosition > mAdapter.getData().size())
            return;

        mRecycler.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = mAdapter.getImageCache(mAdapter.getData().get(mPosition).get_id());
                if (bitmap == null)
                    return;
                Bitmap process = NativeStackBlur.process(bitmap, 25);
                BitmapDrawable drawable = new BitmapDrawable(getResources(), process);

                // 获取前一页的Drawable
                Drawable preBlurDrawable = mTSDraCacheMap.get(KEY_PRE_DRAW) == null ? drawable : mTSDraCacheMap.get(KEY_PRE_DRAW);

                /* 以下为淡入淡出效果 */
                Drawable[] drawableArr = {preBlurDrawable, drawable};
                TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
                llContent.setBackground(transitionDrawable);
                transitionDrawable.startTransition(500);

                // 存入到cache中
                mTSDraCacheMap.put(KEY_PRE_DRAW, drawable);
                // 记录上一次高斯模糊的位置
                mLastDraPosition = mCurViewPosition;
            }
        });
    }


    private void onHorizontalScroll(final RecyclerView recyclerView, int dx) {
        mConsumeX += dx;
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int shouldConsumeX = GalleryItemDecoration.mItemConmusemX;
                // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
                float offset = (float) mConsumeX / (float) shouldConsumeX;
                int round = Math.round(offset);
                // 获取当前页移动的百分值
                float percent = offset - ((int) offset);
                mPosition = round;
                animManager.setAnimation(recyclerView, (int) offset, percent);
            }
        });
    }
}

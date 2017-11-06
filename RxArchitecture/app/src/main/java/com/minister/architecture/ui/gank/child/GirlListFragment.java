package com.minister.architecture.ui.gank.child;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.minister.architecture.R;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.ui.gank.GankViewModel;
import com.minister.architecture.ui.gank.GirlDetailFragment;
import com.minister.architecture.util.RxHelp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.yokeyword.fragmentation.ISupportFragment;

import static com.minister.architecture.ui.gank.GirlDetailFragment.IT_GANK_GRIL_ID;
import static com.minister.architecture.ui.gank.GirlDetailFragment.IT_GANK_GRIL_URL;

/**
 * 美女图片列表
 * Created by leipe on 2017/9/14.
 */

public class GirlListFragment extends BaseSupportFragment {

    @BindView(R.id.products_list)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    GankViewModel mGankViewModel;

    GirlAdapter mAdapter;
    private OnStartGirlDetaiListener mOnStartGirlDetaiListener;

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
                        .compose(RxHelp.<List<GankItemBean>>rxScheduler())
                        .subscribe(new Consumer<List<GankItemBean>>() {
                            @Override
                            public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                setImageHeight(gankItemBeen, true);
                                mRefresh.setRefreshing(false);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                mRefresh.setRefreshing(false);
                            }
                        })
        );
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.list_fragment, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGankViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GankViewModel.class);
        main();
        creaI();
    }

    private void initView() {
        mAdapter = new GirlAdapter(R.layout.item_girl, new ArrayList<GankItemBean>());
        mAdapter.setFooterViewAsFlow(true);
        mAdapter.setHeaderViewAsFlow(true);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        //fix issue #52 https://github.com/codeestX/GeekNews/issues/52
        mStaggeredGridLayoutManager.setItemPrefetchEnabled(false);
        mRecycler.setLayoutManager(mStaggeredGridLayoutManager);
        mRecycler.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGirlData();
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDisposable.add(
                                mGankViewModel
                                        .pullUpLoadGirl()
                                        .compose(RxHelp.<List<GankItemBean>>rxScheduler())
                                        .subscribe(new Consumer<List<GankItemBean>>() {
                                            @Override
                                            public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                                setImageHeight(gankItemBeen, false);
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
                Bundle bundle = new Bundle();
                bundle.putString(IT_GANK_GRIL_URL, item.getUrl());
                bundle.putString(IT_GANK_GRIL_ID, item.get_id());
                GirlDetailFragment detailFragment = GirlDetailFragment.newInstance(bundle);

                setExitTransition(
                        TransitionInflater
                                .from(_mActivity)
                                .inflateTransition(android.R.transition.move)
                );
                detailFragment.setEnterTransition(
                        TransitionInflater
                                .from(_mActivity)
                                .inflateTransition(android.R.transition.move)
                );
                detailFragment.setSharedElementReturnTransition(
                        TransitionInflater
                                .from(_mActivity)
                                .inflateTransition(android.R.transition.move));
                detailFragment.setSharedElementEnterTransition(
                        TransitionInflater
                                .from(_mActivity)
                                .inflateTransition(android.R.transition.move));
                mOnStartGirlDetaiListener.onStartGirlDetail(adapter.getViewByPosition(position, R.id.imageView), detailFragment);


                // activity共享元素
                //                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(_mActivity, adapter.getViewByPosition(position, R.id.imageView), "shareView");
                //                Intent intent = new Intent(_mActivity, GirlDetailActivity.class);
                //                intent.putExtra(IT_GANK_GRIL_URL, item.getUrl());
                //                _mActivity.startActivity(intent, compat.toBundle());
            }
        });
    }

    public void setImageHeight(final List<GankItemBean> gankItemBeanList, final boolean isRefresh) {
        final List<GankItemBean> newList = new ArrayList<>();
        Flowable
                .fromIterable(gankItemBeanList)
                .map(new Function<GankItemBean, GankItemBean>() {
                    @Override
                    public GankItemBean apply(@NonNull GankItemBean gankItemBeen) throws Exception {
                        if (gankItemBeen.getHeight() == 0) {
                            Bitmap bitmap = Glide
                                    .with(_mActivity)
                                    .load(gankItemBeen.getUrl())
                                    .asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                            int bHeight = bitmap.getHeight();
                            int bWeight = bitmap.getWidth();
                            int realHeight = (int) ((float) (MyApp.SCREEN_WIDTH / 2) / bWeight * bHeight);
                            gankItemBeen.setHeight(realHeight);
                            bitmap.recycle();
                        }
                        return gankItemBeen;
                    }
                })
                .compose(RxHelp.<GankItemBean>rxScheduler())
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
                    }
                });
    }

    public interface OnStartGirlDetaiListener {
        void onStartGirlDetail(View view, ISupportFragment supportFragment);
    }

    public void setOnStartGirlDetaiListener(OnStartGirlDetaiListener mOnStartGirlDetaiListener) {
        this.mOnStartGirlDetaiListener = mOnStartGirlDetaiListener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mOnStartGirlDetaiListener = null;
    }

    private void creat(Integer v) {
        //        v = new Integer(1);
        ++v;
    }

    private boolean main() {
        //        Integer v = null;
        Integer v = 1;
        creat(v);
        return v == null ? false : true;
    }

    public int creaI() {
        int i = 0;
        try {
            i = 1;
            return i;
        } catch (Exception e) {
            i = 2;
            return i;
        } finally {
            i = 3;
        }
    }
}

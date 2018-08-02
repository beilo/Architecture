package com.lmroom.gank.view;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.baselib.util.ShareUtil;
import com.lmroom.baselib.util.SystemUtil;
import com.lmroom.gank.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * 美女图片详情
 * Created by leipe on 2017/9/14.
 */
@Route(path = "/gank/girl/detail")
public class GirlDetailFragment extends BaseSupportFragment {

    RxPermissions mRxPermissions;
    Bitmap mBitmap;

    private View _mView;
    ImageView img_detail;
    Toolbar toolbar;
    @Autowired(name = "gank_girl_id")
    String id;
    @Autowired(name = "gank_girl_url")
    String mImgUrl;

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (mImgUrl != null) {
            Glide.with(_mActivity).load(mImgUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    img_detail.setImageBitmap(resource);
                    mBitmap = resource;
                }
            });
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.gank_menu_girl, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _mView = inflater.inflate(R.layout.gank_fragment_girl_detail, container, false);
        ARouter.getInstance().inject(this);
        return _mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        img_detail = _mView.findViewById(R.id.iv_girl_detail);
        toolbar = _mView.findViewById(R.id.toolbar);

        img_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        setToolbar(toolbar, "图片详情");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        toolbar.inflateMenu(R.menu.gank_menu_girl); // http://wuxiaolong.me/2015/12/21/fragmentToolbar/
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.gank_action_share) {
                    checkPermissionAndAction(R.id.gank_action_share);
                } else if (i == R.id.gank_action_save) {
                    checkPermissionAndAction(R.id.gank_action_save);
                }
                return true;
            }
        });
    }

    @Override
    protected int setTitleBar() {
        return R.id.toolbar;
    }

    private void checkPermissionAndAction(final int action) {
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(_mActivity);
        }
        // http://blog.csdn.net/u013553529/article/details/68948971
        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull Permission permission) throws Exception {
                        if (permission.granted) { // 用户已经同意该权限
                            if (action == R.id.gank_action_share) {
                                ShareUtil.shareImage(_mActivity, SystemUtil.saveBitmapToFile(_mActivity, mImgUrl, mBitmap, true), "图片分享");
                            } else if (action == R.id.gank_action_save) {
                                SystemUtil.saveBitmapToFile(_mActivity, mImgUrl, mBitmap, false);
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) { // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Toast.makeText(_mActivity, "没有选中『不再询问』", Toast.LENGTH_SHORT).show();
                        } else { // 用户拒绝了该权限，并且选中『不再询问』
                            Toast.makeText(_mActivity, "『不再询问』", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

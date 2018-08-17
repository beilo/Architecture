package com.minister.architecture.ui.gank;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.util.ShareUtil;
import com.minister.architecture.util.SystemUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 美女图片详情
 * Created by leipe on 2017/9/14.
 */
public class GirlDetailFragment extends BaseSupportFragment {

    public static final String IT_GANK_GRIL_URL = "gank_girl_url";
    public static final String IT_GANK_GRIL_ID = "gank_girl_id";

    RxPermissions mRxPermissions;
    String mImgUrl;
    Bitmap mBitmap;

    @BindView(R.id.iv_girl_detail)
    ImageView img_detail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static GirlDetailFragment newInstance(Bundle bundle) {
        GirlDetailFragment fragment = new GirlDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mImgUrl = getArguments().getString(IT_GANK_GRIL_URL);
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
        inflater.inflate(R.menu.girl_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_girl_detail, container, false);
        unbinder = ButterKnife.bind(this, inflate);

        setToolbar(toolbar, "图片详情");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        toolbar.inflateMenu(R.menu.girl_menu); // http://wuxiaolong.me/2015/12/21/fragmentToolbar/
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_share: // 分享
                        checkPermissionAndAction(R.id.action_share);
                        break;
                    case R.id.action_save: // 保存
                        checkPermissionAndAction(R.id.action_save);
                        break;
                }
                return true;
            }
        });

        return inflate;
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
                            if (action == R.id.action_share){
                                ShareUtil.shareImage(_mActivity, SystemUtil.saveBitmapToFile(_mActivity, mImgUrl, mBitmap, true), "图片分享");
                            }else if (action == R.id.action_save){
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

    @OnClick(R.id.iv_girl_detail)
    public void onViewClicked() {
        pop();
    }
}

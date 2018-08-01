package com.lmroom.gank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.lmroom.baselib.base.BaseActivity;
import com.lmroom.gank.R;

/** 查看美女大图
 * Created by leipe on 2017/9/14.
 */

@Route(path = "/gank/girl/detail/ac")
public class GirlDetailActivity extends BaseActivity {
    ImageView ivGirlDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gank_fragment_girl_detail);
        ivGirlDetail = findViewById(R.id.iv_girl_detail);
        Intent intent = getIntent();
        final String url = intent.getStringExtra(GirlDetailFragment.IT_GANK_GRIL_URL);
        if (url != null) {
            Glide.with(this).load(url).asBitmap().into(ivGirlDetail);
        }
    }
}

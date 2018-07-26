package com.minister.architecture.ui.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.minister.architecture.R;
import com.minister.architecture.base.BaseActivity;

import static com.minister.architecture.ui.gank.GirlDetailFragment.IT_GANK_GRIL_URL;

/** 查看美女大图
 * Created by leipe on 2017/9/14.
 */


public class GirlDetailActivity extends BaseActivity {
    ImageView ivGirlDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_girl_detail);
        ivGirlDetail = findViewById(R.id.iv_girl_detail);
        Intent intent = getIntent();
        final String url = intent.getStringExtra(IT_GANK_GRIL_URL);
        if (url != null) {
            Glide.with(this).load(url).asBitmap().into(ivGirlDetail);
        }
    }
}

package com.lmroom.baselib.base;

import me.yokeyword.fragmentation.SupportActivity;

/** 基础AC
 * Created by leipe on 2017/9/18.
 */
public class BaseActivity extends SupportActivity {
    protected final String TAG = this.getClass().getSimpleName();



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

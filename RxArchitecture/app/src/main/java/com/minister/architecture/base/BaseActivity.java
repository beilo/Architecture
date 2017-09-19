package com.minister.architecture.base;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/** 基础AC
 * Created by leipe on 2017/9/18.
 */

public class BaseActivity extends SupportActivity {
    final String TAG = this.getClass().getSimpleName();
    protected Unbinder unbinder;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}

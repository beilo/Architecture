package debug;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lmroom.baselib.base.BaseSupportActivity;
import com.lmroom.zhihu.R;

@Route(path = "/zhihu/home")
public class HomeActivity extends BaseSupportActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_activity_main);

        if (findFragment(ZhiHuMainFragment.class) == null){
            loadRootFragment(R.id.fl_container,ZhiHuMainFragment.newInstance());
        }
    }
}

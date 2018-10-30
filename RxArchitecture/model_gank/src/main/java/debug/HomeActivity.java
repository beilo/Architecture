package debug;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lmroom.baselib.base.BaseSupportActivity;
import com.lmroom.gank.R;

@Route(path = "/gank/home")
public class HomeActivity extends BaseSupportActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gank_activity_main);

        if (findFragment(GankMainFragment.class) == null){
            loadRootFragment(R.id.fl_container, GankMainFragment.newInstance());
        }
    }
}

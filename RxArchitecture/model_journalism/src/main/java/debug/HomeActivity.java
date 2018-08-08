package debug;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lmroom.baselib.base.BaseSupportActivity;
import com.lmroom.journalism.R;

@Route(path = "/journalism/home")
public class HomeActivity extends BaseSupportActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journalism_activity_main);

        if (findFragment(JournalismMainFragment.class) == null){
            loadRootFragment(R.id.fl_container, JournalismMainFragment.newInstance());
        }
    }
}

package debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.gank.R;
import com.lmroom.gank.event.GankEvent;
import com.lmroom.gank.view.GankTabFragment;
import com.lmroom.gank.view.GirlDetailFragment;
import com.lmroom.gank.view.TechDetailFragment;

import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

@Route(path = "/gank/main")
public class GankMainFragment extends BaseSupportFragment {

    public static GankMainFragment newInstance() {
        Bundle args = new Bundle();
        GankMainFragment fragment = new GankMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.gank_activity_main, container, false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findFragment(GankTabFragment.class) == null) {
            loadRootFragment(R.id.fl_container, GankTabFragment.newInstance());
        }
    }

    @Override
    public void onDestroy() {
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void startDailyDetailFragment(GankEvent.StartTechDetailEvent event) { // 第二种:子fg和父fg通信的方式
        start(TechDetailFragment.newInstance(event.getUrl()));
    }

    @Subscribe
    public void startGirlDetailFragment(GankEvent.StartGirlDetailEvent event) {
        start(GirlDetailFragment.newInstance(event.getId(), event.getUrl()));
    }

}

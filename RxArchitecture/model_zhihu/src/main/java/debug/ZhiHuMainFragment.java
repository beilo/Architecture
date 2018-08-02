package debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.zhihu.R;
import com.lmroom.baselib.eventbus.ZhiHuEvent;
import com.lmroom.zhihu.view.ZhiHuTabFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.ISupportFragment;

@Route(path = "/zhihu/main")
public class ZhiHuMainFragment extends BaseSupportFragment {

    public static ZhiHuMainFragment newInstance() {
        Bundle args = new Bundle();
        ZhiHuMainFragment fragment = new ZhiHuMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView  = inflater.inflate(R.layout.zhihu_activity_main,container,false);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findFragment(ZhiHuTabFragment.class) == null){
            loadRootFragment(R.id.fl_container,ZhiHuTabFragment.newInstance());
        }
    }

    @Override
    public void onDestroy() {
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startDailyDetailFragment(ZhiHuEvent.StartDetailEvent event) { // 第二种:子fg和父fg通信的方式
        Object dailyDetail = ARouter.getInstance()
                .build("/zhihu/detail")
                .withInt("id", event.getId())
                .navigation();
        start((ISupportFragment) dailyDetail);
    }

}

package debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.journalism.R;
import com.lmroom.journalism.view.JournalismTabFragment;

@Route(path = "/journalism/main")
public class JournalismMainFragment extends BaseSupportFragment {

    public static JournalismMainFragment newInstance() {
        Bundle args = new Bundle();
        JournalismMainFragment fragment = new JournalismMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.journalism_activity_main, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findFragment(JournalismTabFragment.class) == null) {
            loadRootFragment(R.id.fl_container, JournalismTabFragment.newInstance());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

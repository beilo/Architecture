package com.minister.architecture.ui.weather;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.minister.architecture.R;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.model.bean.AlarmClock;
import com.minister.architecture.util.GsonUtil;
import com.minister.architecture.util.KeyboardUtil;
import com.minister.architecture.util.MyUtil;
import com.minister.architecture.util.ResourcesUtil;
import com.minister.architecture.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by leipe on 2018/2/8.
 */

public class AddWeatherSettingDialogFragment extends DialogFragment {

    @BindView(R.id.et_hours)
    EditText etHours;
    @BindView(R.id.et_min)
    EditText etMin;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;

    private Activity _mActivity;

    public static AddWeatherSettingDialogFragment newInstance() {

        Bundle args = new Bundle();

        AddWeatherSettingDialogFragment fragment = new AddWeatherSettingDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_add_weather_setting, container, false);
        unbinder = ButterKnife.bind(this, inflate);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hours = etHours.getText().toString();
                String min = etMin.getText().toString();
                hours = "".equals(hours) ? etHours.getHint().toString() : hours;
                min = "".equals(min) ? etMin.getHint().toString() : min;

                AlarmClock clock = new AlarmClock();
                clock.setId(1);
                clock.setHour(Integer.valueOf(hours));
                clock.setMinute(Integer.valueOf(min));
                clock.setWeeks("2,3,4,5,6,7,1");

                addSP(clock);
                MyUtil.startAlarmClock(_mActivity, clock);
                Toast.makeText(_mActivity, "已设定好时间为" + hours + "时" + min + "分", Toast.LENGTH_SHORT).show();
                KeyboardUtil.hideSoftInput(_mActivity);
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = MyApp.SCREEN_WIDTH;
            lp.height = ResourcesUtil.dp2px(_mActivity, 300);
            window.setAttributes(lp);
        }
    }

    private void addSP(AlarmClock clock) {
        String stringClocks = SPUtil.init().getString(SPUtil.ALARM_CLOCKS);
        List<AlarmClock> clocks = GsonUtil.GsonToList(stringClocks, AlarmClock.class);
        if (clocks == null) {
            clocks = new ArrayList<>();
        }
        clocks.add(clock);
        String string = GsonUtil.GsonString(clocks);
        SPUtil.init().removeKey(SPUtil.ALARM_CLOCKS);
        SPUtil.init().put(SPUtil.ALARM_CLOCKS, string);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        if (this.isAdded()) {
            ft.remove(this).commit();
        }
        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
    }
}

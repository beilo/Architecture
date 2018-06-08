package com.minister.architecture.ui.weather;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.minister.architecture.R;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.model.bean.AlarmClock;
import com.minister.architecture.util.ACache;
import com.minister.architecture.util.GsonUtil;
import com.minister.architecture.util.MyUtil;
import com.minister.architecture.util.ResourcesUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.minister.architecture.ui.weather.WeacConstants.ALARM_CLOCK;

/**
 * Created by leipe on 2018/2/8.
 */

public class AddWeatherSettingDialogFragment extends DialogFragment {

    private static final String TAG = "AddWeatherSetting";

    Unbinder unbinder;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.btn_add)
    Button btnAdd;
    private TimePickerDialog mTimePickerDialog;

    private Activity _mActivity;
    private View mView;
    private ACache mACache;
    private AlarmClockAdapter mAdapter;


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
        mView = inflater.inflate(R.layout.dialog_add_weather_setting, container, false);
        unbinder = ButterKnife.bind(this, mView);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new AlarmClockAdapter(R.layout.dialog_add_weather_setting_item);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recycler);
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);

        recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        recycler.setAdapter(mAdapter);

        mACache = ACache.get(_mActivity);
        String jsonString = mACache.getAsString(ALARM_CLOCK);
        List<AlarmClock> clocks = GsonUtil.GsonToList(jsonString, AlarmClock.class);
        mAdapter.replaceData(clocks);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = MyApp.SCREEN_WIDTH;
            lp.height = ResourcesUtil.dp2px(_mActivity, 350);
            window.setAttributes(lp);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            updateACache();
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
        }
    };


    private List<AlarmClock> getAlarmClocks(){
        return mAdapter.getData();
    }

    /**
     * 更新ALARM_CLOCK本地缓存
     */
    private void updateACache() {
        mACache.remove(ALARM_CLOCK);
        mACache.put(ALARM_CLOCK, GsonUtil.GsonString(getAlarmClocks()));
        Log.d(TAG, "updateACache: " + getAlarmClocks());
    }

    /**
     * 获取TimePickerDialog的实例
     *
     * @return TimePickerDialog
     */
    private TimePickerDialog getTimePickerDialog(TimePickerDialog.OnTimeSetListener listener) {
        if (mTimePickerDialog == null) {
            int hour = 0;
            int min = 0;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(System.currentTimeMillis()));
            hour = calendar.get(Calendar.HOUR);
            min = calendar.get(Calendar.MINUTE);
            mTimePickerDialog = new TimePickerDialog(_mActivity, listener, hour, min, true);
        }
        return mTimePickerDialog;
    }

    public void show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        if (this.isAdded()) {
            ft.remove(this).commit();
        }
        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        getTimePickerDialog(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                AlarmClock clock = new AlarmClock();
                clock.setId(getAlarmClocks().size());
                clock.setHour(hourOfDay);
                clock.setMinute(minute);
                clock.setWeeks("2,3,4,5,6,7,1");

                mAdapter.addData(clock);
                updateACache();

                MyUtil.startAlarmClock(_mActivity, clock);
                Toast.makeText(_mActivity, "已设定好时间为" + hourOfDay + "时" + minute + "分", Toast.LENGTH_SHORT).show();
                mTimePickerDialog.dismiss();
            }
        }).show();
    }
}

package com.minister.architecture.ui.weather;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.minister.architecture.R;
import com.minister.architecture.model.bean.AlarmClock;

import java.util.ArrayList;

/**
 * Created by leipe on 2018/3/14.
 */

public class AlarmClockAdapter extends BaseItemDraggableAdapter<AlarmClock, BaseViewHolder> {

    public AlarmClockAdapter(int layoutResId) {
        super(layoutResId, new ArrayList<AlarmClock>());
    }

    @Override
    protected void convert(BaseViewHolder helper, AlarmClock item) {
        String timeString = item.getHour() + ": " + item.getMinute();
        helper.
                setText(R.id.tv_time, timeString);
    }
}

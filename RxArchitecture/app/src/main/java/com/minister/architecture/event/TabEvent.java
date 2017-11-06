package com.minister.architecture.event;

/**
 * Created by 被咯苏州 on 2017/10/23.
 */

public class TabEvent {
    private int position;

    public TabEvent(int position) {
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}

package com.lmroom.zhihu.event;

public class ZhiHuEvent {
    public static class StartDetailEvent{
        public StartDetailEvent(int id) {
            this.id = id;
        }

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}

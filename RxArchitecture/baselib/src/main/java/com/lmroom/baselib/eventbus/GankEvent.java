package com.lmroom.baselib.eventbus;

public class GankEvent {
    public static class TabEvent {
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

    public static class StartTechDetailEvent {
        private String url;

        public StartTechDetailEvent(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class StartGirlDetailEvent {
        private String id;
        private String url;


        public StartGirlDetailEvent(String id, String url) {

            this.id = id;
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }
    }
}

package com.minister.architecture.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * Created by codeest on 16/8/20.
 */
@Entity
public class GankItemBean implements Parcelable {


    /**
     * _id : 59b667cf421aa9118887ac12
     * createdAt : 2017-09-11T18:39:11.631Z
     * desc : 用少量Rxjava代码，为retrofit添加退避重试功能
     * publishedAt : 2017-09-12T08:15:08.26Z
     * source : web
     * type : Android
     * url : http://www.jianshu.com/p/fca90d0da2b5
     * used : true
     * who : 小鄧子
     * images : ["http://img.gank.io/3b0b193d-6abf-4714-9d5a-5508404666f4"]
     */
    @Id
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    @Convert(converter = GankItemConverter.class,columnType = String.class)
    private List<String> images;

    // 记录图片高度，用于处理瀑布流混乱的问题
    private int height;


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.createdAt);
        dest.writeString(this.desc);
        dest.writeString(this.publishedAt);
        dest.writeString(this.source);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.who);
        dest.writeStringList(this.images);
    }

    public boolean getUsed() {
        return this.used;
    }

    public GankItemBean() {
    }

    protected GankItemBean(Parcel in) {
        this._id = in.readString();
        this.createdAt = in.readString();
        this.desc = in.readString();
        this.publishedAt = in.readString();
        this.source = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
        this.images = in.createStringArrayList();
    }

    @Generated(hash = 1524262729)
    public GankItemBean(String _id, String createdAt, String desc, String publishedAt, String source, String type,
            String url, boolean used, String who, List<String> images, int height) {
        this._id = _id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
        this.images = images;
        this.height = height;
    }

    public static final Parcelable.Creator<GankItemBean> CREATOR = new Parcelable.Creator<GankItemBean>() {
        @Override
        public GankItemBean createFromParcel(Parcel source) {
            return new GankItemBean(source);
        }

        @Override
        public GankItemBean[] newArray(int size) {
            return new GankItemBean[size];
        }
    };

    public static class GankItemConverter implements PropertyConverter<List<String>,String>{

        @Override
        public List<String> convertToEntityProperty(String databaseValue) {
            if(databaseValue == null){
                return null;
            }
            return new Gson().fromJson(databaseValue,new TypeToken<List<String>>(){}.getType());
        }

        @Override
        public String convertToDatabaseValue(List<String> entityProperty) {
            if(entityProperty == null){
                return null;
            }
            return new Gson().toJson(entityProperty);
        }
    }
}

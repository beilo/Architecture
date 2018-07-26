package com.minister.architecture.model.bean;

/**
 * Created by leipe on 2018/2/7.
 */

public class WeatherBean {

    /**
     * weatherIco : http://pic9.tianqijun.com/static/wap2018/ico1/b2.png
     * weather : 阴
     * humidity : 湿度：38%
     * temperature : -2 ~ 9℃
     * ultravioletRay : 紫外线：最弱
     * date : 2018年02月07日　星期三　丁酉年腊月廿二
     * windDirection : 风向：东南风 1级
     * cityName : 黄山
     * nowTemperature : 1
     * cityIco : http://content.pic.tianqijun.com/content/20170918/59d61ef29065e0897c616fb232aa6b26.jpg
     */

    private String weatherIco;
    private String weather;
    private String humidity;
    private String temperature;
    private String ultravioletRay;
    private String date;
    private String windDirection;
    private String cityName;
    private String nowTemperature;
    private String cityIco;

    public String getWeatherIco() {
        return weatherIco;
    }

    public void setWeatherIco(String weatherIco) {
        this.weatherIco = weatherIco;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUltravioletRay() {
        return ultravioletRay;
    }

    public void setUltravioletRay(String ultravioletRay) {
        this.ultravioletRay = ultravioletRay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getNowTemperature() {
        return nowTemperature;
    }

    public void setNowTemperature(String nowTemperature) {
        this.nowTemperature = nowTemperature;
    }

    public String getCityIco() {
        return cityIco;
    }

    public void setCityIco(String cityIco) {
        this.cityIco = cityIco;
    }

    @Override
    public String toString() {
        return "现在时间是" + this.date +
                "天气" + this.weather +
                this.humidity +
                this.ultravioletRay +
                this.windDirection;
    }
}

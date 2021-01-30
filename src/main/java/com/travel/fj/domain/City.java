package com.travel.fj.domain;

public class City extends ValueObject {
    private Integer cityId;
    private String cityName;
    private String cityEngName;
    private Integer attracCount;
    private byte[] cityPic;
    private String cityTips;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityEngName() {
        return cityEngName;
    }

    public void setCityEngName(String cityEngName) {
        this.cityEngName = cityEngName;
    }

    public Integer getAttracCount() {
        return attracCount;
    }

    public void setAttracCount(Integer attracCount) {
        this.attracCount = attracCount;
    }

    public byte[] getCityPic() {
        return cityPic;
    }

    public void setCityPic(byte[] cityPic) {
        this.cityPic = cityPic;
    }

    public String getCityTips() {
        return cityTips;
    }

    public void setCityTips(String cityTips) {
        this.cityTips = cityTips;
    }
}

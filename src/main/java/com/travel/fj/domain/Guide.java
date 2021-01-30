package com.travel.fj.domain;

import java.util.Date;

public class Guide extends ValueObject{

    private Integer guideId;
    private String guideTitle;
    private Date guideCreateTime;
    private Admin guideAuthor;
    private GuideType guideType;
    private Attraction guideAttrac;
    private String guideText;
    private Integer guideStatus;
    private String guidePicList;
    private Integer guideClickCount;

    public Integer getGuideId() {
        return guideId;
    }

    public void setGuideId(Integer guideId) {
        this.guideId = guideId;
    }

    public String getGuideTitle() {
        return guideTitle;
    }

    public void setGuideTitle(String guideTitle) {
        this.guideTitle = guideTitle;
    }

    public Date getGuideCreateTime() {
        return guideCreateTime;
    }

    public void setGuideCreateTime(Date guideCreateTime) {
        this.guideCreateTime = guideCreateTime;
    }

    public Admin getGuideAuthor() {
        return guideAuthor;
    }

    public void setGuideAuthor(Admin guideAuthor) {
        this.guideAuthor = guideAuthor;
    }

    public GuideType getGuideType() {
        return guideType;
    }

    public void setGuideType(GuideType guideType) {
        this.guideType = guideType;
    }

    public Attraction getGuideAttrac() {
        return guideAttrac;
    }

    public void setGuideAttrac(Attraction guideAttrac) {
        this.guideAttrac = guideAttrac;
    }

    public String getGuideText() {
        return guideText;
    }

    public void setGuideText(String guideText) {
        this.guideText = guideText;
    }

    public Integer getGuideStatus() {
        return guideStatus;
    }

    public void setGuideStatus(Integer guideStatus) {
        this.guideStatus = guideStatus;
    }

    public String getGuidePicList() {
        return guidePicList;
    }

    public void setGuidePicList(String guidePicList) {
        this.guidePicList = guidePicList;
    }

    public Integer getGuideClickCount() {
        return guideClickCount;
    }

    public void setGuideClickCount(Integer guideClickCount) {
        this.guideClickCount = guideClickCount;
    }
}

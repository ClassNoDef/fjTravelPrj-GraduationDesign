package com.travel.fj.domain;

public class GuideType extends ValueObject {

    private int guideTypeId;
    private String guideTypeName;
    private  int guideCount;

    public int getGuideTypeId() {
        return guideTypeId;
    }

    public void setGuideTypeId(int guideTypeId) {
        this.guideTypeId = guideTypeId;
    }

    public String getGuideTypeName() {
        return guideTypeName;
    }

    public void setGuideTypeName(String guideTypeName) {
        this.guideTypeName = guideTypeName;
    }

    public int getGuideCount() {
        return guideCount;
    }

    public void setGuideCount(int guideCount) {
        this.guideCount = guideCount;
    }
}

package com.travel.fj.domain;

public class AttractionType extends ValueObject {

    private int attracTypeId;
    private String attracTypeName;
    private int attracCount;

    public int getAttracTypeId() {
        return attracTypeId;
    }

    public void setAttracTypeId(int attracTypeId) {
        this.attracTypeId = attracTypeId;
    }

    public String getAttracTypeName() {
        return attracTypeName;
    }

    public void setAttracTypeName(String attracTypeName) {
        this.attracTypeName = attracTypeName;
    }

    public int getAttracCount() {
        return attracCount;
    }

    public void setAttracCount(int attracCount) {
        this.attracCount = attracCount;
    }
}

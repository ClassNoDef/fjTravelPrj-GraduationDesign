package com.travel.fj.domain;

@Deprecated
public class Ob {
    int value;
    int value2;

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public  native ValueObject a();
}

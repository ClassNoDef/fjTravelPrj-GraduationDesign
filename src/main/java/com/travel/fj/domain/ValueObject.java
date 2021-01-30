package com.travel.fj.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ValueObject {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

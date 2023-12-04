package com.example.mhcdemo.enums;

public enum EventStatusTypes {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private String name;

    EventStatusTypes(String name) {
        this.name = name;
    }
}

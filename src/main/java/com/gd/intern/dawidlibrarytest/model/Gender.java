package com.gd.intern.dawidlibrarytest.model;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER"),
    RATHER_NOT_SAY("RATHER_NOT_SAY");

    private String gender;

    Gender(String value) {this.gender = value;}

    @Override
    public String toString() {
        return gender;
    }

    public static Gender fromValue(String value) {
        for (Gender e : Gender.values()) {
            if (e.gender.equals(value))
                return e;
        }
        return null;
    }
}

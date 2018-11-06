package com.gd.intern.dawidlibrarytest.util;

import io.qameta.allure.Step;
import io.restassured.RestAssured;

public class ConfigurationRestAssured {

    @Step("Set baseUri")
    public static void baseUri() {
        RestAssured.baseURI = "http://localhost:9090/dawid-library/";
    }
}

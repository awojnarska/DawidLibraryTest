package com.gd.intern.dawidlibrarytest.util;

import io.qameta.allure.Step;

public class CountElementLimit {

    @Step("Count elements on page")
    public static int countElementsOnPage(int page, int limit, int listSize) {
        int lastPage = 0;
        if (listSize % limit == 0) {
            lastPage = listSize / limit - 1;
        } else lastPage = listSize / limit;

        if (page == lastPage) {
            return listSize % limit;
        } else if (page < lastPage) return limit;
        else return 0;
    }
}

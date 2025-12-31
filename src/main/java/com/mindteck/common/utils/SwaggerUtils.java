package com.mindteck.common.utils;


import com.mindteck.common.models.rest.ApiView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the apiInfo list and the method to add and get data from the list
 *
 * @Author Jobin Jacob Paily
 *
 */
public class SwaggerUtils {

    private static List<ApiView> apiInfo = new ArrayList<>();

    private SwaggerUtils() {}

    public static final void addToList(ApiView apiView) {
        if(null == apiView) return;
        apiView.setSlNo(apiInfo.size() + 1);
        apiInfo.add(apiView);
    }

    public static final List<ApiView> getApiList() {
        return apiInfo;
    }

}

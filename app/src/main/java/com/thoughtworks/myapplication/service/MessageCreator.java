package com.thoughtworks.myapplication.service;

import com.thoughtworks.myapplication.domain.PM25;

/**
 * Created by zhihu on 15/12/24.
 */
public class MessageCreator {

    public static String initMessage(PM25 pm25){
        String result = "检测时间：" + pm25.getTime() + "\n";
        if (pm25.getPositionName() == null) {
            result += "地址：未知\n";
        }else {
            result += "地址：" + pm25.getPositionName() + "\n";
        }
        if (pm25.getPollutant() == null) {
            result += "首要污染物：未知\n";
        }else {
            result += "首要污染物：" + pm25.getPollutant() + "\n";
        }

        result += "颗粒物（粒径小于等于2.5μm）1小时平均：" + pm25.getPM25() + "\n";
        result += "颗粒物（粒径小于等于10μm）1小时平均：" + pm25.getPM10() + "\n";
        result += "二氧化硫1小时平均：" + pm25.getSO2() + "\n";
        result += "二氧化氮1小时平均：" + pm25.getNO2() + "\n";
        result += "一氧化碳1小时平均：" + pm25.getCO() + "\n";
        result += "臭氧1小时平均：" + pm25.getO3() + "\n";
        return result;
    }
}

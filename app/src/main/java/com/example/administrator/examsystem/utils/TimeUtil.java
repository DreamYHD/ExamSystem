package com.example.administrator.examsystem.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2019/5/8.
 */

public class TimeUtil {
    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current = sdf.format(System.currentTimeMillis()).split(" ")[0];
        return current;
    }

}

package com.example.administrator.examsystem.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2019/4/6.
 */

public class DayToDay {
    private static final String TAG = "DayToDay";
    public static long getDay() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //跨年不会出现问题
        //如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0
        Date fDate= null;
        try {
            fDate = sdf.parse(sdf.format(new Date()));
            Log.i(TAG, "getDay: "+fDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date oDate= null;
        try {
            oDate = sdf.parse("2019-12-22");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long days=(oDate.getTime()-fDate.getTime())/(1000*3600*24);
        System.out.print(days);
        return days;
    }

    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        String pBeginTime = sdf.format(new Date());
        return pBeginTime;
    }
    public static String getTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        String pBeginTime = sdf.format(date);
        Log.i(TAG, "getTime: "+pBeginTime);
        return pBeginTime;
    }
    public static String TimeDiff()  {

        String pEndTime = "2019-12-22 08:00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        String pBeginTime = sdf.format(new Date());
        Long beginL = null;
        try {
            beginL = sdf.parse(pBeginTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long endL = null;
        try {
            endL = sdf.parse(pEndTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long day = (endL - beginL) / 86400000;
        Long hour = ((endL - beginL) % 86400000) / 3600000;
        Long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
        return ( hour + ":" + min+":");
    }
}

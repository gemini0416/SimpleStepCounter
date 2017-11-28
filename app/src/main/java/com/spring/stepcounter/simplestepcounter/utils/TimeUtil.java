package com.spring.stepcounter.simplestepcounter.utils;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具类
 */

public class TimeUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CANADA);
    private static String[] weekStrings = new String[]{"日","一", "二", "三", "四", "五", "六"};
    private static String[] rWeekStrings = new String[]{"周日","周一", "周二", "周三", "周四", "周五", "周六"};


    /**
     * 改变日期格式
     * @param date  2017年02月09日
     * @return 2017-02-09
     */
    public static String changeFormatDate(String date){
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String curDate = null;
        try {
            Date dt = dateFormat.parse(date);
            curDate = dFormat.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curDate;
    }

    /**
     * 改变日期格式
     * @param date  2017-02-09
     * @return 2017年02月09日
     */
    public static String changeDateFormat(String date){
        SimpleDateFormat dFormatTmp = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String curDate = null;
        try {
            Date dt = dFormatTmp.parse(date);
            curDate = dateFormat.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curDate;
    }

    /**
     * 返回当前的时间
     * @return  今天 09:48
     */
    public static String getCurTime(){
        SimpleDateFormat dFormat = new SimpleDateFormat("HH:mm", Locale.CANADA);
        return "今天 "+dFormat.format(System.currentTimeMillis());
    }

    /**
     * 获取运动记录是周几，今天则返回具体时间，其他则返回具体周几
     * @param dateStr
     * @return
     */
    public static String getWeekStr(String dateStr){
        Calendar mCalendar = Calendar.getInstance();
        String todayStr = dateFormat.format(mCalendar.getTime());

        if(todayStr.equals(dateStr)){
            return getCurTime();
        }

        Calendar preCalendar = Calendar.getInstance();
        preCalendar.add(Calendar.DATE, -1);
        String yesterdayStr = dateFormat.format(preCalendar.getTime());
        if(yesterdayStr.equals(dateStr)){
            return "昨天";
        }

        int w = 0;
        try {
            Date date = dateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0){
                w = 0;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rWeekStrings[w];
    }


    /**
     * 获取是几号
     *
     * @return dd
     */
    public static int getCurrentDay() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.DATE);
    }

    /**
     * 获取当前的日期
     *
     * @return yyyy年MM月dd日
     */
    public static String getCurrentDate() {
        Calendar mCalendar = Calendar.getInstance();
        return dateFormat.format(mCalendar.getTime());
    }


    /**
     * 根据date列表获取day列表
     *
     * @param dateList
     * @return
     */
    public static List<Integer> dateListToDayList(List<String> dateList) {
        Calendar calendar = Calendar.getInstance();
        List<Integer> dayList = new ArrayList<>();
        for (String date : dateList) {
            try {
                calendar.setTime(dateFormat.parse(date));
                int day = calendar.get(Calendar.DATE);
                dayList.add(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dayList;
    }


    /**
     * 根据当前日期获取前三天和后三天时间
     * @return
     */
    public static List<String> getBeforeDateListByNow(String time){
        List<String> weekList = new ArrayList<>();

        for (int i = -3; i <= 3; i++) {
            weekList.add(getPastTime(time, i));
        }
        return weekList;
    }

    /**
     * 判断当前日期是周几
     * @param curDate
     * @return
     */
    public static String getCurWeekDay(String curDate){
        int w = 0;
        try {
            Date date = dateFormat.parse(curDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0){
                w = 0;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weekStrings[w];
    }

    /**
     * 获取指定时间多少天为哪一天
     * @param time yyyy年mm月dd日
     * @param step 步长(+后多少天，-前多少天)
     * @return
     */
    public static String getPastTime(String time, int step){
        Date date = dateFormat.parse(time, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, step);
        return dateFormat.format(calendar.getTime());
    }
}

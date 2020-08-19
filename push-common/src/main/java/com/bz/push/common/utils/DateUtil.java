package com.bz.push.common.utils;

import java.util.Date;

public class DateUtil {
	/**
     * 计算两个日期相差秒
     *
     * @param begin
     * @param end
     * @return
     */
    public static long getSecond(Date begin, Date end) {
        long l = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

        /*long hour = l / (60 * 60);
        long minute = (l / 60 - hour * 60);
        long second = (l - hour * 60 * 60 - minute * 60);
        System.out.println(second);*/
        return l;
    }
    /**
     * 计算两个日期相差分
     *
     * @param begin
     * @param end
     * @return
     */
    public static long getMinute(Date begin, Date end) {
        long l = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
        long minute = l / 60 ;
       /* long hour = l / (60 * 60);
        long minute = (l / 60 - hour * 60);
        long second = (l - hour * 60 * 60 - minute * 60);
        System.out.println(second);*/
        return minute;
    }
}

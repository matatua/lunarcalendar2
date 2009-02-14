/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anchorman.lunarcalendar.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author NamNT2
 */
public class DateUtils {

    public static final String[] DAY_OF_WEEK = {"Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"};
    
    public static int[] getYearMonthDate(Date date) {
        int[] result = new int[3];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        result[0] = calendar.get(Calendar.YEAR);
        result[1] = calendar.get(Calendar.MONTH) + 1;
        result[2] = calendar.get(Calendar.DATE);
        return result;
    }

    public static int[] getCurrentYearMonthDate() {
        Date date = new Date();
        return getYearMonthDate(date);
    }

    public static String getDayOfWeek(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        return DAY_OF_WEEK[i-1];
    }

}

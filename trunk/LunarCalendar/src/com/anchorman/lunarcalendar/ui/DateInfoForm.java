/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anchorman.lunarcalendar.ui;

import com.anchorman.lunarcalendar.properties.Resource;
import com.anchorman.lunarcalendar.util.DateUtils;
import com.anchorman.lunarcalendar.util.LunarCalendarUtils;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author NamNT2
 */
public class DateInfoForm extends Form implements CommandListener{

    private static final Command CMD_CLOSE = new Command(Resource.CLOSE_TEXT, Command.OK, 1);
    private Display display;
    private Displayable parent;
    private StringItem solarStringItem;
    private StringItem lunarStringItem;
    private StringItem zodiacTimeStringItem;
    private StringItem yearlyEventStringItem;
    private StringItem specialYearlyEventStringItem;

    private int year, month, day;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    public DateInfoForm(String title) {
        super(title);
    }

    public DateInfoForm(String title, Displayable parent, Display display) {
        super(title);
        this.parent = parent;
        this.display = display;
        solarStringItem = new StringItem(Resource.SOLAR_CALENDAR_TEXT, Resource.EMPTY);
        lunarStringItem = new StringItem(Resource.LUNAR_CALENDAR_TEXT, Resource.EMPTY);
        zodiacTimeStringItem = new StringItem(Resource.ZODIAC_TIME_TEXT,Resource.EMPTY);
        yearlyEventStringItem = new StringItem(Resource.YEARLY_EVENT_TEXT,Resource.EMPTY);
        specialYearlyEventStringItem = new StringItem(Resource.SPECIAL_YEARLY_EVENT_TEXT,Resource.EMPTY);
        solarStringItem.setFont(Resource.FONT_SMALL_PLAIN);
        lunarStringItem.setFont(Resource.FONT_SMALL_PLAIN);
        zodiacTimeStringItem.setFont(Resource.FONT_SMALL_PLAIN);
        yearlyEventStringItem.setFont(Resource.FONT_SMALL_PLAIN);
        specialYearlyEventStringItem.setFont(Resource.FONT_SMALL_PLAIN);

        append(solarStringItem);
        //append(specialYearlyEventStringItem);
        append(lunarStringItem);
        append(zodiacTimeStringItem);
        //append(yearlyEventStringItem);
        
        addCommand(CMD_CLOSE);

        setCommandListener(this);
    }

    public void getInformation(){
        // Get solar calendar
        String str = DateUtils.getDayOfWeek(year, month, day);
        str += " ngày " + day + " tháng " + month + " năm " + year;
        solarStringItem.setText(str);

        int jd = LunarCalendarUtils.jdFromDate(day, month, year);
        
        // Get lunar calendar
        int[] dmy = LunarCalendarUtils.convertSolar2Lunar(day, month, year, LunarCalendarUtils.TIME_ZONE);

        String lunarMonthName = LunarCalendarUtils.getLunarMonthName(dmy[1] - 1);
        String lunarYearName = LunarCalendarUtils.getLunarYearName(dmy[2]);
        str = "Ngày " + dmy[0] + " " + lunarMonthName + " " + lunarYearName + Resource.NEW_LINE;
        str += LunarCalendarUtils.getLunarMonthCanChiName(dmy[2],dmy[1]) + Resource.NEW_LINE;
        //int jd = LunarCalendarUtils.jdFromDate(dmy[0], dmy[1], dmy[2]);
        str += LunarCalendarUtils.getLunarDayCanChiName(jd) + Resource.NEW_LINE;
        str += LunarCalendarUtils.getHourCan(jd) + Resource.NEW_LINE;
        str += LunarCalendarUtils.getTietKhiName(jd) + Resource.NEW_LINE;
        lunarStringItem.setText(str);

        // get special event on day
        String eventString = LunarCalendarUtils.getDaySpecialYearlyEvent(day, month);
        if (!Resource.EMPTY.equals(eventString)){
            specialYearlyEventStringItem.setText(eventString);
            //append(yearlyEventStringItem);
            insert(1, specialYearlyEventStringItem);
        }


        // Get zodiac time
        //int jd = LunarCalendarUtils.jdFromDate(day, month, year);
        String zodiacTimeString = LunarCalendarUtils.getZodiacTime(jd);
        zodiacTimeStringItem.setText(zodiacTimeString);

        // get event on day
        eventString = LunarCalendarUtils.getDayYearlyEvent(dmy[0], dmy[1]);
        if (!Resource.EMPTY.equals(eventString)){
            yearlyEventStringItem.setText(eventString);
            append(yearlyEventStringItem);
        }
    }

    public void commandAction(Command command, Displayable d) {
        if(command == CMD_CLOSE){
            display.setCurrent(parent);
        }
    }

}

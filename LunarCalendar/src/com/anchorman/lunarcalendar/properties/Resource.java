/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anchorman.lunarcalendar.properties;

import javax.microedition.lcdui.Font;

/**
 *
 * @author NamNT2
 */
public class Resource {

    public static final String EXIT_TEXT = "Thoát";
    public static final String CANCEL_TEXT = "Hủy bỏ";
    public static final String OK_TEXT = "Đồng ý";
    public static final String MONTH_TEXT = "Tháng";
    public static final String YEAR_TEXT = "Năm";
    public static final String CHOOSE_MONTH_TEXT = "Chọn tháng";
    public static final String DATE_INFO_TEXT = "Xem ngày";
    public static final String CLOSE_TEXT = "Đóng";
    public static final String EMPTY = "";
    public static final String CURENT_MONTH_TEXT = "Tháng này";
    public static final String NEXT_MONTH_TEXT = "Tháng sau";
    public static final String PREV_MONTH_TEXT = "Tháng trước";
    public static final String ABOUT_TEXT = "Thông tin";

    public static final String BACKGROUND_IMAGE_PATH = "/ui/bg.jpg";
    public static final String ABOUT_IMAGE_PATH = "/ui/about.jpg";
    public static final String EVENT_IMAGE_PATH = "/ui/event.png";

    public static final String PROJECT_NAME = "Lunar Calendar";
    public static final String ZODIAC_TIME_TEXT = "Giờ hoàng đạo: ";
    public static String SOLAR_CALENDAR_TEXT = "Dương lịch: ";
    public static String LUNAR_CALENDAR_TEXT = "Âm lịch: ";
    public static String NEW_LINE = "\r\n";
    //public static int FONT_FACE_SYSTEM = Font.FACE_SYSTEM;
    //public static int FONT_FACE_PROPORTIONAL = Font.FACE_PROPORTIONAL;
    //public static int FONT_FACE_MONOSPACE = Font.FACE_MONOSPACE;

    public static Font FONT_SMALL_PLAIN = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    public static Font FONT_MEDIUM_PLAIN = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
    public static Font FONT_MEDIUM_BOLD = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
    public static Font FONT_LARGE_PLAIN = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);

    public static String YEARLY_EVENT_TEXT = "Sự kiện âm lịch: ";
    public static String SPECIAL_YEARLY_EVENT_TEXT = "Sự kiện dương lịch: ";
}
package com.anchorman.lunarcalendar.ui;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

import com.anchorman.lunarcalendar.properties.Resource;
import com.anchorman.lunarcalendar.util.DateUtils;

/**
 *
 * @author NamNT2
 */
public class MainMIDlet extends MIDlet implements CommandListener {

    private static final Command CMD_EXIT = new Command(Resource.EXIT_TEXT, Command.EXIT, 1);
    private static final Command CMD_SHOW_DATE_INFO = new Command(Resource.DATE_INFO_TEXT, Command.SCREEN, 1);
    private static final Command CMD_CHOOSE_DATE = new Command(Resource.CHOOSE_MONTH_TEXT, Command.SCREEN, 2);
    private static final Command CMD_GOTO_CURRENT_MONTH = new Command(Resource.CURENT_MONTH_TEXT, Command.SCREEN, 3);
    private static final Command CMD_GOTO_NEXT_MONTH = new Command(Resource.NEXT_MONTH_TEXT, Command.SCREEN, 4);
    private static final Command CMD_GOTO_PREV_MONTH = new Command(Resource.PREV_MONTH_TEXT, Command.SCREEN, 5);
    private static final Command CMD_ABOUT = new Command(Resource.ABOUT_TEXT, Command.SCREEN, 6);
    private Display display;
    private boolean firstTime;
    //private Form mainForm;
    public int currentYear;
    public int currentMonth;
    //public int currrentDay;
    private ChooseDateForm chooseDateForm = null;
    //private LunarCalendarControl lunarCalControl;
    private CalendarCanvas calendarControl;
    
    public Display getDisplay() {
        return display;
    }

    public MainMIDlet() {
        firstTime = true;

        // Set current currentMonth
        int[] ymd = DateUtils.getCurrentYearMonthDate();
        currentYear = ymd[0];
        currentMonth = ymd[1];

        //mainForm = new Form(Resource.PROJECT_NAME);
    }

//    public void showSplashScreen() {
//        //new SplashCanvas(display, mainForm);
//    	new SplashCanvas(display, calendarControl);
//    }

    public void gotoMonth(int year, int month) {
        this.currentYear = year;
        this.currentMonth = month;
        //lunarCalControl.setCurrentDay(1);

        calendarControl.gotoMonth(currentYear, currentMonth);
    }

    public void gotoDate(int year, int month, int day) {
        this.currentYear = year;
        this.currentMonth = month;

        calendarControl.gotoMonth(currentYear, currentMonth);
        //lunarCalControl.setCurrentDay(day);

        int[] ymd = DateUtils.getCurrentYearMonthDate();
        if (currentMonth == ymd[1] && currentYear == ymd[0]) {
            day = ymd[2];
        }
        gotoMonth(currentYear, currentMonth);
        calendarControl.setCurrentDay(day);
    }

    private void showAboutForm() {
        AboutCanvas aboutCanvas = new AboutCanvas(calendarControl, display);
        display.setCurrent(aboutCanvas);
    }

    private void showChooseDateForm() {
        if (chooseDateForm == null) {
            chooseDateForm = new ChooseDateForm(Resource.CHOOSE_MONTH_TEXT, this);
        }
        chooseDateForm.setMonth(currentMonth);
        chooseDateForm.setYear(currentYear);
        display.setCurrent(chooseDateForm);
    }

    private void showDateInformation() {
        DateInfoForm dateInfomationForm = new DateInfoForm(Resource.APPLICATION_NAME, calendarControl, display);
        dateInfomationForm.setDay(calendarControl.getCurrentDay());
        dateInfomationForm.setMonth(currentMonth);
        dateInfomationForm.setYear(currentYear);
        dateInfomationForm.getInformation();

        display.setCurrent(dateInfomationForm);
    }

    private void showCurrentMonth() {
        int[] ymd = DateUtils.getCurrentYearMonthDate();
        currentYear = ymd[0];
        currentMonth = ymd[1];
        gotoMonth(currentYear, currentMonth);
        calendarControl.setCurrentDay(ymd[2]);
    }

    private void showNextMonth() {
        int day = 1;
        if (currentMonth == 12) {
            currentMonth = 1;
            currentYear++;
        } else {
            currentMonth++;
        }

        int[] ymd = DateUtils.getCurrentYearMonthDate();
        if (currentMonth == ymd[1] && currentYear == ymd[0]) {
            day = ymd[2];
        }
        gotoMonth(currentYear, currentMonth);
        calendarControl.setCurrentDay(day);
    }

    private void showPrevMonth() {
        int day = 1;
        if (currentMonth == 1) {
            currentMonth = 12;
            currentYear--;
        } else {
            currentMonth--;
        }

        int[] ymd = DateUtils.getCurrentYearMonthDate();
        if (currentMonth == ymd[1] && currentYear == ymd[0]) {
            day = ymd[2];
        }
        gotoMonth(currentYear, currentMonth);
        calendarControl.setCurrentDay(day);
    }

    protected void initialize() {
        display = Display.getDisplay(this);
        
//        lunarCalControl = new LunarCalendarControl(Resource.EMPTY, Display.getDisplay(this));
//        lunarCalControl.gotoMonth(currentYear, currentMonth);
//        lunarCalControl.setCurrentDay(lunarCalControl.getCurrentDay());
//        lunarCalControl.setPreferredSize(ScreenDetection.getFormWidth(), ScreenDetection.getFormHeight());
//        //lunarCalControl.setLayout(Item.LAYOUT_EXPAND | Item.LAYOUT_VEXPAND);
//
//        mainForm.append(lunarCalControl);
//        
//        mainForm.addCommand(CMD_EXIT);
//        mainForm.addCommand(CMD_SHOW_DATE_INFO);
//        mainForm.addCommand(CMD_CHOOSE_DATE);
//        mainForm.addCommand(CMD_GOTO_CURRENT_MONTH);
//        mainForm.addCommand(CMD_GOTO_NEXT_MONTH);
//        mainForm.addCommand(CMD_GOTO_PREV_MONTH);
//        mainForm.addCommand(CMD_ABOUT);
//        mainForm.setCommandListener(this);
        
        calendarControl = new CalendarCanvas();
        calendarControl.gotoMonth(currentYear, currentMonth);
        calendarControl.setCurrentDay(calendarControl.getCurrentDay());     
        
        calendarControl.addCommand(CMD_EXIT);
        calendarControl.addCommand(CMD_SHOW_DATE_INFO);
        calendarControl.addCommand(CMD_CHOOSE_DATE);
        calendarControl.addCommand(CMD_GOTO_CURRENT_MONTH);
        calendarControl.addCommand(CMD_GOTO_NEXT_MONTH);
        calendarControl.addCommand(CMD_GOTO_PREV_MONTH);
        calendarControl.addCommand(CMD_ABOUT);
        
        calendarControl.setCommandListener(this);
    }

    protected void startApp() {
        if (firstTime) {
            initialize();

            firstTime = false;
        }

        display.setCurrent(calendarControl);
    //showSplashScreen();
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == CMD_EXIT) {
            destroyApp(false);
            notifyDestroyed();
        } else if (command == CMD_CHOOSE_DATE) {
            //lunarCalControl.setLocation(LunarCalendarControl.UPPER);
            showChooseDateForm();
        } else if (command == CMD_SHOW_DATE_INFO) {
            //lunarCalControl.setLocation(LunarCalendarControl.UPPER);
            showDateInformation();
        } else if (command == CMD_GOTO_CURRENT_MONTH) {
            showCurrentMonth();
        } else if (command == CMD_GOTO_NEXT_MONTH) {
            showNextMonth();
        } else if (command == CMD_GOTO_PREV_MONTH) {
            showPrevMonth();
        } else if (command == CMD_ABOUT) {
            //lunarCalControl.setLocation(LunarCalendarControl.UPPER);
            showAboutForm();
        }
    }

    protected void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }

    protected void pauseApp() {
    }

	public Displayable getCalendarControl() {
		return calendarControl;
	}
}

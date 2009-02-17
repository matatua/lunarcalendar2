package com.anchorman.lunarcalendar.ui;

import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.anchorman.lunarcalendar.platform.ScreenDetection;
import com.anchorman.lunarcalendar.properties.Resource;
import com.anchorman.lunarcalendar.util.DateUtils;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

public class TestCalendarCanvas extends MIDlet implements CommandListener {
    private static final Command CMD_EXIT = new Command(Resource.EXIT_TEXT, Command.EXIT, 1);
    private static final Command CMD_SHOW_DATE_INFO = new Command(Resource.DATE_INFO_TEXT, Command.SCREEN, 1);
    private static final Command CMD_CHOOSE_DATE = new Command(Resource.CHOOSE_MONTH_TEXT, Command.SCREEN, 2);
    private static final Command CMD_GOTO_CURRENT_MONTH = new Command(Resource.CURENT_MONTH_TEXT, Command.SCREEN, 3);
    private static final Command CMD_GOTO_NEXT_MONTH = new Command(Resource.NEXT_MONTH_TEXT, Command.SCREEN, 4);
    private static final Command CMD_GOTO_PREV_MONTH = new Command(Resource.PREV_MONTH_TEXT, Command.SCREEN, 5);
    private static final Command CMD_ABOUT = new Command(Resource.ABOUT_TEXT, Command.SCREEN, 6);
    private Display display;
    private boolean firstTime;
    private Form mainForm;
    public int currentYear;
    public int currentMonth;
    //public int currrentDay;
    private ChooseDateForm chooseDateForm = null;
    private StringItem header;
	private CalendarCanvas canvas;
	
	public TestCalendarCanvas() {
		display = Display.getDisplay(this);
		canvas = new CalendarCanvas();
		canvas.gotoMonth(2009, 4);
		canvas.setCurrentDay(canvas.getCurrentDay());

        canvas.addCommand(CMD_EXIT);
        canvas.addCommand(CMD_SHOW_DATE_INFO);
        canvas.addCommand(CMD_CHOOSE_DATE);
        canvas.addCommand(CMD_GOTO_CURRENT_MONTH);
        canvas.addCommand(CMD_GOTO_NEXT_MONTH);
        canvas.addCommand(CMD_GOTO_PREV_MONTH);
        canvas.addCommand(CMD_ABOUT);
        canvas.setCommandListener(this);
		
	}

    public void showSplashScreen() {
        new SplashCanvas(display, mainForm);
    }

    public void gotoMonth(int year, int month) {
        this.currentYear = year;
        this.currentMonth = month;
        //canvas.setCurrentDay(1);

        canvas.gotoMonth(currentYear, currentMonth);
    }

    public void gotoDate(int year, int month, int day) {
        this.currentYear = year;
        this.currentMonth = month;

        canvas.gotoMonth(currentYear, currentMonth);
        //canvas.setCurrentDay(day);

        int[] ymd = DateUtils.getCurrentYearMonthDate();
        if (currentMonth == ymd[1] && currentYear == ymd[0]) {
            day = ymd[2];
        }
        gotoMonth(currentYear, currentMonth);
        canvas.setCurrentDay(day);
    }

    private void showAboutForm() {
        AboutCanvas aboutCanvas = new AboutCanvas(canvas, display);
        display.setCurrent(aboutCanvas);
    }

    private void showChooseDateForm() {
//        if (chooseDateForm == null) {
//            chooseDateForm = new ChooseDateForm(Resource.CHOOSE_MONTH_TEXT, this);
//        }
//        chooseDateForm.setMonth(currentMonth);
//        chooseDateForm.setYear(currentYear);
//        display.setCurrent(chooseDateForm);
    }

    private void showDateInformation() {
        DateInfoForm dateInfomationForm = new DateInfoForm(Resource.PROJECT_NAME, canvas, display);
        dateInfomationForm.setDay(canvas.getCurrentDay());
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
        canvas.setCurrentDay(ymd[2]);
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
        canvas.setCurrentDay(day);
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
        canvas.setCurrentDay(day);
    }

    

    protected void startApp() {
        if (firstTime) {
            //initialize();

            firstTime = false;
        }

        display.setCurrent(canvas);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == CMD_EXIT) {
            notifyDestroyed();
        } else if (command == CMD_CHOOSE_DATE) {
            //canvas.setLocation(LunarCalendarControl.UPPER);
            showChooseDateForm();
        } else if (command == CMD_SHOW_DATE_INFO) {
            //canvas.setLocation(LunarCalendarControl.UPPER);
            showDateInformation();
        } else if (command == CMD_GOTO_CURRENT_MONTH) {
            showCurrentMonth();
        } else if (command == CMD_GOTO_NEXT_MONTH) {
            showNextMonth();
        } else if (command == CMD_GOTO_PREV_MONTH) {
            showPrevMonth();
        } else if (command == CMD_ABOUT) {
            //canvas.setLocation(LunarCalendarControl.UPPER);
            showAboutForm();
        }
    }
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		notifyDestroyed();
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}
}

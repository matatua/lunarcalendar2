/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anchorman.lunarcalendar.ui;

import com.anchorman.lunarcalendar.properties.Resource;
import com.anchorman.lunarcalendar.util.LunarCalendarUtils;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;

/**
 * @author NamNT2
 */
public class ChooseDateForm extends Form implements CommandListener {

    private static final Command CMD_CANCEL = new Command(Resource.CANCEL_TEXT, Command.EXIT, 1);
    private static final Command CMD_OK = new Command(Resource.OK_TEXT, Command.SCREEN, 1);
    private String[] monthList = LunarCalendarUtils.THANG;
    private int year;
    private int month;
    private Display display;
    private MainMIDlet parent;
    private ChoiceGroup monthChooseGroup;
    private TextField yearTextField;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        this.monthChooseGroup.setSelectedIndex(month-1, true);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        this.yearTextField.setString(String.valueOf(year));
    }

    public ChooseDateForm(String title) {
        super(title);

        initialize();
    }

    public ChooseDateForm(String title, MainMIDlet parent) {
        super(title);
        this.parent = parent;
        this.display = parent.getDisplay();

        initialize();
    }

    protected void initialize() {
        monthChooseGroup = new ChoiceGroup(Resource.MONTH_TEXT, ChoiceGroup.POPUP, monthList, null);
        yearTextField = new TextField(Resource.YEAR_TEXT, String.valueOf(year), 4, TextField.NUMERIC);

        //monthChooseGroup.setLayout(Item.LAYOUT_CENTER);
        //yearTextField.setLayout(Item.LAYOUT_CENTER);
        //yearTextField.setPreferredSize(100, 10);
        
        this.append(monthChooseGroup);
        this.append(yearTextField);
        this.addCommand(CMD_OK);
        this.addCommand(CMD_CANCEL);
        this.setCommandListener(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == CMD_OK) {
            month = monthChooseGroup.getSelectedIndex() + 1;
            year = Integer.parseInt(yearTextField.getString());
            parent.gotoDate(year, month, 1);
            display.setCurrent(parent.getMainForm());
        } else if (command == CMD_CANCEL) {
            display.setCurrent(parent.getMainForm());
        }
    }
}

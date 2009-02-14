/*
 *
 * Copyright (c) 2007, Sun Microsystems, Inc.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Sun Microsystems nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.anchorman.lunarcalendar.ui;

import com.anchorman.lunarcalendar.drawing.Color;
import com.anchorman.lunarcalendar.platform.ScreenDetection;
import com.anchorman.lunarcalendar.properties.Resource;
import com.anchorman.lunarcalendar.util.DateUtils;
import javax.microedition.lcdui.*;
import com.anchorman.lunarcalendar.util.LunarCalendarUtils;
import com.anchorman.lunarcalendar.util.GraphicUtils;
import java.util.Date;

/**
 *
 * @author NamNT2
 */
public class LunarCalendarControl extends CustomItem implements ItemCommandListener {

    //private static final Command CMD_EDIT = new Command("Edit", Command.ITEM, 1);
    public static final int UPPER = 0;
    public static final int IN = 1;
    public static final int LOWER = 2;
    private Display display;
    private int rows = LunarCalendarUtils.NUMBER_ROW;
    private int cols = LunarCalendarUtils.NUMBER_COLUMN;
    private static int CELL_WIDTH = 32;
    private static int CELL_HEIGHT = 30;
    private int location = UPPER;
    //public static int location = UPPER;
    private int currentX = 0;
    private int currentY = 0;
    private String[][] solarCalendarData = new String[rows][cols];
    private String[][] lunarCalendarData = new String[rows][cols];
    private Color headerDayColor = new Color(0, 0, 0);
    private Color solarSundayColor = new Color(230, 4, 4);
    private Color lunarSundayColor = new Color(230, 4, 4);
    private Color solarSaturdayColor = new Color(12, 85, 206);
    private Color lunarSaturdayColor = new Color(12, 85, 206);
    private Color solarDayColor = new Color(0, 0, 0);
    private Color lunarDayColor = new Color(70, 70, 70);
    private Color eventColor = new Color(255, 0, 0);
    private Color lineColor = new Color(203, 203, 203);
    private Color cellFocusColor = new Color(243, 185, 185);
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private static int PADDING_TOP = 25;
    private static int SUB_HEIGHT = 10;
    // Traversal stuff     
    // indicating support of horizontal traversal internal to the CustomItem
    boolean horz;

    // indicating support for vertical traversal internal to the CustomItem.
    boolean vert;
    private String header;
    private Image background;

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
    

    public int getCurrentMonth() {
        return currentMonth;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public int getCurrentDay() {
        String dayString = solarCalendarData[currentY][currentX];
        try {
            if (dayString.indexOf("/") >= 0) {
                currentDay = Integer.parseInt(dayString.substring(0, dayString.indexOf("/")));
            } else {
                currentDay = Integer.parseInt(dayString);
            }
        } catch (Exception ex) {
        }
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
        setDayFocus(currentDay);
    }

    public LunarCalendarControl(String title, Display display) {
        super(title);
        this.display = display;
        //setDefaultCommand(CMD_EDIT);
        //setItemCommandListener(this);
        int[] ymd = DateUtils.getCurrentYearMonthDate();
        currentYear = ymd[0];
        currentMonth = ymd[1];
        currentDay = ymd[2];

        int interactionMode = getInteractionModes();
        horz = ((interactionMode & CustomItem.TRAVERSE_HORIZONTAL) != 0);
        vert = ((interactionMode & CustomItem.TRAVERSE_VERTICAL) != 0);
        this.setPreferredSize(ScreenDetection.getCanvasWidth(), ScreenDetection.getCanvasHeight());
    }

    private void fillDateForMonth(int year, int month) {
        solarCalendarData = LunarCalendarUtils.getSolarMonth(year, month);
        lunarCalendarData = LunarCalendarUtils.getLunarMonth(year, month);
        header = "Tháng " + currentMonth + " năm " + currentYear;
        repaint();
    }

    public void setDateFocus(Date date) {
        int[] yearMonthDate = DateUtils.getYearMonthDate(date);
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (solarCalendarData[i][j].equals(String.valueOf(yearMonthDate[2]))) {
                    currentX = j;
                    currentY = i;
                    break;
                }
            }
        }
        repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
    }

    public void setDayFocus(int day) {
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (solarCalendarData[i][j].equals(String.valueOf(day))) {
                    currentX = j;
                    currentY = i;
                    break;
                }
            }
        }
        repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
    }

    protected int getMinContentHeight() {
        return (rows * CELL_HEIGHT) + 1;
    }

    protected int getMinContentWidth() {
        return (cols * CELL_WIDTH) + 1;
    }

    protected int getPrefContentHeight(int width) {
        return (rows * CELL_HEIGHT) + 1;
    }

    protected int getPrefContentWidth(int height) {
        return (cols * CELL_WIDTH) + 1;
    }

    protected void paint(Graphics g, int w, int h) {
        // Load background image
        if (background == null) {
            try {
                background = Image.createImage(Resource.BACKGROUND_IMAGE_PATH);
            } catch (Exception e) {
            }
        }
        // Draw background
        if (background != null){
            g.drawImage(background, 0, 0, Graphics.TOP | Graphics.LEFT);
        }

        // Draw header of calendar (Month mm Year yyyy)
        Font headFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        GraphicUtils.drawText(g, header, headFont, ScreenDetection.getCanvasWidth() / 2, PADDING_TOP, headerDayColor, Align.Center);

        GraphicUtils.setColor(g, lineColor);
        //for (int i = 0; i <= rows; i++) {
        for (int i = 1; i < rows; i++) {
            g.drawLine(0, i * CELL_HEIGHT + PADDING_TOP, cols * CELL_WIDTH, i * CELL_HEIGHT + PADDING_TOP);
        }

        //for (int i = 0; i <= cols; i++) {
        for (int i = 1; i < cols; i++) {
            g.drawLine(i * CELL_WIDTH, 0 + PADDING_TOP + SUB_HEIGHT, i * CELL_WIDTH, rows * CELL_HEIGHT + PADDING_TOP);
        }

        int oldColor = g.getColor();
        GraphicUtils.setColor(g, cellFocusColor);
        g.fillRect((currentX * CELL_WIDTH) + 1, (currentY * CELL_HEIGHT) + 1 + PADDING_TOP, CELL_WIDTH - 1, CELL_HEIGHT - 1);
        g.setColor(oldColor);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (solarCalendarData[i][j] != null || !Resource.EMPTY.equals(solarCalendarData[i][j])) {
                    // store clipping properties
                    int oldClipX = g.getClipX();
                    int oldClipY = g.getClipY();
                    int oldClipWidth = g.getClipWidth();
                    int oldClipHeight = g.getClipHeight();
                    g.setClip((j * CELL_WIDTH) + 1, i * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH - 1, CELL_HEIGHT - 1);

                    if (i == 0) {
                        GraphicUtils.setColor(g, headerDayColor);
                    } else {
                        GraphicUtils.setColor(g, solarDayColor);
                    }
                    if (j == 0) {
                        GraphicUtils.setColor(g, solarSundayColor);
                    } else if (j == cols - 1) {
                        GraphicUtils.setColor(g, solarSaturdayColor);
                    }

                    g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD,
                            Font.SIZE_MEDIUM));


                    if (i == 0) {
                        g.drawString(solarCalendarData[i][j], (int) (j * CELL_WIDTH + CELL_WIDTH / 2), (int) (i * CELL_HEIGHT + CELL_HEIGHT / 2 + PADDING_TOP + SUB_HEIGHT),
                                Graphics.BASELINE | Graphics.HCENTER);
                    } else {
                        g.drawString(solarCalendarData[i][j], (j * CELL_WIDTH) + 1, ((i + 1) * CELL_HEIGHT + PADDING_TOP),
                                Graphics.BOTTOM | Graphics.LEFT);
                    }


                    if (i == 0) {
                        //GraphicUtils.setColor(g, headerDayColor);
                        continue;
                    } else {
                        GraphicUtils.setColor(g, lunarDayColor);
                    }
                    if (j == 0) {
                        GraphicUtils.setColor(g, lunarSundayColor);
                    } else if (j == cols - 1) {
                        GraphicUtils.setColor(g, lunarSaturdayColor);
                    }

                    g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN,
                            Font.SIZE_SMALL));
                    g.drawString(lunarCalendarData[i][j], (j + 1) * CELL_WIDTH, ((i + 0) * CELL_HEIGHT) - 3 + PADDING_TOP,
                            Graphics.TOP | Graphics.RIGHT);

                    // restore clipping properties
                    g.setClip(oldClipX, oldClipY, oldClipWidth, oldClipHeight);
                }
            }
        }
    }

    protected boolean traverse(int dir, int viewportWidth, int viewportHeight, int[] visRect_inout) {
        if (horz && vert) {
            switch (dir) {
                case Canvas.DOWN:
                    if (location == UPPER) {
                        location = IN;
                    } else {
                        if (currentY < (rows - 1)) {
                            // Check focusable for cell
                            if (Resource.EMPTY.equals(solarCalendarData[currentY + 1][currentX])) {
                                break;
                            }

                            currentY++;
                            repaint(currentX * CELL_WIDTH, (currentY - 1) * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                            repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                        } else {
                            location = LOWER;

                            return false;
                        }
                    }

                    break;

                case Canvas.UP:

                    if (location == LOWER) {
                        location = IN;
                    } else {
                        if (currentY > 1) {
                            // Check focusable for cell
                            if (Resource.EMPTY.equals(solarCalendarData[currentY - 1][currentX])) {
                                break;
                            }
                            currentY--;
                            repaint(currentX * CELL_WIDTH, (currentY + 1) * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                            repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                        } else {
                            location = UPPER;

                            return false;
                        }
                    }

                    break;

                case Canvas.LEFT:

                    if (currentX > 0) {
                        // Check focusable for cell
                        if (Resource.EMPTY.equals(solarCalendarData[currentY][currentX - 1])) {
                            break;
                        }

                        currentX--;
                        repaint((currentX + 1) * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                        repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                    }

                    break;

                case Canvas.RIGHT:

                    if (currentX < (cols - 1)) {
                        // Check focusable for cell
                        if (Resource.EMPTY.equals(solarCalendarData[currentY][currentX + 1])) {
                            break;
                        }
                        currentX++;
                        repaint((currentX - 1) * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                        repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                    }
            }
        } else if (horz || vert) {
            switch (dir) {
                case Canvas.UP:
                case Canvas.LEFT:

                    if (location == LOWER) {
                        location = IN;
                    } else {
                        if (currentX > 0) {
                            currentX--;
                            repaint((currentX + 1) * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                            repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                        } else if (currentY > 0) {
                            currentY--;
                            repaint(currentX * CELL_WIDTH, (currentY + 1) * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                            currentX = cols - 1;
                            repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                        } else {
                            location = UPPER;

                            return false;
                        }
                    }

                    break;

                case Canvas.DOWN:
                case Canvas.RIGHT:

                    if (location == UPPER) {
                        location = IN;
                    } else {
                        if (currentX < (cols - 1)) {
                            currentX++;
                            repaint((currentX - 1) * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                            repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                        } else if (currentY < (rows - 1)) {
                            currentY++;
                            repaint(currentX * CELL_WIDTH, (currentY - 1) * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                            currentX = 0;
                            repaint(currentX * CELL_WIDTH, currentY * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
                        } else {
                            location = LOWER;

                            return false;
                        }
                    }
            }
        } else {
            // In case of no Traversal at all: (horz|vert) == 0
        }

        visRect_inout[0] = currentX;
        visRect_inout[1] = currentY;
        visRect_inout[2] = CELL_WIDTH;
        visRect_inout[3] = CELL_HEIGHT;

        return true;
    }

    public void setText(String text) {
        solarCalendarData[currentY][currentX] = text;
        repaint(currentY * CELL_WIDTH, currentX * CELL_HEIGHT + PADDING_TOP, CELL_WIDTH, CELL_HEIGHT);
    }

    public void commandAction(Command c, Item i) {
        /*
        if (c == CMD_EDIT) {
        TextInput textInput = new TextInput(solarCalendarData[currentY][currentX], this, display);
        display.setCurrent(textInput);
        }
         * */
    }

    public void gotoMonth(int year, int month) {
        currentMonth = month;
        currentYear = year;
        fillDateForMonth(year, month);
    }
}

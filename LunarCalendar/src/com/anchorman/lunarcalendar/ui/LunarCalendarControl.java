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

    public static final int UPPER = 0;
    public static final int IN = 1;
    public static final int LOWER = 2;
    private Display display;
    private int rows = LunarCalendarUtils.NUMBER_ROW;
    private int cols = LunarCalendarUtils.NUMBER_COLUMN;
    private int cellWidth = ScreenDetection.getFormWidth() / cols;
    private int cellHeight = (ScreenDetection.getFormHeight() - PADDING_TOP) / rows;
    private int location = UPPER;
    private int currentX = 0;
    private int currentY = 0;
    private String[][] solarCalendarData = new String[rows][cols];
    private String[][] lunarCalendarData = new String[rows][cols];
    private Color headerDayColor = new Color(0, 0, 0);
    private Color solarSundayColor = new Color(164, 13, 13);
    private Color lunarSundayColor = new Color(213, 8, 8);
    private Color solarSaturdayColor = new Color(0, 68, 181);
    private Color lunarSaturdayColor = new Color(4, 59, 149);
    private Color solarDayColor = new Color(0, 0, 0);
    private Color lunarDayColor = new Color(70, 70, 70);
    private Color eventColor = new Color(230, 0, 230);
    private Color lineColor = new Color(203, 203, 203);
    private Color cellFocusColor = new Color(90, 200, 228);
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private static int PADDING_TOP = 25;
    private static int SUB_HEIGHT = 10;
    private static int ARC_CORNER_WIDTH = 10;
    private static int ARC_CORNER_HEIGHT = 10;
    // Traversal stuff     
    // indicating support of horizontal traversal internal to the CustomItem
    boolean horz;

    // indicating support for vertical traversal internal to the CustomItem.
    boolean vert;
    private String header;
    private Image backgroundImage;
    private Image eventImage;
    private static String EVENT_SYMBOL = "*";

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
        //this.setPreferredSize(ScreenDetection.getFormWidth(), ScreenDetection.getFormHeight());

        // Load backgroundImage image
        if (backgroundImage == null) {
            try {
                backgroundImage = Image.createImage(Resource.BACKGROUND_IMAGE_PATH);
                int width = ScreenDetection.getFormWidth();
                int height = ScreenDetection.getFormHeight();
                backgroundImage = GraphicUtils.scaleImage(backgroundImage, width, height);
            } catch (Exception e) {
            }
        }

        // Load event image
        if (eventImage == null) {
            try {
                eventImage = Image.createImage(Resource.EVENT_IMAGE_PATH);
            } catch (Exception e) {
            }
        }
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
        repaint(currentX * cellWidth, currentY * cellHeight, cellWidth, cellHeight);
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
        repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
    }

    protected int getMinContentHeight() {
        return (rows * cellHeight) + 1;
    }

    protected int getMinContentWidth() {
        return (cols * cellWidth) + 1;
    }

    protected int getPrefContentHeight(int width) {
        return (rows * cellHeight) + 1;
    }

    protected int getPrefContentWidth(int height) {
        return (cols * cellWidth) + 1;
    }

    protected void paint(Graphics g, int w, int h) {
        // Draw backgroundImage
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Graphics.TOP | Graphics.LEFT);
        //g.drawRegion(backgroundImage, 0, 0,
        //        backgroundImage.getWidth(), backgroundImage.getHeight(), Sprite.TRANS_NONE, h, h, h);
        }

        // Draw header of calendar (Month mm Year yyyy)
        Font headFont = Resource.FONT_MEDIUM_BOLD;
        GraphicUtils.drawText(g, header, headFont, ScreenDetection.getCanvasWidth() / 2, PADDING_TOP, headerDayColor, Align.Center);

        GraphicUtils.setColor(g, lineColor);
        g.setStrokeStyle(Graphics.DOTTED);
        //for (int i = 0; i <= rows; i++) {
        for (int i = 1; i < rows; i++) {
            //g.drawLine(0, i * cellHeight + PADDING_TOP, cols * cellWidth, i * cellHeight + PADDING_TOP);
        }

        //for (int i = 0; i <= cols; i++) {
        for (int i = 1; i < cols; i++) {
            g.drawLine(i * cellWidth, 0 + PADDING_TOP + SUB_HEIGHT, i * cellWidth, rows * cellHeight + PADDING_TOP);
        }

        int oldColor = g.getColor();
        GraphicUtils.setColor(g, cellFocusColor);
        g.fillRoundRect((currentX * cellWidth) + 1, (currentY * cellHeight) + 1 + PADDING_TOP, cellWidth - 1, cellHeight - 1, ARC_CORNER_WIDTH, ARC_CORNER_HEIGHT);
        g.setColor(oldColor);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (solarCalendarData[i][j] != null && !Resource.EMPTY.equals(solarCalendarData[i][j])) {
                    // store clipping properties
                    int oldClipX = g.getClipX();
                    int oldClipY = g.getClipY();
                    int oldClipWidth = g.getClipWidth();
                    int oldClipHeight = g.getClipHeight();
                    g.setClip((j * cellWidth) + 1, i * cellHeight + PADDING_TOP, cellWidth - 1, cellHeight - 1);

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

                    g.setFont(Resource.FONT_MEDIUM_PLAIN);

                    if (i == 0) {
                        g.drawString(solarCalendarData[i][j], (int) (j * cellWidth + cellWidth / 2), (int) (i * cellHeight + cellHeight / 2 + PADDING_TOP + SUB_HEIGHT),
                                Graphics.BASELINE | Graphics.HCENTER);
                    } else {
                        g.drawString(solarCalendarData[i][j], (j * cellWidth) + 1, ((i + 1) * cellHeight + PADDING_TOP),
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

                    g.setFont(Resource.FONT_SMALL_PLAIN);
                    g.drawString(lunarCalendarData[i][j], (j + 1) * cellWidth, ((i + 0) * cellHeight) - 3 + PADDING_TOP,
                            Graphics.TOP | Graphics.RIGHT);

                    // Draw yearly event day
                    try {
                        if (lunarCalendarData[i][j] != null && !Resource.EMPTY.equals(lunarCalendarData[i][j])) {
                            int[] dmy = LunarCalendarUtils.convertSolar2Lunar(Integer.parseInt(solarCalendarData[i][j]), currentMonth, currentYear, LunarCalendarUtils.TIME_ZONE);

                            // Check the date has event?
                            if (LunarCalendarUtils.hasYearlyEvents(dmy[0], dmy[1])) {
                                // Draw backgroundImage
                                if (backgroundImage != null) {
                                    g.drawImage(eventImage, j * cellWidth + 2, i * cellHeight + 2 + PADDING_TOP,
                                            Graphics.TOP | Graphics.LEFT);
                                } else {
                                    GraphicUtils.setColor(g, eventColor);
                                    g.drawString(EVENT_SYMBOL, j * cellWidth + 2, i * cellHeight - 4 + PADDING_TOP,
                                            Graphics.TOP | Graphics.LEFT);
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    // Draw special yearly event day
                    try {
                        if (solarCalendarData[i][j] != null && !Resource.EMPTY.equals(solarCalendarData[i][j])) {
                            // Check the date has event?
                            if (LunarCalendarUtils.hasSpecialYearlyEvents(Integer.parseInt(solarCalendarData[i][j]), currentMonth)) {
                                if (backgroundImage != null) {
                                    g.drawImage(eventImage, j * cellWidth + 2, i * cellHeight + 2 + PADDING_TOP,
                                            Graphics.TOP | Graphics.LEFT);
                                } else {
                                    GraphicUtils.setColor(g, eventColor);
                                    g.drawString(EVENT_SYMBOL, j * cellWidth + 2, i * cellHeight - 4 + PADDING_TOP,
                                            Graphics.TOP | Graphics.LEFT);
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

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
                            repaint(currentX * cellWidth, (currentY - 1) * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                            repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
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
                            repaint(currentX * cellWidth, (currentY + 1) * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                            repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
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
                        repaint((currentX + 1) * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                        repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                    }

                    break;

                case Canvas.RIGHT:

                    if (currentX < (cols - 1)) {
                        // Check focusable for cell
                        if (Resource.EMPTY.equals(solarCalendarData[currentY][currentX + 1])) {
                            break;
                        }
                        currentX++;
                        repaint((currentX - 1) * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                        repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
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
                            repaint((currentX + 1) * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                            repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                        } else if (currentY > 0) {
                            currentY--;
                            repaint(currentX * cellWidth, (currentY + 1) * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                            currentX = cols - 1;
                            repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
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
                            repaint((currentX - 1) * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                            repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                        } else if (currentY < (rows - 1)) {
                            currentY++;
                            repaint(currentX * cellWidth, (currentY - 1) * cellHeight + PADDING_TOP, cellWidth, cellHeight);
                            currentX = 0;
                            repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP, cellWidth, cellHeight);
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
        visRect_inout[2] = cellWidth;
        visRect_inout[3] = cellHeight;

        return true;
    }

    public void setText(String text) {
        solarCalendarData[currentY][currentX] = text;
        repaint(currentY * cellWidth, currentX * cellHeight + PADDING_TOP, cellWidth, cellHeight);
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

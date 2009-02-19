package com.anchorman.lunarcalendar.ui;

import com.anchorman.lunarcalendar.drawing.Color;
import com.anchorman.lunarcalendar.entities.Day;
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
public class CalendarCanvas extends Canvas {

    private int rows = LunarCalendarUtils.NUMBER_ROW;
    private int cols = LunarCalendarUtils.NUMBER_COLUMN;
    private int cellWidth = ScreenDetection.getCanvasWidth() / cols;
    private int cellHeight = (ScreenDetection.getCanvasHeight() - PADDING_TOP) / rows;
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
    private Font headerFont = Resource.FONT_MEDIUM_BOLD;
    private Font weekFont = Resource.FONT_MEDIUM_BOLD;
    private Font solarDayFont = Resource.FONT_MEDIUM_BOLD;
    private Font lunarDayFont = Resource.FONT_SMALL_PLAIN;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private static int PADDING_TOP = 25;
    private static int SUB_HEIGHT = 10;
    private static int CELL_ARC_CORNER_WIDTH = 7;
    private static int CELL_ARC_CORNER_HEIGHT = 7;
    private static int BORDER_ARC_CORNER_WIDTH = 30;
    private static int BORDER_ARC_CORNER_HEIGHT = 30;
    private String header;
    private Image backgroundImage;
    private Image eventImage;
    /**
     * showMode = -1: Show lunar month
     * showMode = 1 : Show solar month
     * showMode = 0 : Show lunar & solar month
     */
    private int showMode = 1;
    private Day[][] data;

    public int getShowMode() {
        return showMode;
    }

    public void setShowMode(int showMode) {
        this.showMode = showMode;
        repaint();
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

    public CalendarCanvas() {
        data = new Day[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new Day();
            }
        }

        int[] ymd = DateUtils.getCurrentYearMonthDate();
        currentYear = ymd[0];
        currentMonth = ymd[1];
        currentDay = ymd[2];

        // Load backgroundImage image
        if (backgroundImage == null) {
            try {
                backgroundImage = Image.createImage(Resource.BACKGROUND_IMAGE_PATH);
                int width = ScreenDetection.getCanvasWidth();
                int height = ScreenDetection.getCanvasHeight();
                backgroundImage = GraphicUtils.scaleImage(backgroundImage,
                        width, height);
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

    private void fillDataForMonth(int year, int month) {
        solarCalendarData = LunarCalendarUtils.getSolarMonth(year, month);
        lunarCalendarData = LunarCalendarUtils.getLunarMonth(year, month);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j].setSolarDay(solarCalendarData[i][j]);
                data[i][j].setLunarDay(lunarCalendarData[i][j]);
                data[i][j].setHasEvent(false);
                data[i][j].setHasEventSpecial(false);
            }
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String day = data[i][j].getSolarDay();
                if (day == null || Resource.EMPTY.equals(day))
                    continue;
                int[] dmy = LunarCalendarUtils.convertSolar2Lunar(
                        Integer.parseInt(data[i][j].getSolarDay()),
                        currentMonth, currentYear,
                        LunarCalendarUtils.TIME_ZONE);

                // Check the date has event?
                if (LunarCalendarUtils.hasYearlyEvents(dmy[0], dmy[1])) {
                    data[i][j].setHasEvent(true);
                }

                if (LunarCalendarUtils.hasSpecialYearlyEvents(
                        Integer.parseInt(data[i][j].getSolarDay()),
                        currentMonth)) {
                    data[i][j].setHasEventSpecial(true);
                }
            }
        }

        header = "Thang " + currentMonth + " n?m " + currentYear;

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
        repaint(currentX * cellWidth, currentY * cellHeight, cellWidth,
                cellHeight);
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
        //repaint(currentX * cellWidth, currentY * cellHeight + PADDING_TOP,
        //		cellWidth, cellHeight);
        repaint();
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

    public void gotoMonth(int year, int month) {
        currentMonth = month;
        currentYear = year;
        fillDataForMonth(year, month);
    }

    protected void drawBorder(Graphics g) {
        // Draw border
        int x1 = 0;
        int y1 = 0;
        int x2 = ScreenDetection.getCanvasWidth() - x1 * 2;
        int y2 = ScreenDetection.getCanvasHeight() - y1 * 2;
        g.drawRoundRect(x1, y1, x2, y2, BORDER_ARC_CORNER_WIDTH, BORDER_ARC_CORNER_HEIGHT);
        g.drawRoundRect(x1 + 1, y1 + 1, x2 - 2, y2 - 2, BORDER_ARC_CORNER_WIDTH, BORDER_ARC_CORNER_HEIGHT);
    }

    protected void drawBackground(Graphics g) {
        // Draw backgroundImage
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Graphics.TOP | Graphics.LEFT);
        // g.drawRegion(backgroundImage, 0, 0,
        // backgroundImage.getWidth(), backgroundImage.getHeight(),
        // Sprite.TRANS_NONE, h, h, h);
        }
    }

    protected void drawHeader(Graphics g) {
        // Draw header of calendar (Month mm Year yyyy)
        Font headFont = Resource.FONT_MEDIUM_BOLD;
        GraphicUtils.drawText(g, header, headFont, ScreenDetection.getCanvasWidth() / 2, PADDING_TOP, headerDayColor,
                Align.Center);
    }

    protected void drawGrid(Graphics g) {
        GraphicUtils.setColor(g, lineColor);
        g.setStrokeStyle(Graphics.DOTTED);

//        for (int i = 1; i < rows; i++) {
//            g.drawLine(0, i * cellHeight + PADDING_TOP, cols * cellWidth, i *
//            cellHeight + PADDING_TOP);
//        }

        for (int i = 1; i < cols; i++) {
            g.drawLine(i * cellWidth, 0 + PADDING_TOP + SUB_HEIGHT, i * cellWidth, rows * cellHeight + PADDING_TOP);
        }
    }

    protected void drawCellHighLight(Graphics g) {
        int oldColor = g.getColor();
        GraphicUtils.setColor(g, cellFocusColor);
        g.fillRoundRect((currentX * cellWidth) + 1, (currentY * cellHeight) + 1 + PADDING_TOP, cellWidth - 1, cellHeight - 1, CELL_ARC_CORNER_WIDTH,
                CELL_ARC_CORNER_HEIGHT);
        g.setColor(oldColor);
    }

    protected void drawSonarCalendarMonth(Graphics g, boolean drawWeek) {

        if (drawWeek) {
            // Draw header day of week
            g.setFont(weekFont);
            GraphicUtils.setColor(g, headerDayColor);

            for (int j = 0; j < cols; j++) {
                int i = 0;
                String day = data[i][j].getSolarDay();
                g.drawString(day, (int) (j * cellWidth + cellWidth / 2),
                        (int) (i * cellHeight + cellHeight / 2 + PADDING_TOP + SUB_HEIGHT),
                        Graphics.BASELINE | Graphics.HCENTER);
            }
        }

        g.setFont(solarDayFont);
        GraphicUtils.setColor(g, solarDayColor);

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String day = data[i][j].getSolarDay();

                if (day != null && !Resource.EMPTY.equals(day)) {
                    if (j == 0) {
                        // Set font color for sunday
                        GraphicUtils.setColor(g, solarSundayColor);
                    } else if (j == cols - 1) {
                        // Set font color for saturday
                        GraphicUtils.setColor(g, solarSaturdayColor);
                    } else {
                        GraphicUtils.setColor(g, solarDayColor);
                    }

                    // Draw solar day
                    g.drawString(day, (j * cellWidth) + 1,
                            ((i + 1) * cellHeight + PADDING_TOP),
                            Graphics.BOTTOM | Graphics.LEFT);
                }
            }
        }
    }

    protected void drawLunarCalendarMonth(Graphics g, boolean drawWeek) {

        if (drawWeek) {
            // Draw header day of week
            g.setFont(weekFont);
            GraphicUtils.setColor(g, headerDayColor);

            for (int j = 0; j < cols; j++) {
                int i = 0;
                String day = data[i][j].getLunarDay();
                g.drawString(day, (int) (j * cellWidth + cellWidth / 2),
                        (int) (i * cellHeight + cellHeight / 2 + PADDING_TOP + SUB_HEIGHT),
                        Graphics.BASELINE | Graphics.HCENTER);
            }
        }

        g.setFont(lunarDayFont);
        GraphicUtils.setColor(g, lunarDayColor);

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String day = data[i][j].getLunarDay();

                if (day != null && !Resource.EMPTY.equals(day)) {
                    if (j == 0) {
                        // Set font color for sunday
                        GraphicUtils.setColor(g, lunarSundayColor);
                    } else if (j == cols - 1) {
                        // Set font color for saturday
                        GraphicUtils.setColor(g, lunarSaturdayColor);
                    } else {
                        GraphicUtils.setColor(g, lunarDayColor);
                    }

                    // Draw lunar day                    
                    g.drawString(day, (j + 1) * cellWidth,
                            ((i + 0) * cellHeight) - 3 + PADDING_TOP,
                            Graphics.TOP | Graphics.RIGHT);
                }
            }
        }
    }

    protected void drawEventIcon(Graphics g) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (data[i][j].isHasEvent()) {
                    if (eventImage != null) {
                        g.drawImage(eventImage, j * cellWidth + 2,
                                i * cellHeight + 2 + PADDING_TOP,
                                Graphics.TOP | Graphics.LEFT);
                    } else {
                        GraphicUtils.setColor(g, eventColor);
                        g.drawString(Resource.EVENT_SYMBOL, j * cellWidth + 2, i * cellHeight - 4 + PADDING_TOP, Graphics.TOP | Graphics.LEFT);
                    }
                } else if (data[i][j].isHasEventSpecial()) {
                    if (eventImage != null) {
                        g.drawImage(eventImage, j * cellWidth + 2,
                                i * cellHeight + 2 + PADDING_TOP,
                                Graphics.TOP | Graphics.LEFT);
                    } else {
                        GraphicUtils.setColor(g, eventColor);
                        g.drawString(Resource.EVENT_SYMBOL, j * cellWidth + 2, i * cellHeight - 4 + PADDING_TOP, Graphics.TOP | Graphics.LEFT);
                    }
                }
            }
        }
    }

    protected void paint(Graphics g) {
        try {
            drawBackground(g);
            //drawBorder(g);
            drawHeader(g);
            drawGrid(g);
            drawCellHighLight(g);

            drawSonarCalendarMonth(g, true);
            drawLunarCalendarMonth(g, false);
            drawEventIcon(g);
        } catch (Exception ex) {
        }
    }

    protected void keyPressed(int keyCode) {
        // TODO Auto-generated method stub
        super.keyPressed(keyCode);
        //String keyValue = getKeyName(keyCode);

        //if ("DOWN".equals(keyValue)) {
        if (keyCode == -2 || keyCode == Canvas.KEY_NUM8) {
            if (currentY < (rows - 1)) {
                // Check focusable for cell
                if (Resource.EMPTY.equals(solarCalendarData[currentY + 1][currentX])) {
                    return;
                }

                currentY++;
                repaint();
            }
        }
        //if ("UP".equals(keyValue)) {
        if (keyCode == -1 || keyCode == Canvas.KEY_NUM2) {

            if (currentY > 1) {
                // Check focusable for cell
                if (Resource.EMPTY.equals(solarCalendarData[currentY - 1][currentX])) {
                    return;
                }
                currentY--;
                repaint();
            }

        }
        //if ("LEFT".equals(keyValue)) {
        if (keyCode == -3 || keyCode == Canvas.KEY_NUM4) {
            if (currentX > 0) {
                // Check focusable for cell
                if (Resource.EMPTY.equals(solarCalendarData[currentY][currentX - 1])) {
                    return;
                }

                currentX--;
                repaint();
            }
        }
        //if ("RIGHT".equals(keyValue)) {
        if (keyCode == -4 || keyCode == Canvas.KEY_NUM6) {
            if (currentX < (cols - 1)) {
                // Check focusable for cell
                if (Resource.EMPTY.equals(solarCalendarData[currentY][currentX + 1])) {
                    return;
                }
                currentX++;
                repaint();
            }
        }
    }
}

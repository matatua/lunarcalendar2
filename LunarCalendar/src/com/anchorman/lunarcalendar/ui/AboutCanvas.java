/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anchorman.lunarcalendar.ui;

import com.anchorman.lunarcalendar.drawing.Color;
import com.anchorman.lunarcalendar.platform.ScreenDetection;
import com.anchorman.lunarcalendar.properties.Resource;
import com.anchorman.lunarcalendar.util.GraphicUtils;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author NamNT2
 */
public class AboutCanvas extends Canvas implements CommandListener {

    private static final Command CMD_CLOSE = new Command(Resource.CLOSE_TEXT, Command.SCREEN, 1);
    private Display display;
    private Displayable parent;
    private Image backgroundImage;
    private Image iconImage;
    private Color stringColor = new Color(8,9,54);
    private static int BORDER_ARC_CORNER_WIDTH = 7;
    private static int BORDER_ARC_CORNER_HEIGHT = 7;
    
    private void initialize() {
        addCommand(CMD_CLOSE);
        setCommandListener(this);
        
        setFullScreenMode(false);
    }

    public AboutCanvas(Displayable parent, Display display) {
        this.parent = parent;
        this.display = display;

        initialize();
    }

    public void commandAction(Command command, Displayable d) {
        if (command == CMD_CLOSE) {
            display.setCurrent(parent);
        }
    }

    protected void paint(Graphics g) {
    	int x = ScreenDetection.getCanvasWidth() / 2;
    	int y = ScreenDetection.getCanvasHeight() / 2;
         // Load background image
        if (backgroundImage == null) {
            try {
                backgroundImage = Image.createImage(Resource.ABOUT_IMAGE_PATH);
                //backgroundImage = GraphicUtils.scaleImage(backgroundImage, ScreenDetection.getCanvasWidth(), ScreenDetection.getCanvasHeight());
            } catch (Exception e) {
            	try{
            		backgroundImage = Image.createImage(Resource.BACKGROUND_IMAGE_PATH);
            		backgroundImage = GraphicUtils.scaleImage(backgroundImage, ScreenDetection.getCanvasWidth(), ScreenDetection.getCanvasHeight());
            	}catch(Exception ex){
            	}
            }
        }
        // Draw background
        if (backgroundImage != null){
            x = (ScreenDetection.getCanvasWidth() - backgroundImage.getWidth()) / 2;
            y = (ScreenDetection.getCanvasHeight() - backgroundImage.getHeight()) / 2;
            g.drawImage(backgroundImage, x, y, Graphics.TOP | Graphics.LEFT);
        }
        
        // Draw border
        int x1 = ScreenDetection.getCanvasWidth() / 18;
        int y1 = x1;
        int x2 = ScreenDetection.getCanvasWidth() - x1 * 2;
        int y2 = ScreenDetection.getCanvasHeight() - y1 * 2;
        g.drawRoundRect(x1, y1, x2, y2, BORDER_ARC_CORNER_WIDTH, BORDER_ARC_CORNER_HEIGHT);
        g.drawRoundRect(x1+1, y1+1, x2-2, y2-2, BORDER_ARC_CORNER_WIDTH, BORDER_ARC_CORNER_HEIGHT);
        
        // Load icon
        if (iconImage == null) {
            try {
            	iconImage = Image.createImage(Resource.ICON_IMAGE_PATH);
            } catch (Exception e) {
            }
        }
        // Draw icon
        if (iconImage != null){
            x = (ScreenDetection.getCanvasWidth() - iconImage.getWidth()) / 2;
            y = (ScreenDetection.getCanvasHeight() - iconImage.getHeight()) / 5;
            g.drawImage(iconImage, x, y, Graphics.TOP | Graphics.LEFT);
        }
        
        // Draw application name
        GraphicUtils.setColor(g, stringColor);
        x =  ScreenDetection.getCanvasWidth() / 2;
        y += iconImage != null ? iconImage.getHeight() * 4/3 : - y / 2;
        g.setFont(Resource.FONT_LARGE_PLAIN_BOLD);
        g.drawString(Resource.APPLICATION_NAME, x, y, Graphics.BASELINE | Graphics.HCENTER);
        
        // Draw version
        y += g.getFont().getHeight() * 2 / 3;
        g.setFont(Resource.FONT_SMALL_PLAIN);
        g.drawString(Resource.APPLICATION_VERSION, x, y, Graphics.BASELINE | Graphics.HCENTER);
        
        // Draw copy right
        g.setFont(Resource.FONT_MEDIUM_PLAIN);
        y = y2 - g.getFont().getHeight() * 2 / 3;
        g.drawString(Resource.COPY_RIGHT, x, y, Graphics.BASELINE | Graphics.HCENTER);
        
        // Draw over email
        y += g.getFont().getHeight();
        g.drawString(Resource.OWNER_EMAIL, x, y, Graphics.BASELINE | Graphics.HCENTER);
        
    }
}

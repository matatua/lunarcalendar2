/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anchorman.lunarcalendar.ui;

import com.anchorman.lunarcalendar.platform.ScreenDetection;
import com.anchorman.lunarcalendar.properties.Resource;
import java.util.Timer;
import java.util.TimerTask;
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
public class SplashCanvas extends Canvas {

    private Display display;
    private Displayable next;
    private Timer timer = new Timer();
    private Image image;
    private static int INTERVAL_TIME = 2000;

    public SplashCanvas(Display display, Displayable next) {
        this.next = next;
        this.display = display;

        display.setCurrent(this);
    }

    protected void paint(Graphics g) {
        // Load background image
        if (image == null) {
            try {
                image = Image.createImage(Resource.ABOUT_IMAGE_PATH);
            } catch (Exception e) {
            }
        }
        // Draw background
        if (image != null) {
            int x, y;
            x = (ScreenDetection.getCanvasWidth() - image.getWidth()) / 2;
            y = (ScreenDetection.getCanvasHeight() - image.getHeight()) / 2;
            g.drawImage(image, x, y, Graphics.TOP | Graphics.LEFT);
        }
    }

    protected void showNotify() {
        timer.schedule(new CountDown(), INTERVAL_TIME);
    }

    protected void keyPressed(int keyCode) {
        dismiss();
    }

    protected void pointerPressed(int x, int y) {
        dismiss();
    }

    private void dismiss() {
        timer.cancel();
        display.setCurrent(next);
    }

    private class CountDown extends TimerTask {

        public void run() {
            dismiss();
        }
    }
}

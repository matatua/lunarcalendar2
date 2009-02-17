/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anchorman.lunarcalendar.platform;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author NamNT2
 */
public class ScreenDetection {

    private static int canvasWidth = 0,  canvasHeight = 0;
    private static int formWidth = 0,  formHeight = 0;


    static {
        if (canvasWidth == 0 || canvasHeight == 0) {
            Canvas canvas = new Canvas() {

                protected void paint(Graphics arg0) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            };
            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();
            
            Form screen = new Form("");
            formWidth = screen.getWidth();
            formHeight = screen.getHeight();
        }
    }

    public static int getCanvasWidth() {
        return canvasWidth;
    }

    public static int getCanvasHeight() {
        return canvasHeight;
    }

    public static int getFormWidth() {
        return formWidth;
    }

    public static int getFormHeight() {
        return formHeight;
    }
}

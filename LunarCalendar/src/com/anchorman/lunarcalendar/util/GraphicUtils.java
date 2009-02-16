/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anchorman.lunarcalendar.util;

import javax.microedition.lcdui.Graphics;
import com.anchorman.lunarcalendar.drawing.Color;
import javax.microedition.lcdui.Font;

/**
 *
 * @author NamNT2
 */
public class GraphicUtils {
    public static void setColor(Graphics g, Color color) {
        g.setColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static void drawText(Graphics g, String text, Font font, int x, int y, Color color, int align)
    {
        int oldColor = g.getColor();
        Font oldFont = g.getFont();
        g.setColor(color.getRed(), color.getGreen(), color.getBlue());
        g.setFont(font);
        g.drawString(text, x, y, align | Graphics.HCENTER);
        
        g.setColor(oldColor);
        g.setFont(oldFont);
    }
}

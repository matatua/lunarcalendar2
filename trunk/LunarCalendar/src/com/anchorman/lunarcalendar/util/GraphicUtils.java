/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anchorman.lunarcalendar.util;

import javax.microedition.lcdui.Graphics;
import com.anchorman.lunarcalendar.drawing.Color;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

/**
 *
 * @author NamNT2
 */
public class GraphicUtils {

    public static void setColor(Graphics g, Color color) {
        g.setColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static void drawText(Graphics g, String text, Font font, int x, int y, Color color, int align) {
        int oldColor = g.getColor();
        Font oldFont = g.getFont();
        g.setColor(color.getRed(), color.getGreen(), color.getBlue());
        g.setFont(font);
        g.drawString(text, x, y, align | Graphics.HCENTER);

        g.setColor(oldColor);
        g.setFont(oldFont);
    }

    /**
     * Creates a new, scaled version of the given image.
     *
     * @param src: The source image
     * @param dstW: The destination (scaled) image width
     * @param dstH: The destination (scaled) image height
     * @return Image: A new Image object with the given width and height.
     */
    public static Image scaleImage(Image src, int dstW, int dstH) {
        int srcW = src.getWidth();
        int srcH = src.getHeight();

        Image tmp = Image.createImage(dstW, srcH);
        Graphics g = tmp.getGraphics();

        int delta = (srcW << 16) / dstW;
        int pos = delta / 2;

        for (int x = 0; x < dstW; x++) {
            g.setClip(x, 0, 1, srcH);
            g.drawImage(src, x - (pos >> 16), 0, Graphics.LEFT | Graphics.TOP);
            pos += delta;
        }

        Image dst = Image.createImage(dstW, dstH);
        g = dst.getGraphics();

        delta = (srcH << 16) / dstH;
        pos = delta / 2;

        for (int y = 0; y < dstH; y++) {
            g.setClip(0, y, dstW, 1);
            g.drawImage(tmp, 0, y - (pos >> 16), Graphics.LEFT | Graphics.TOP);
            pos += delta;
        }

        return dst;
    }
}

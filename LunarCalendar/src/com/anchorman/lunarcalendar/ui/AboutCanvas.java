/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anchorman.lunarcalendar.ui;

import com.anchorman.lunarcalendar.properties.Resource;
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
    private Form parent;
    private Image image;

    private void initialize() {
        addCommand(CMD_CLOSE);
        setCommandListener(this);
    }

    /*
    public AboutCanvas(String title) {
        super(title);
        initialize();
    }
*/
    public AboutCanvas(Form parent, Display display) {      
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
         // Load background image
        if (image == null) {
            try {
                image = Image.createImage(Resource.ABOUT_IMAGE_PATH);
            } catch (Exception e) {
            }
        }
        // Draw background
        if (image != null){
            g.drawImage(image, 0, 0, Graphics.TOP | Graphics.LEFT);
        }
    }
}

package MRDControl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.Timer;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Armando
 */
public class RoomIcon extends JLabel {

    static Font fontAwesome;
    static float fontSize = Float.parseFloat(Config.propiedades.getProperty("icono","130"));
    static Color FREE = new java.awt.Color(0, 102, 0);
    static Color BUSSY = new java.awt.Color(204, 0, 0);
    static Color UNAVAILABLE = Color.gray;
    private int number;
    private boolean active;
    private boolean blink;
    private final Timer timer;

    public boolean isBlink() {
        return blink;
    }

    public void setBlink(boolean blink) {
        this.blink = blink;
        if (blink) {
            setBusy();
        } else {
            setFree();
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    static {
        try {
            InputStream in = RoomIcon.class.getClassLoader().getResourceAsStream("fontawesome-webfont.ttf");
            fontAwesome = Font.createFont(Font.TRUETYPE_FONT, in);
        } catch (FontFormatException | IOException ex) {
            System.out.println("Could not load font");
        }
    }

    public RoomIcon() {
        timer = new Timer(300, new Blinker(this)); 
        timer.setInitialDelay(0);
        setFont(getFontVariant(500));
        setForeground(RoomIcon.BUSSY);
        setText("\uf015");
        setActive(true);
    }

    public void startBlinking() {
        timer.start();
    }

    public void stopBlinking() {
        timer.stop();
    }

    public RoomIcon(int number) {
        this();
        this.number = number;
    }

    public static Font getFontVariant(float size) {
        return fontAwesome.deriveFont(Font.PLAIN, RoomIcon.fontSize);
    }

    public void setBusy() {
        setForeground(RoomIcon.BUSSY);
    }

    public void setUnavailable() {
        setForeground(RoomIcon.UNAVAILABLE);
    }

    public void setFree() {
        setForeground(RoomIcon.FREE);
    }

    public void blink() {
        setBlink(!isBlink());
    }
}


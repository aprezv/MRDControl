/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRDControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Armando
 */
public class NoiseCanceller implements ActionListener {

    RoomIcon roomIcon;
    RoomPanel panel;
    Timer timerToSave;

    public NoiseCanceller(RoomIcon roomIcon, RoomPanel panel, Timer timerToSave) {
        this.roomIcon = roomIcon;
        this.panel = panel;
        this.timerToSave = timerToSave;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        roomIcon.setBusy();
        roomIcon.stopBlinking();
        panel.removeArrow();
        timerToSave.stop();
    }

}

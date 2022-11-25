/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRDControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Armando
 */
class SetReadyToSave implements ActionListener {

    private OccupancyRecord or;
    private RoomPanel panel;

    public SetReadyToSave(OccupancyRecord or, RoomPanel panel) {
        this.panel = panel;
        this.or = or;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.getIcon().stopBlinking();
        panel.getIcon().setBusy();
        panel.removeArrow();
        or.setAbleForSave(true);
    }
}

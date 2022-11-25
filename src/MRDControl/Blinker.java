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
class Blinker implements ActionListener {

    private RoomIcon icon;

    public Blinker(RoomIcon icon) {
        this.icon = icon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        icon.blink();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRDControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 *
 * @author Armando
 */
class WaitForSave implements ActionListener {

    private OccupancyRecordDAO dao;
    private OccupancyRecord or;
    private RoomPanel panel;

    public WaitForSave(OccupancyRecordDAO dao, OccupancyRecord or, RoomPanel panel) {
        this.panel = panel;
        this.dao = dao;
        this.or = or;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.getIcon().stopBlinking();
        panel.removeArrow();
        dao.save(or);
        panel.getIcon().setFree();
        panel.stopStopWatch();
        panel.continueStopWatch();
        or.setStartTime(null); 
        or.setAbleForSave(false);
    }
}

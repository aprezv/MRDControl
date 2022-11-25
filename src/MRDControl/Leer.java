package MRDControl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

class Leer implements Runnable {

    RoomIcon roomIcon;
    RoomPanel panel;
    SerialTest serial;
    private OccupancyRecord or;
    private OccupancyRecordDAO dao;
    Timer timerToSetReady;
    Timer timerToSave;
    Timer tt;
    boolean canSave = false;
    char defaultStatus = '0';

    public Leer(RoomPanel room, SerialTest serial, OccupancyRecordDAO dao) {
        this.serial = serial;
        this.roomIcon = room.getIcon();
        this.dao = dao;
        this.panel = room;
        or = new OccupancyRecord(room.getIcon().getNumber());
        int doorDown = Integer.valueOf(Config.propiedades.getProperty("doorDown","1"))*1000*60;
        int doorUp = Integer.valueOf(Config.propiedades.getProperty("doorUp","1"))*1000*60;
        //timerToSetReady = new Timer(Config.timeParams.getDoorDown(), new SetReadyToSave(or, room));
        //timerToSave = new Timer(Config.timeParams.getDoorUp(), new WaitForSave(dao, or, panel));
        
        timerToSetReady = new Timer(doorDown, new SetReadyToSave(or, room));
        timerToSave = new Timer(doorUp, new WaitForSave(dao, or, panel));
        
        timerToSetReady.setRepeats(false);
        timerToSave.setRepeats(false);
        try{
            defaultStatus = Config.propiedades.getProperty("entrada").toCharArray()[0];
        }catch(Exception e){}
        
    }

    @Override
    public void run() {
        char oldData = 'n';
        while (true) {
            String data = serial.getData();         
            char[] dataChar = data.toCharArray();
            if (dataChar.length == 66) {
                if (oldData != dataChar[roomIcon.getNumber() - 1]) {
                    oldData = dataChar[roomIcon.getNumber() - 1];
                    if (dataChar[roomIcon.getNumber() - 1] == '0') {

                        tt = new Timer(0, new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                panel.startStopWatch();
                                if (or.getStartTime() == null) {
                                    roomIcon.startBlinking();
                                    panel.setDownArrow();
                                    timerToSetReady.restart();
                                    or.setStartTime(new Date());
                                    or.setEndTime(null);
                                } else {
                                    roomIcon.setBusy();
                                    roomIcon.stopBlinking();
                                    panel.removeArrow();
                                    timerToSave.stop();
                                }
                                canSave = true;
                            }
                        });
                        tt.setRepeats(false);
                        tt.setInitialDelay(3000);
                        tt.start();

                    } else {
                        if (tt != null) {
                            tt.stop();
                        }
                        if (!or.isAbleForSave()) {
                            panel.stopStopWatch();
                            roomIcon.stopBlinking();
                            or.setStartTime(null);
                            roomIcon.setFree();
                            panel.removeArrow();
                            timerToSetReady.stop();
                        } else {
                            panel.pauseStopWatch();
                            panel.setUpArrow();
                            roomIcon.startBlinking();
                            or.setEndTime(new Date());

                            if (canSave) {

                                timerToSave.restart();
                            }
                            canSave = false;

                        }
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }

        }
    }
}

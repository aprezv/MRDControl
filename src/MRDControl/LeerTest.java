package MRDControl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

class LeerTest implements Runnable {

    RoomIcon roomIcon;
    RoomPanel panel;
    SerialTest serial;
    private OccupancyRecord or;
    private OccupancyRecordDAO dao;
    Timer timerToSetReady;
    Timer timerToSave;
    Timer noiseTimer;/////////////////////////////
    Scanner sc;
    Timer tt;
    boolean canSave = false;

    public LeerTest(RoomPanel room, OccupancyRecordDAO dao) {
        this.roomIcon = room.getIcon();
        this.dao = dao;
        this.panel = room;
        or = new OccupancyRecord(room.getIcon().getNumber());
        timerToSetReady = new Timer(5000, new SetReadyToSave(or, room));
        timerToSave = new Timer(5000, new WaitForSave(dao, or, panel));
        timerToSetReady.setRepeats(false);
        timerToSave.setRepeats(false);
        noiseTimer = new Timer(10000, new NoiseCanceller(roomIcon, panel, timerToSave));///////////////////
        noiseTimer.setRepeats(false);
        sc = new Scanner(System.in);

    }

    @Override

    public void run() {
        int oldData = '1';
        while (true) {

            int lectura = sc.nextInt();
            if (oldData != lectura) {
                oldData = lectura;
                if (lectura == 1) {

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
                        roomIcon.stopBlinking();
                        or.setStartTime(null);
                        roomIcon.setFree();
                        panel.removeArrow();
                        timerToSetReady.stop();
                        panel.stopStopWatch();
                    } else {
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

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }

        }
    }
}

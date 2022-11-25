package MRDControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Armando
 */
public class RoomPanel extends JPanel {

    private RoomIcon icon;
    JLabel l;
    JLabel arrow;
    JLabel timer;
    StopWatch stopWatch;
    Timer stopWatchPaiter;

    public JLabel getArrow() {
        return arrow;
    }

    public void setArrow(JLabel arrow) {
        this.arrow = arrow;
    }

    public void setDownArrow() {
        this.arrow.setText("                   \uf063");
    }

    public void setUpArrow() {
        this.arrow.setText("                   \uf062");
    }

    public void removeArrow() {
        this.arrow.setText("");
    }

    public RoomIcon getIcon() {
        return icon;
    }

    public void setIcon(RoomIcon icon) {
        this.icon = icon;
        l.setText(String.valueOf(icon.getNumber()));
        JPanel pan = new JPanel(new BorderLayout());
        pan.add(l, BorderLayout.LINE_START);
        pan.add(arrow, BorderLayout.LINE_END);
        add(pan, BorderLayout.PAGE_START);
        this.icon.setHorizontalAlignment(SwingConstants.CENTER);
        add(this.icon, BorderLayout.CENTER);
        timer.setHorizontalAlignment(SwingConstants.CENTER);
        add(timer, BorderLayout.PAGE_END);
    }

    public RoomPanel() {

        stopWatch = new StopWatch();
        //stopWatch.start();
        timer = new JLabel();
        timer.setFont(new java.awt.Font("Tahoma", 0, 25));
        stopWatchPaiter = new Timer(1000, new StopWatchRefresher(timer, stopWatch));
        stopWatchPaiter.setInitialDelay(0);
        stopWatchPaiter.start();

        l = new JLabel();
        l.setFont(null);
        l.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        l.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        l.setForeground(Color.BLACK);
        l.setSize(20, 40);
        arrow = new JLabel();
        arrow.setFont(RoomIcon.fontAwesome.deriveFont(Font.PLAIN, 20));
        arrow.setForeground(new Color(105, 00, 104));
        Border border = javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, RoomIcon.fontAwesome);
        setBorder(border);
        setLayout(new BorderLayout(0, 0));
        setFont(RoomIcon.fontAwesome);
    }
    public void startStopWatch() {
        if(!stopWatchPaiter.isRunning()){
            stopWatchPaiter.restart();
        }
        if (stopWatch.isStopped()) {
            stopWatch.reset();
            stopWatch.start();
        }
    }
    public void resumeStopWatch() {
        stopWatch.resume();
    }

    public void stopStopWatch() {
        if (stopWatch.isStarted()) {
            stopWatch.stop();
            stopWatch.reset();
        }
    }
    
    public void pauseStopWatch(){
        stopWatchPaiter.stop();
    }
    
    public void continueStopWatch(){
        stopWatchPaiter.restart();
    }

}

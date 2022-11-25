/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MRDControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Armando
 */
public class StopWatchRefresher implements ActionListener{
    JLabel label;
    StopWatch stopWatch;

    @Override
    public void actionPerformed(ActionEvent e) {
        //stopWatch.split();
        String time = stopWatch.toString();
        label.setText(String.valueOf(time.substring(0, time.indexOf("."))));
        
    }
    
    public StopWatchRefresher(JLabel label, StopWatch stopWatch){
        this.label = label;
        this.stopWatch = stopWatch;
    }
    
}

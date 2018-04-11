/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcstopwatchfxml;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author Yihe Wang
 */
public class DigitalModel {

    private String digitalDisplay;
    private static String label;
    private SimpleDateFormat sdf;
    private Date time;
    private static Date recordOld;
    private static Date recordNew;
    private long timeDiff;
    private static boolean initialized;
    private static int count;
    
    public DigitalModel(){
        sdf = new SimpleDateFormat("hh:mm:ss.SS");
        time = new Date();
        digitalDisplay = "--：--:--.--";
        label = "--:--.--";
        initialized = true;
        count = 1;
    }
    
    public void updateMonitor(){
        time = new Date();
        digitalDisplay = sdf.format(time);
    }
    
    public void recordOnLabel(){
        if(initialized){
            recordOld = recordNew = new Date();
            label = "00:00:00.00";//first click do not have time different
            initialized = false;
        }
        else{
            recordOld = recordNew;
            recordNew = new Date();
            timeDiff = recordNew.getTime() - recordOld.getTime();//get the time different
            label = sdf.format(timeDiff);//set it to correct format
            ++count;
        }   
    }
    
    public void reset(){
        label = "--:--.--";
        digitalDisplay = "--:--:--.--";
        initialized = true;
    }
    
    public String getLabel(){
        return label;
    }
    
    public String getDigitalDisplay(){
        return digitalDisplay;
    }
    
    public boolean getInitialized(){
        return initialized;
    }
    
    public int getCount(){
        return count;
    }
}

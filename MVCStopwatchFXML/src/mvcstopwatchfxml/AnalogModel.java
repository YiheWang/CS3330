/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcstopwatchfxml;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author Yihe Wang
 */
public class AnalogModel {
    
    //private Timeline timeline;
    //private KeyFrame keyFrame; //Change resolution
    private double tickTimeInSeconds;
    private double angleDeltaPerSeconds;
    private double angleDeltaPerMinutes;
    private double angleDeltaPerHours;
    private double secondsElapsed;
    private double secondsRotation;
    private double minutesRotation;
    private double hoursRotation;
    
    public AnalogModel(){
        tickTimeInSeconds = 0.01;
        angleDeltaPerSeconds = 6.0;
        angleDeltaPerMinutes = 0.1;
        angleDeltaPerHours = (double)0.1/60;
        secondsElapsed = 0.0;
        secondsRotation = 0.0;
        minutesRotation = 0.0;
    } 
    
    public void updateMonitor() {
        secondsElapsed = secondsElapsed + tickTimeInSeconds;
        secondsRotation = secondsElapsed * angleDeltaPerSeconds;
        minutesRotation = secondsElapsed *angleDeltaPerMinutes;
        hoursRotation = secondsElapsed * angleDeltaPerHours;
    }
    
    public void reset() {
        //timeline.stop();
        secondsElapsed = 0.0;
        secondsRotation = 0.0;
        minutesRotation = 0.0;
        hoursRotation = 0.0;
    }
    
    public double getSecondsRotation(){
        return secondsRotation;
    }
    
    public double getTickTimeInSeconds(){
        return tickTimeInSeconds;
    }
    
    public double getMinutesRotation(){
        return minutesRotation;
    }
    
    public double getHoursRotation(){
        return hoursRotation;
    }
}

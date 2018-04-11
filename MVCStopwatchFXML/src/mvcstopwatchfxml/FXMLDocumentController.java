/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcstopwatchfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Yihe Wang
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ImageView hand1;
    @FXML
    private ImageView hand2;
    @FXML
    private ImageView hand3;
    @FXML
    private Button startStopButton;
    @FXML
    private Button recordResetButton;
    @FXML
    private Label digitalDisplay;
    @FXML
    private Label lap1;
    @FXML
    private Label lap2;
    @FXML
    private Label lap3;
    
    private AnalogModel analogModel;
    private DigitalModel digitalModel;
    private int count = 1;
    private Timeline timeline;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        analogModel = new AnalogModel(); 
        hand1.setRotate(0);
        hand2.setRotate(0);
        hand3.setRotate(0);
        
        digitalModel = new DigitalModel();
        
        timeline = new Timeline(new KeyFrame(Duration.millis(1000*analogModel.getTickTimeInSeconds()), (ActionEvent) -> {
            analogModel.updateMonitor();
            hand1.setRotate(analogModel.getSecondsRotation());
            hand2.setRotate(analogModel.getMinutesRotation());
            hand3.setRotate(analogModel.getHoursRotation());
            
            digitalModel.updateMonitor();
            digitalDisplay.setText("Digital Diaplay:" + digitalModel.getDigitalDisplay());
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    @FXML
    public void startStopMonitor(ActionEvent event) {
        if(!isRunning()) {
            timeline.play();
            startStopButton.setText("Stop");
            startStopButton.setStyle("-fx-background-color: #FF0000");
            recordResetButton.setText("Record");
        }
        else{
            timeline.pause();
            startStopButton.setText("Start");
            startStopButton.setStyle("-fx-background-color: #008000");
            recordResetButton.setText("Reset");
        }
    }
    
    @FXML
    public void recordResetMonitor(ActionEvent event) {
        if(!isRunning()) {
            hand1.setRotate(0);
            hand2.setRotate(0);
            hand3.setRotate(0);
            startStopButton.setText("Start");
            startStopButton.setStyle("-fx-background-color: #008000");
            recordResetButton.setText("Record");
            analogModel.reset();
            digitalModel.reset();
            lap1.setText("--:--.--");
            lap2.setText("--:--.--");
            lap3.setText("--:--.--");
            digitalDisplay.setText("--:--:--.--");
        }
        else {
            //record on the digital label
            digitalModel.recordOnLabel();
            if(!digitalModel.getInitialized()){
            switch(count%3){
                case 1:
                    lap1.setText("lap" + digitalModel.getCount() + ": " + digitalModel.getLabel());
                    ++count;
                    break;
                case 2:
                    lap2.setText("lap" + digitalModel.getCount() + ": " + digitalModel.getLabel());
                    ++count;
                    break;
                case 0:
                    lap3.setText("lap" + digitalModel.getCount() + ": " + digitalModel.getLabel());
                    ++count;
                    break;
            }
            }
            else{
                System.out.println("First Click!");
            }
        }
    }
    
    public boolean isRunning(){
        if(timeline != null){
            if(timeline.getStatus() == Animation.Status.RUNNING){
                return true;
            }
        }
        return false;
    }
    
}

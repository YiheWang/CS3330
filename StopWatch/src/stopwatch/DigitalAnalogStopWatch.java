/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stopwatch;


import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Yihe Wang
 */
public class DigitalAnalogStopWatch {
    
    private double tickTimeInSeconds = 0.01;
    private double angleDeltaPerSeconds = 6.0;
    private double secondsElapsed = 0;
    
    private Timeline timeline;
    private KeyFrame keyFrame;
    
    private StackPane rootContainer;
    private ImageView dialImageView;
    private ImageView handImageView;
    private Image dialImage;
    private Image handImage;
    private String dialImageName = "clockface.png";
    private String handImageName = "hand.png";
    
    private VBox controls;
    
    private Button start;
    private Button stop;
    private Button record;
    private Button reset;
    
    private Label label1;
    private Label label2;
    private Label label3;
    
    private boolean analogIsRunning = true;
    private boolean initialized = true;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SS");
    private Date timeOld;
    private Date timeNew;
    private long timeDiff;
    
    int count = 1;
    
    public DigitalAnalogStopWatch(){
        setupUI();
        setupTimer();
    }
       
    public void setupUI(){
        rootContainer = new StackPane();
        dialImageView = new ImageView();
        handImageView = new ImageView();
        dialImage = new Image(getClass().getResourceAsStream(dialImageName));
        handImage = new Image(getClass().getResourceAsStream(handImageName));
        dialImageView.setImage(dialImage);
        handImageView.setImage(handImage);
        rootContainer.getChildren().addAll(dialImageView,handImageView);
        //initialize the variables
        
        controls = new VBox();
        
        start = new Button("Start");
        stop = new Button("Stop");
        record = new Button("Record");
        reset = new Button("Reset");
        //initialize a VBox and some buttons
        
        label1 = new Label("--:--.--");
        label2 = new Label("--:--.--");
        label3 = new Label("--:--.--");
        //initialize the labels
        
        controls.setAlignment(Pos.BOTTOM_CENTER);
        controls.setSpacing(10);
        controls.setPadding(new Insets(25,25,25,25));
        controls.getChildren().addAll(stop,start,record,reset,label1,label2,label3);
        //some parameters of the vbox
        
        rootContainer.getChildren().addAll(controls);
        
        start.setTextFill(Color.GREEN);
        stop.setTextFill(Color.RED);
        reset.setVisible(false);
        stop.setVisible(false);
        
        start.setOnAction((ActionEvent event) ->{
                timeline.play();
                analogIsRunning = true;//analog clock is running
                visibleOrNot(initialized, analogIsRunning, record, start, stop, reset);
                //use visibleornot function to hide some buttons
        });
        
        stop.setOnAction((ActionEvent event) ->{
                timeline.pause();
                analogIsRunning = false;//analog clock is not running
                visibleOrNot(initialized, analogIsRunning, record, start, stop, reset);
        });
        
        record.setOnAction((ActionEvent event) ->{
                if(initialized&&analogIsRunning){
                    timeOld = timeNew = new Date();//first press record button
                }
                else if(!initialized&&analogIsRunning){
                    timeOld = timeNew;
                    //set old time equal to last time we press the record
                    timeNew = new Date();
                    //set new time equal to new time
                    timeDiff = timeNew.getTime() - timeOld.getTime();
                    //get the difference between two time
                    
                    if(count%3==1){
                        label1.setText("lap" +count+": " + sdf.format(timeDiff));
                        ++count;
                    }//put record into first label
                    else if(count%3==2){
                        label2.setText("lap" +count+": " + sdf.format(timeDiff));
                        ++count;
                    }//put record into second label 
                    else{
                        label3.setText("lap" +count+": " + sdf.format(timeDiff));
                        ++count;
                    }//put record into third label
                }
                initialized = false;
                visibleOrNot(initialized, analogIsRunning, record, start, stop, reset);
        });
        
        reset.setOnAction((ActionEvent event) ->{
                label1.setText("--:--.--");
                label2.setText("--:--.--");
                label3.setText("--:--.--");
                count = 1;
                //reset all the variables to initial situation
                
                initialized = true;
                visibleOrNot(initialized, analogIsRunning, record, start, stop, reset);
        });
    }
    
    public void setupTimer() {
        keyFrame = new KeyFrame(Duration.millis(tickTimeInSeconds * 1000), (ActionEvent event) -> {
            update();
        });
        
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    
    private void update() {
        secondsElapsed += tickTimeInSeconds;
        double rotation = secondsElapsed * angleDeltaPerSeconds;
        handImageView.setRotate(rotation);
    }

    public Parent getRootContainer() {
        return rootContainer;
    }   
    
    public void start() {
        timeline.play();
    }
    
    public void visibleOrNot(boolean initialized, boolean analogIsRunning, Button record, 
            Button start, Button stop, Button reset){
        if(initialized==true&&analogIsRunning==false){
            record.setVisible(true);
            start.setVisible(true);
            stop.setVisible(false);
            reset.setVisible(false);
        }//when all the variables are initialized and analog clock is not running
        
        if(initialized==false&&analogIsRunning==true){
            record.setVisible(true);
            stop.setVisible(true);
            start.setVisible(false);
            reset.setVisible(false);
        }//when variables are not initialized and analog clock is running
        
        else if(initialized==false&&analogIsRunning==false){
            record.setVisible(false);
            stop.setVisible(true);
            start.setVisible(false);
            reset.setVisible(true);
        }//when variables are not initialized and analog clock is not running
    }
}

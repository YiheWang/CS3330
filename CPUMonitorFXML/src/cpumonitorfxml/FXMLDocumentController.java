/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpumonitorfxml;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author Administrator
 */
public class FXMLDocumentController implements Initializable {
    private int count = 1;
    private static double cpuUsage;
    boolean isRunning = false;
    Timeline timeline;
    private static String string;
    private static int rotate = 0;
    
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label;
    @FXML
    private Button start;
    @FXML
    private Button record;
    @FXML
    private ImageView hand;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        start.setOnAction((ActionEvent event) ->{
            if(!isRunning){
                isRunning = true;
                record.setText("record");
                start.setText("stop");
                timeline.play();
            }//button is start this time
            else if(isRunning){
                isRunning = false;
                record.setText("reset");
                start.setText("start");
                timeline.pause();
            }//button is stop this time 
        });
        
        record.setOnAction((ActionEvent event) -> {
            if(isRunning){
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            cpuUsage = 100*getCPUUsage();
            string = new DecimalFormat("00.00").format(cpuUsage);
            if(count%3==1){
                        label1.setText("Record: " +count+": " + string + "% at"+ sdf.format(now));
                        ++count;
                    }//put record into first label
                    else if(count%3==2){
                        label2.setText("Record: " +count+": " + string + "% at"+ sdf.format(now));
                        ++count;
                    }//put record into second label 
                    else{
                        label3.setText("Record: " +count+": " + string + "% at"+ sdf.format(now));
                        ++count;
                    }//put record into third label
            }//button is record this time 
            else{
                count = 1;
                label1.setText("--.--%");
                label2.setText("--.--%");
                label3.setText("--.--%");
                record.setText("record");
                start.setText("start");
                hand.setRotate(225);
            }//button is reset this time
        });
     
        timeline = new Timeline(new KeyFrame(Duration.millis(100), (ActionEvent) -> {
            cpuUsage = 100*getCPUUsage();
            System.out.println("CPU: " + cpuUsage); 
            if(cpuUsage>0){
                double usage = cpuUsage;
                double rotate = usage *3.6;
                string =  "Digital Display: "+ new DecimalFormat("00.00").format(usage)+"%";
                hand.setRotate(rotate+225);
                label.setText(string);
            }
            else{
                string = "Digital Display: 00.00%";
                label.setText(string);
                hand.setRotate(225);
            }
          
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        
        
    }
        
    
    
    private static double getCPUUsage() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        double value = 0;
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("getSystemCpuLoad")
                    && Modifier.isPublic(method.getModifiers())) {
                try {
                    value = (double) method.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    value = 0;
                }
                return value;
            }
        }
        return value;
    }
    
}

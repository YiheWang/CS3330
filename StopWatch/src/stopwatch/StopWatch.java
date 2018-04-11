/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stopwatch;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Yihe Wang
 */
public class StopWatch extends Application {
    
    private String appName = "StopWatch";
            
    @Override
    public void start(Stage primaryStage) {
        
        DigitalAnalogStopWatch digitalAnalogStopWatch = new DigitalAnalogStopWatch();
        
        Scene scene = new Scene(digitalAnalogStopWatch .getRootContainer(), 400, 800);
        primaryStage.setTitle(appName);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        digitalAnalogStopWatch.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

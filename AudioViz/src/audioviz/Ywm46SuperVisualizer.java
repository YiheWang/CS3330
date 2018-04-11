/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Yihe Wang
 */
public class Ywm46SuperVisualizer implements Visualizer{
    private final String name = "Ywm46 Super Visualizer";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minRectangleHeight = 10.0;  // 10.0
    private final Double halfMinRectangleHeight = minRectangleHeight / 2;
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    private final Double startHue = 260.0;
    
    private Double magnitudeOffset = 70.0; 
    
    private Rectangle[] rectangles1;
    private Rectangle[] rectangles2;
    
    private Ellipse ellipse1;
    private Ellipse ellipse2;
    
    public Ywm46SuperVisualizer() {
        
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end();
        
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        rectangles1 = new Rectangle[numBands];
        rectangles2 = new Rectangle[numBands];
        ellipse1 = new Ellipse();
        ellipse2 = new Ellipse();
        
        ellipse1.setCenterX(600.0);
        ellipse1.setCenterY(275.0);
        ellipse2.setCenterX(100.0);
        ellipse2.setCenterY(325.0);
        ellipse1.setRadiusY(10.0);
        ellipse1.setRadiusX(10.0);
        ellipse2.setRadiusY(10.0);
        ellipse2.setRadiusX(10.0);
        
        ellipse1.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
        ellipse2.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
        
        vizPane.getChildren().addAll(ellipse1,ellipse2);
        
        for (int i = 0; i < numBands; i++) {
            Rectangle rectangle1 = new Rectangle();
            Rectangle rectangle2 = new Rectangle();
            
            rectangle1.setX(bandWidth + bandWidth * i);
            rectangle1.setY(height / 2 - halfMinRectangleHeight);
            rectangle1.setWidth(bandWidth);
            rectangle1.setHeight(minRectangleHeight);
            rectangle1.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(rectangle1);
            rectangles1[i] = rectangle1;
            
            rectangle2.setX(bandWidth + bandWidth * i);
            rectangle2.setY(height / 2 + halfMinRectangleHeight);
            rectangle2.setWidth(bandWidth);
            rectangle2.setHeight(minRectangleHeight);
            rectangle2.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(rectangle2);
            rectangles2[i] = rectangle2;
        }

    }
    
    @Override
    public void end() {
         if (rectangles1 != null) {
             for (Rectangle rectangle : rectangles1) {
                 vizPane.getChildren().remove(rectangle);
             }
            rectangles1 = null;
        } 
         
         if (rectangles2 != null) {
             for (Rectangle rectangle : rectangles2) {
                 vizPane.getChildren().remove(rectangle);
             }
            rectangles2 = null;
        } 
         
         if (ellipse1 != null) {
             vizPane.getChildren().remove(ellipse1);
             ellipse1 = null;
         }         
         
         if (ellipse2 != null) {
             vizPane.getChildren().remove(ellipse2);
             ellipse2 = null;
         }       
    }
    
    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (rectangles1 == null) {
            return;
        }
        if (rectangles2 == null) {
            return;
        }
        
        Integer num = min(rectangles1.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            rectangles1[i].setHeight( ((60.0 + magnitudes[i])/60.0) * halfBandHeight + minRectangleHeight);
            rectangles1[i].setTranslateY(- rectangles1[i].getHeight());
            rectangles1[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            
            rectangles2[num-i-1].setHeight( ((60.0 + magnitudes[i])/60.0) * halfBandHeight + minRectangleHeight);
            //rectangles2[num-i-1].setTranslateY(rectangles1[i].getHeight());
            rectangles2[num-i-1].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            
            
            ellipse1.setRadiusY(magnitudeOffset + magnitudes[0]);
            ellipse1.setRadiusX(magnitudeOffset + magnitudes[1]);
            ellipse2.setRadiusY(magnitudeOffset + magnitudes[0]);
            ellipse2.setRadiusX(magnitudeOffset + magnitudes[1]);
            ellipse1.setTranslateY((Math.abs(magnitudes[0]) * 5) - 300);
            ellipse2.setTranslateY(-(Math.abs(magnitudes[1]) * 5) + 300);
            ellipse1.setFill(Color.hsb(magnitudes[0] * -6.0, 1.0, 1.0, 1.0));
            ellipse2.setFill(Color.hsb(magnitudes[0] * -6.0, 1.0, 1.0, 1.0));
        }
    }
}

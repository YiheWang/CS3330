/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chanllenge2;

/**
 *
 * @author Yihe Wang
 */
import java.util.*;
import java.text.*;

public class Chanllenge2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Helle World!");
        String myPawPrint = "ywm46";
        invokeMe(myPawPrint);//call the function
    }  
    
    public static void invokeMe(String myPawPrint){
        System.out.println("My PawPrint is:"+myPawPrint);
        //print out the pawprint
        Date now = new Date();//get time now 
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        //define the format of date
        System.out.println("Today's date is "+ sdf.format(now));
    }
}

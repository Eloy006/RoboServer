/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roboserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Eloy
 */
public class RoboBodyThread implements Runnable{

    public static Object syncObj=new Object();
    public static Object syncCommand=new Object();
    
    static Queue<SpiderCommand> myCommands=new LinkedList<SpiderCommand>();
    
    public RoboBodyThread(SpiderCommand command)
    {
        synchronized(syncCommand)
        {
            myCommands.add(command);
           
        }
        
    }
    
    
        
    
    
   
        
    @Override
    public void run() {
       synchronized(syncObj)
       {
           Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE,"run");
           
           SpiderCommand command;
            synchronized(syncCommand) {command=myCommands.poll();}
            
            if(command.bodyMotorState==EN_SpiderCommand.EnableBodyA) 
            {
                GPIOController.bodyPinA.high();
                GPIOController.bodyPinB.low();
            }
            
            if(command.bodyMotorState==EN_SpiderCommand.EnableBodyB) 
            {
                GPIOController.bodyPinB.high();
                GPIOController.bodyPinA.low();
            }
            
         
            
            if(command.bodyMotorEnable) GPIOController.bodyPinE.high();
         
       
            try {
                
                Thread.sleep(command.bodyMotorTimeOut*1000);
                
                
                
                
                //   headPinE.setShutdownOptions(true, PinState.LOW);
                //    headPinA.setShutdownOptions(true, PinState.LOW);
                //   headPinB.setShutdownOptions(true, PinState.LOW);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(myCommands.isEmpty())
            {
                GPIOController.bodyPinE.low();
                
                
            }
           
           
       }
    }
    
}

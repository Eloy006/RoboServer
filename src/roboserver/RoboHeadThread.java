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
public class RoboHeadThread implements Runnable{

    public static Object syncObj=new Object();
    public static Object syncCommand=new Object();
    
    static Queue<SpiderCommand> myCommands=new LinkedList<SpiderCommand>();
    
    public RoboHeadThread(SpiderCommand command)
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
            
         
            
            if(command.headMotorState==EN_SpiderCommand.EnableHeadA) 
            {
               GPIOController.headPinA.high();
               GPIOController.headPinB.low();
                Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE,"EnableHeadA");
            }
            
            if(command.headMotorState==EN_SpiderCommand.EnableHeadB) 
            {
                GPIOController.headPinB.high();
                GPIOController.headPinA.low();
                 Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE,"EnableHeadB");
            }
            
            
            
            if(command.headMotorEnable) {GPIOController.headPinE.high();  Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE,"EnableHeadE");}
       
            try {
             
                Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE,"TimeOut: " + (command.headMotorTimeOut*1000));
                Thread.sleep(command.headMotorTimeOut*1000);
                
                
                
                //   headPinE.setShutdownOptions(true, PinState.LOW);
                //    headPinA.setShutdownOptions(true, PinState.LOW);
                //   headPinB.setShutdownOptions(true, PinState.LOW);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(myCommands.isEmpty())
            {
                 Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE,"headPinE to low");
                GPIOController.headPinE.low();
                
            }
           
           
       }
    }
    
}

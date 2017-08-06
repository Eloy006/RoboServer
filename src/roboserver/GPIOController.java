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

/**
 *
 * @author Eloy
 */
public class GPIOController {
        public static final GpioController gpio = GpioFactory.getInstance();
        public static final GpioPinDigitalOutput bodyPinE = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Enable", PinState.LOW);
        public static final GpioPinDigitalOutput bodyPinA = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "L1", PinState.LOW);
        public static final GpioPinDigitalOutput bodyPinB = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "L2", PinState.LOW);
        
        public static final GpioPinDigitalOutput headPinB = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "HEnable", PinState.LOW);
        public static final GpioPinDigitalOutput headPinA = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "HL1", PinState.LOW);
        public static final GpioPinDigitalOutput headPinE = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "HL2", PinState.LOW);
        static
        {
                  bodyPinE.setShutdownOptions(true, PinState.LOW);
                  bodyPinA.setShutdownOptions(true, PinState.LOW);
                  bodyPinB.setShutdownOptions(true, PinState.LOW);
                  
                  headPinE.setShutdownOptions(true, PinState.LOW);
                  headPinA.setShutdownOptions(true, PinState.LOW);
                  headPinB.setShutdownOptions(true, PinState.LOW);
        }
        
}

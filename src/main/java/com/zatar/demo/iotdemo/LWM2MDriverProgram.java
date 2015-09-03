package com.zatar.demo.iotdemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.pi4j.component.button.ButtonEvent;
import com.pi4j.component.button.ButtonPressedListener;
import com.pi4j.component.button.ButtonReleasedListener;
import com.pi4j.system.SystemInfo;
import com.zatar.demo.iotdemo.DataTransmissionKeys;
import com.zatar.demo.iotdemo.LWM2MDevice;
import com.zatar.demo.iotdemo.PibrellaUtility;

public class LWM2MDriverProgram {
	public static void main(final String[] args) {
		LWM2MDevice client = new LWM2MDevice();
		final PibrellaUtility pibrellaUtil = PibrellaUtility.getInstance();
		
		try{
			InputStream propertiesInputStreamf = new FileInputStream(args[0]);
			DataTransmissionKeys.pros.load(propertiesInputStreamf);
			final String udi = SystemInfo.getSerial();
			System.out.println("Serial number is : " + udi);
		}
		catch (IOException | InterruptedException ex){
			System.out.println(ex.getMessage());
			
		}
		catch (Exception e)
        {
            throw new ExceptionInInitializerError("There was a problem initializing the properties file: " + e.toString());
        }
		
		
		pibrellaUtil.pibrella.button().addListener(new ButtonPressedListener() {
            public void onButtonPressed(ButtonEvent event) {
                System.out.println("[BUTTON PRESSED]");
                System.out.println("State of button is now: " +  pibrellaUtil.getButtonState(String.valueOf(DataTransmissionKeys.PIBRELLA_BUTTON_STATE)));
               
            }
        });
		
		pibrellaUtil.pibrella.button().addListener(new ButtonReleasedListener() {
            public void onButtonReleased(ButtonEvent event) {
                System.out.println("[BUTTON RELEASED]");
                System.out.println("State of button is now: " + pibrellaUtil.getButtonState(String.valueOf(DataTransmissionKeys.PIBRELLA_BUTTON_STATE)));
          
            }
        });
		client.start();

	}
}
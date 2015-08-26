package com.zatar.demo.makerfaire;

import java.io.FileInputStream;
import java.io.InputStream;

import com.pi4j.component.button.ButtonEvent;
import com.pi4j.component.button.ButtonPressedListener;
import com.pi4j.component.button.ButtonReleasedListener;

public class LWM2MDriverProgram {
	public static void main(final String[] args) {
		LWM2MDevice client = new LWM2MDevice();
		final PibrellaUtility pibrellaUtil = PibrellaUtility.getInstance();
		
		try{
			InputStream propertiesInputStreamf = new FileInputStream(args[0]);
			DataTransmissionKeys.pros.load(propertiesInputStreamf);
		}catch (Exception e)
        {
            throw new ExceptionInInitializerError("There was a problem initializing the properties file: " + e.toString());
        }
		
		
		pibrellaUtil.pibrella.button().addListener(new ButtonPressedListener() {
            @Override
            public void onButtonPressed(ButtonEvent event) {
                System.out.println("[BUTTON PRESSED]");
                System.out.println("State of button is now: " +  pibrellaUtil.getButtonState(String.valueOf(DataTransmissionKeys.PIBRELLA_BUTTON_STATE)));
               
            }
        });
		
		pibrellaUtil.pibrella.button().addListener(new ButtonReleasedListener() {
            @Override
            public void onButtonReleased(ButtonEvent event) {
                System.out.println("[BUTTON RELEASED]");
                System.out.println("State of button is now: " + pibrellaUtil.getButtonState(String.valueOf(DataTransmissionKeys.PIBRELLA_BUTTON_STATE)));
          
            }
        });
		client.start();

	}
}

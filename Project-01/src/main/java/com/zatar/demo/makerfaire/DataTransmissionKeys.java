package com.zatar.demo.makerfaire;

import java.util.Properties;

//Possible  values These were picked by the developer of the PI. 
//Have to remain constant because they have to match in the avatar defenition.
//The person that writes the avatar defenition is the same person that writes the device
public class DataTransmissionKeys {
	
	
	public static final Properties pros = new Properties();
	
	
	public static final int PIBRELLA_RED_LED = 700;
	public static final int PIBRELLA_YELLOW_LED = 701;
	public static final int PIBRELLA_GREEN_LED = 702;
	public static final int PIBRELLA_BUTTON_STATE = 703;
	public static final int PIBRELLA_BUZZER_FREQUENCY = 704;
	public static final int PIBRELLA_BUZZER_STATE = 705;
}

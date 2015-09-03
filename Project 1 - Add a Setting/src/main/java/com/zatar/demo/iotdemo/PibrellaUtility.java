package com.zatar.demo.iotdemo;

import com.pi4j.component.button.ButtonState;
import com.pi4j.device.pibrella.Pibrella;
import com.pi4j.device.pibrella.PibrellaLed;
import com.pi4j.device.pibrella.impl.PibrellaDevice;

public class PibrellaUtility {
	private static final String ON = "On";
	private static final String OFF = "Off";
	//private static final String UP = "Up";
	//private static final String DOWN = "Down";
	private static final String RELEASED = "Released";
	private static final String PRESSED = "Pressed";
	public Pibrella pibrella;
	private static PibrellaUtility instance;

	public static PibrellaUtility getInstance() {
		if (instance == null) {
			instance = new PibrellaUtility();
		}
		return instance;
	}

	private PibrellaUtility() {
		System.out.println("PibrellaDataSource: Initializing...");
		pibrella = new PibrellaDevice();
	}

	public String readPibrellaInidcators(String value) {

		String indicator = null;
		if (value.equals(String
				.valueOf(DataTransmissionKeys.PIBRELLA_GREEN_LED))) {
			indicator = booleanOnOff(pibrella.getLed(PibrellaLed.GREEN).isOn());
		} else if (value.equals(String
				.valueOf(DataTransmissionKeys.PIBRELLA_YELLOW_LED))) {
			indicator = booleanOnOff(pibrella.getLed(PibrellaLed.YELLOW).isOn());
		} else if (value.equals(String
				.valueOf(DataTransmissionKeys.PIBRELLA_RED_LED))) {
			indicator = booleanOnOff(pibrella.getLed(PibrellaLed.RED).isOn());
		}
		return indicator;
	}

	public void writePibrellaIndicators(final String value, final String indicator){
		if (value.equals(String
				.valueOf(DataTransmissionKeys.PIBRELLA_GREEN_LED))) {
			setLedState(PibrellaLed.GREEN, indicator);
		} else if (value.equals(String
				.valueOf(DataTransmissionKeys.PIBRELLA_YELLOW_LED))) {
			setLedState(PibrellaLed.YELLOW, indicator);
		} else if (value.equals(String
				.valueOf(DataTransmissionKeys.PIBRELLA_RED_LED))) {
			setLedState(PibrellaLed.RED, indicator);
		}
		
	}
	
	
	public String getButtonState(String value) {
		String indication = null;
		if (value.equals(String
				.valueOf(DataTransmissionKeys.PIBRELLA_BUTTON_STATE))) {
			indication = stateUpDown(pibrella.getButton().getState());
		}
		return indication;
	}

	private String booleanOnOff(boolean isOn) {
		return isOn ? ON : OFF;
	}

	private String stateUpDown(ButtonState state) {
		return state == ButtonState.PRESSED ? PRESSED : RELEASED;
	}
	private void setLedState(PibrellaLed led, String ledState) {
		
		if (ledState.equalsIgnoreCase(ON)) {
			pibrella.getLed(led).on();
		} else if (ledState.equalsIgnoreCase(OFF)) {
			pibrella.getLed(led).off();
		}
	}
}

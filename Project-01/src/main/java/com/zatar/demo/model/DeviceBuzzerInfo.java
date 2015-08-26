package com.zatar.demo.model;

import java.util.HashMap;
import java.util.Map;

import com.zatar.demo.makerfaire.DataTransmissionKeys;

public class DeviceBuzzerInfo extends BaseZatarInstanceEnabler {

	public DeviceBuzzerInfo() {
		super(createResourceEnablers());
	}

	
	private static Map<Integer, ResourceEnabler> createResourceEnablers() {
		
		final Map<Integer, ResourceEnabler> resources = new HashMap<>();
		resources.put(10, new RWBuzzerInfoResourceEnabler(10, String.valueOf(DataTransmissionKeys.PIBRELLA_BUZZER_STATE)));
		resources.put(13, new RWBuzzerInfoResourceEnabler(13, String.valueOf(DataTransmissionKeys.PIBRELLA_BUZZER_FREQUENCY)));

		return resources;
	}
	
}

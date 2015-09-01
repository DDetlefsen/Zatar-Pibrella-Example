package com.zatar.demo.iotdemo.model;

import java.util.HashMap;
import java.util.Map;

import com.zatar.demo.iotdemo.DataTransmissionKeys;
import com.zatar.demo.iotdemo.resources.RWLedLightsResourceEnabler;
import com.zatar.demo.iotdemo.resources.ResourceEnabler;

public class DeviceLedInfo extends BaseZatarInstanceEnabler {

	public DeviceLedInfo() {
		super(createResourceEnablers());
	}

	private static Map<Integer, ResourceEnabler> createResourceEnablers() {
		
		final Map<Integer, ResourceEnabler> resources = new HashMap<>();
		resources.put(6, new RWLedLightsResourceEnabler(6, String.valueOf(DataTransmissionKeys.PIBRELLA_YELLOW_LED)));
		resources.put(11, new RWLedLightsResourceEnabler(11, String.valueOf(DataTransmissionKeys.PIBRELLA_RED_LED)));
		resources.put(12, new RWLedLightsResourceEnabler(12, String.valueOf(DataTransmissionKeys.PIBRELLA_GREEN_LED)));
		
		return resources;
	}
	
}

package com.zatar.demo.model;

import java.util.HashMap;
import java.util.Map;

import com.zatar.demo.makerfaire.DataTransmissionKeys;

public class DeviceButtonInfo extends BaseZatarInstanceEnabler {

	public DeviceButtonInfo() {
		super(createResourceEnablers());
		// TODO Auto-generated constructor stub
	}
	
	private static Map<Integer, ResourceEnabler> createResourceEnablers() {
		
		final Map<Integer, ResourceEnabler> resources = new HashMap<>();
		resources.put(8, new RButtonInfoResourceEnabler(8, String.valueOf(DataTransmissionKeys.PIBRELLA_BUTTON_STATE)));

		return resources;
	}
	
}

package com.zatar.demo.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.pi4j.system.SystemInfo;

public class DeviceInfo extends BaseZatarInstanceEnabler {
	
	private static final String INITIAL_UTC_OFFSET = "+05";

	public DeviceInfo() {
		super(createResourceEnablers());
	}

	private static Map<Integer, ResourceEnabler> createResourceEnablers() {
	
		final Map<Integer, ResourceEnabler> resources = new HashMap<>();
		try {
			resources.put(1, new ReadOnlyResourceEnabler(1, "Demo-lwm2m"));
			resources.put(2, new ReadOnlyResourceEnabler(2, SystemInfo.getSerial()));
			resources.put(14, new ReadWriteResourceEnabler(14, INITIAL_UTC_OFFSET));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return resources;
	}
}

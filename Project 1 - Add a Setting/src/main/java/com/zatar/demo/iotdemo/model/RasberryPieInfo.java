package com.zatar.demo.iotdemo.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.pi4j.system.SystemInfo;
import com.zatar.demo.iotdemo.resources.ReadOnlyResourceEnabler;
import com.zatar.demo.iotdemo.resources.ResourceEnabler;

public class RasberryPieInfo extends BaseZatarInstanceEnabler {

	public RasberryPieInfo() {
		super(createResourceEnablers());
	}

	private static Map<Integer, ResourceEnabler> createResourceEnablers() {
		final Map<Integer, ResourceEnabler> resources = new HashMap<>();
		try{
			resources.put(15, new ReadOnlyResourceEnabler(15, SystemInfo.getCpuImplementer()));
		}catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return resources;
	}
}

package com.zatar.demo.iotdemo.model;

import java.util.HashMap;
import java.util.Map;

import com.zatar.demo.iotdemo.resources.ReadOnlyResourceEnabler;
import com.zatar.demo.iotdemo.resources.ResourceEnabler;
import com.zatar.demo.iotdemo.resources.WriteOnlyResourceEnabler;

public class DevToken extends BaseZatarInstanceEnabler {

	public DevToken() {
		super(createEnablers());
	}

	/**
	 * The token is a one time deal , we need to distribute that through 
	 * to the client, avoid hard coding.
	 * @return
	 */
	private static Map<Integer, ResourceEnabler> createEnablers() {
		final HashMap<Integer, ResourceEnabler> resources = new HashMap<>();
		resources.put(0, new ReadOnlyResourceEnabler(0, "c11d146b-8238-4950-9be6-ef839fc1d78b"));
		resources.put(3, new WriteOnlyResourceEnabler());
		return resources;
	}

}

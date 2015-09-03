package com.zatar.demo.iotdemo.resources;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.Value;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.core.response.ValueResponse;

import com.zatar.demo.iotdemo.PibrellaUtility;
import com.zatar.demo.iotdemo.model.BaseResourceEnabler;

public class RWLedLightsResourceEnabler extends BaseResourceEnabler {
	
	private int id;
	private String value;
	private PibrellaUtility instance = PibrellaUtility.getInstance();
	public RWLedLightsResourceEnabler(final int id, final String value) {
		this.id = id;
		this.value = value;
	}
	
	@Override
	public ValueResponse read() {
		Value<String> convertedValue = Value.newStringValue(instance.readPibrellaInidcators(value.toString()));
		return new ValueResponse(ResponseCode.CONTENT, new LwM2mResource(id, convertedValue));
	}

	@Override
	public LwM2mResponse write(final LwM2mResource node) {
		//APPLY the write here.
		String indicator = (String) node.getValue().value;
		instance.writePibrellaIndicators(value,indicator);
		return new LwM2mResponse(ResponseCode.CHANGED);
	}
	
	
	
	
}

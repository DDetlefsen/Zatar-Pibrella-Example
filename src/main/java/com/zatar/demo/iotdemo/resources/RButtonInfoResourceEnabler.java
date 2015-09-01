package com.zatar.demo.iotdemo.resources;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.Value;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.core.response.ValueResponse;

import com.zatar.demo.iotdemo.PibrellaUtility;
import com.zatar.demo.iotdemo.model.BaseResourceEnabler;

public class RButtonInfoResourceEnabler extends BaseResourceEnabler {
	
	private int id;
	private String value;
	private PibrellaUtility instance = PibrellaUtility.getInstance();
	public RButtonInfoResourceEnabler(int id, String value) {
		this.id = id;
		this.value = value;
	}
	

	public ValueResponse read() {
		//Apply the actual button state from PI
		Value<String> convertedValue = Value.newStringValue(instance.getButtonState(value));
		return new ValueResponse(ResponseCode.CONTENT, new LwM2mResource(id, convertedValue));
	}
	@SuppressWarnings("unused")
	@Override
	public LwM2mResponse write(final LwM2mResource node) {
		//APPLY the write here.
		String indicator = (String) node.getValue().value;
		return new LwM2mResponse(ResponseCode.CHANGED);
	}
}

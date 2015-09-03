package com.zatar.demo.iotdemo.resources;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.node.Value;
import org.eclipse.leshan.core.response.ValueResponse;

import com.zatar.demo.iotdemo.model.BaseResourceEnabler;

public class ReadOnlyResourceEnabler extends BaseResourceEnabler {
	private final int id;
	private final String value;

	public ReadOnlyResourceEnabler(final int id, final String value) {
		this.id = id;
		this.value = value;
	}

	public ValueResponse read() {
		return new ValueResponse(ResponseCode.CONTENT, new LwM2mResource(id, Value.newStringValue(value)));
	}

}
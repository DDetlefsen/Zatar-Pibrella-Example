package com.zatar.demo.iotdemo.resources;

import org.eclipse.leshan.ResponseCode;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.LwM2mResponse;

import com.zatar.demo.iotdemo.model.BaseResourceEnabler;

public class WriteOnlyResourceEnabler extends BaseResourceEnabler {

	@Override
	public LwM2mResponse write(final LwM2mResource node) {
		// TODO: This should actually trigger something, or store something.
		return new LwM2mResponse(ResponseCode.CHANGED);
	}

}

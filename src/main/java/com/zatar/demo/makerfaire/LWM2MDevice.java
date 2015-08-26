package com.zatar.demo.makerfaire;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.leshan.client.LwM2mClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.model.ResourceModel;
import org.eclipse.leshan.core.model.ResourceModel.Operations;
import org.eclipse.leshan.core.model.ResourceModel.Type;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.request.DeregisterRequest;
import org.eclipse.leshan.core.request.RegisterRequest;
import org.eclipse.leshan.core.response.RegisterResponse;

import com.zatar.demo.model.DevToken;
import com.zatar.demo.model.DeviceButtonInfo;
import com.zatar.demo.model.DeviceBuzzerInfo;
import com.zatar.demo.model.DeviceInfo;
import com.zatar.demo.model.DeviceLedInfo;
import com.zatar.demo.model.RasberryPieInfo;

public class LWM2MDevice {

	public void start(){
		final Map<Integer, ObjectModel> objectModels = new HashMap<>();
		objectModels.put(3, createDeviceObjectModel());
		objectModels.put(22332, createRaspberryPieObjectModel());
		objectModels.put(23854, createDevTokenObjectModel());
		objectModels.put(22335, createLedDeviceObjectModel());
		objectModels.put(22333, createButtonInfoObjectModel());
		objectModels.put(22334, createBuzzerInfoObjectModel());

		final LwM2mModel model = new LwM2mModel(objectModels);
		final ObjectsInitializer initializer = new ObjectsInitializer(model);

		initializer.setClassForObject(3, DeviceInfo.class);
		initializer.setClassForObject(22332, RasberryPieInfo.class);
		initializer.setClassForObject(23854, DevToken.class);
		initializer.setClassForObject(22335, DeviceLedInfo.class);
		initializer.setClassForObject(22333, DeviceButtonInfo.class);
		initializer.setClassForObject(22334, DeviceBuzzerInfo.class);
		
		String hostName = DataTransmissionKeys.pros.getProperty("zatar.hostname");
		int port =  Integer.parseInt(DataTransmissionKeys.pros.getProperty("zatar.port"));
		System.out.println(hostName);

		final LwM2mClient client = new LeshanClientBuilder()
		.setBindingMode(BindingMode.T)
		.setServerAddress(new InetSocketAddress(hostName, port))
		.setObjectsInitializer(initializer)
		.build(3, 22332, 23854, 22335, 22333, 22334);
		
		client.start();
		final RegisterResponse response = client.send(new RegisterRequest("example-endpoint"));
		final String registrationID = response.getRegistrationID();
		System.out.println("Registered with ID: " + registrationID);

		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.send(new DeregisterRequest(registrationID));
		client.stop();
	}
	
	private static ObjectModel createBuzzerInfoObjectModel() {
		final Map<Integer, ResourceModel> resources = new HashMap<Integer, ResourceModel>();
		resources.put(10, new ResourceModel(10, "Buzzer State", Operations.RW, false, false, Type.STRING, "", "", ""));
		resources.put(13, new ResourceModel(13, "Buzzer Frequency", Operations.RW, false, false, Type.STRING, "", "", ""));
		return new ObjectModel(22334, "Buzzer Device Info", "", false, true, resources);
	}

	private static ObjectModel createButtonInfoObjectModel() {
		final Map<Integer, ResourceModel> resources = new HashMap<Integer, ResourceModel>();
		resources.put(8, new ResourceModel(8, "Button State", Operations.RW, false, false, Type.STRING, "", "", ""));
		return new ObjectModel(22333, "Button Device Info", "", false, true, resources);
	}

	
	private static ObjectModel createLedDeviceObjectModel() {
		final Map<Integer, ResourceModel> resources = new HashMap<Integer, ResourceModel>();
		resources.put(11, new ResourceModel(11, "Red LED", Operations.RW, false, false, Type.STRING, "", "", ""));
		resources.put(6, new ResourceModel(6, "Yellow LED", Operations.RW, false, false, Type.STRING, "", "", ""));
		resources.put(12, new ResourceModel(12, "Green LED", Operations.RW, false, false, Type.STRING, "", "", ""));
		return new ObjectModel(22335, "Led Device Info", "", false, true, resources);
	}
	
	private static ObjectModel createRaspberryPieObjectModel() {
		final Map<Integer, ResourceModel> resources = new HashMap<Integer, ResourceModel>();
		resources.put(15, new ResourceModel(15, "CPU implementer", Operations.RW, false, false, Type.STRING, "", "", ""));
		return new ObjectModel(22332, "Raspberry pie", "", false, true, resources);
	}

	private static ObjectModel createDeviceObjectModel() {
		final Map<Integer, ResourceModel> resources = new HashMap<Integer, ResourceModel>();
		resources.put(1, new ResourceModel(1, "Model", Operations.R, false, false, Type.STRING, "", "", ""));
		resources.put(2, new ResourceModel(2, "Serial Number", Operations.R, false, false, Type.STRING, "", "", ""));
		resources.put(14, new ResourceModel(14, "UTC Offset", Operations.RW, false, false, Type.STRING, "", "", ""));
		return new ObjectModel(3, "Device", "", false, true, resources);
	}

	private static ObjectModel createDevTokenObjectModel() {
		final Map<Integer, ResourceModel> resources = new HashMap<Integer, ResourceModel>();
		resources.put(0, new ResourceModel(0, "Token", Operations.R, false, false, Type.STRING, "", "", ""));
		resources.put(3, new ResourceModel(3, "Validation", Operations.W, false, false, Type.INTEGER, "", "", ""));
		return new ObjectModel(23854, "Zatar Device Token", "", false, true, resources);
	}
}

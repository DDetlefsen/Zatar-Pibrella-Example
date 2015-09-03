package com.zatar.demo.iotdemo;

import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.eclipse.leshan.client.LwM2mClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.californium.LeshanClientBuilder.TCPConfigBuilder;
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

import com.zatar.demo.iotdemo.model.DevToken;
import com.zatar.demo.iotdemo.model.DeviceButtonInfo;
import com.zatar.demo.iotdemo.model.DeviceBuzzerInfo;
import com.zatar.demo.iotdemo.model.DeviceInfo;
import com.zatar.demo.iotdemo.model.DeviceLedInfo;
import com.zatar.demo.iotdemo.model.RasberryPieInfo;

public class LWM2MDevice {
	
	private static boolean isTlsEnabled;
	private static String tlsProtocol;

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


		
		
		
		final LeshanClientBuilder builder = new LeshanClientBuilder()
		.setServerAddress(new InetSocketAddress(hostName, port))
		.setObjectsInitializer(initializer);
		final TCPConfigBuilder tcpBuilder = builder.addBindingModeTCPClient();
			if(isTlsEnabled) {
				SSLContext context = null;
				try {
					context = SSLContext.getInstance(tlsProtocol);
					context.init(null, null, null);
				} catch (final NoSuchAlgorithmException e) {
					System.out.println("There was problem initializing the TLS objects, please make sure that chosen protocol exists");
					e.printStackTrace();
					System.exit(-1);
				} catch (final KeyManagementException e) {
					System.out.println("There was problem initializing the TLS objects, please make sure that keystore exists");
					e.printStackTrace();
					System.exit(-1);
				}
				//configure TLS
				tcpBuilder.secure().setSSLContext(context).configure();
			}		
			//configure TCP and build a LWM2M Client
			final LwM2mClient client = tcpBuilder.configure().build();		
		
		

		
		
		
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

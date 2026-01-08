package com.autoTransport.manager;

import java.util.List;

import com.autoTransport.model.Payment;
import com.autoTransport.model.Vehicle;

public interface MainManager {
	
	boolean isValidLogin(String userId, String userPass);
	
	void saveVehicle(Vehicle vehicle);
	
	List<Vehicle> getVehicles();

	Vehicle findVehicleByVehicleNo(String vehicleNo);

	List<Payment> getPaidVehicles();

}

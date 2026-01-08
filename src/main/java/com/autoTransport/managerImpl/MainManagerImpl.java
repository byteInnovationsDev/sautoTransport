package com.autoTransport.managerImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.autoTransport.manager.MainManager;
import com.autoTransport.model.Payment;
import com.autoTransport.model.User;
import com.autoTransport.model.Vehicle;
import com.autoTransport.repository.PaymentRepository;
import com.autoTransport.repository.UserRepository;
import com.autoTransport.repository.VehicleRepository;

@Service
public class MainManagerImpl implements MainManager{
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	VehicleRepository vehicleRepo;
	@Autowired
	PaymentRepository payRepo;

	@Override
	public boolean isValidLogin(String userId, String userPass) {
		
		User user = userRepo.findByUserId(userId);
		
		if(user != null)
		{
			if(user.getUserPass().equals(userPass))
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void saveVehicle(Vehicle vehicle) {

	    Optional<Vehicle> existingOpt =
	            vehicleRepo.findByVehicleNo(vehicle.getVehicleNo());

	    if (existingOpt.isPresent()) {

	        Vehicle existing = existingOpt.get();

	        existing.setCategory(vehicle.getCategory());
	        existing.setDriverName(vehicle.getDriverName());
	        existing.setDriverPhoneNo(vehicle.getDriverPhoneNo());

	        existing.setFcExpiryDate(vehicle.getFcExpiryDate());
	        existing.setNocExpiryDate(vehicle.getNocExpiryDate());
	        existing.setRcExpiryDate(vehicle.getRcExpiryDate());
	        existing.setLicenseExpiryDate(vehicle.getLicenseExpiryDate());

	        if (vehicle.getFcFile() != null) {
	            existing.setFcFile(vehicle.getFcFile());
	            existing.setFcFileName(vehicle.getFcFileName());
	            existing.setFcFileType(vehicle.getFcFileType());
	        }

	        if (vehicle.getNocFile() != null) {
	            existing.setNocFile(vehicle.getNocFile());
	            existing.setNocFileName(vehicle.getNocFileName());
	            existing.setNocFileType(vehicle.getNocFileType());
	        }

	        if (vehicle.getRcFile() != null) {
	            existing.setRcFile(vehicle.getRcFile());
	            existing.setRcFileName(vehicle.getRcFileName());
	            existing.setRcFileType(vehicle.getRcFileType());
	        }

	        if (vehicle.getLicenseFile() != null) {
	            existing.setLicenseFile(vehicle.getLicenseFile());
	            existing.setLicenseFileName(vehicle.getLicenseFileName());
	            existing.setLicenseFileType(vehicle.getLicenseFileType());
	        }

	        vehicleRepo.save(existing);

	    } else {

	        vehicleRepo.save(vehicle);
	    }
	}


	@Override
	public List<Vehicle> getVehicles() {

		List<Vehicle> vehicles = vehicleRepo.findAll();
		
		return vehicles;
	}

	@Override
	public Vehicle findVehicleByVehicleNo(String vehicleNo) {
		
		Vehicle vehicle = vehicleRepo.findVehicleByVehicleNo(vehicleNo);
		
		return vehicle;
	}

	@Override
	public List<Payment> getPaidVehicles() {

		return payRepo.findAll();
	}

}

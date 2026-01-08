package com.autoTransport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autoTransport.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	Vehicle findVehicleByVehicleNo(String vehicleNo);
	Optional<Vehicle> findByVehicleNo(String vehicleNo);
	
}

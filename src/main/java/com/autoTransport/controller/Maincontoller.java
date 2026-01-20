package com.autoTransport.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.autoTransport.dto.PaymentDTO;
import com.autoTransport.dto.VehicleDTO;
import com.autoTransport.dto.VehicleViewDTO;
import com.autoTransport.manager.MainManager;
import com.autoTransport.manager.ReportManager;
import com.autoTransport.model.Payment;
import com.autoTransport.model.Vehicle;
import com.autoTransport.repository.PaymentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class Maincontoller {
	
	@Autowired
	MainManager manager;
	@Autowired
	PaymentRepository paymentRepo;
	
	@GetMapping("/")
	public String main() 
	{
		return "index";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestParam String userId,
	        @RequestParam String userPass,HttpSession session) {
		
		if(!manager.isValidLogin(userId, userPass)) {
			
			return "invalid";
		}
		
		session.setAttribute("User", userId);
	    return "success";
	}
	
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {

		/*
		 * if (session.getAttribute("User") == null) { return "redirect:/"; }
		 */
	    List<Vehicle> vehicles = manager.getVehicles();
	    model.addAttribute("vehicles", vehicles);
	    model.addAttribute("userId", session.getAttribute("User"));
		return "home";
	}
	
		@GetMapping("/payments")
		public String payments(HttpSession session, Model model) {
			
			List<Vehicle> vehicles = manager.getVehicles();
			model.addAttribute("vehicles", vehicles);
			return "Payments";
		}
	
	@GetMapping("/billing")
	public String billing(HttpSession session, Model model) {

	    List<Payment> paidVehicles = manager.getPaidVehicles();
	    List<Vehicle> vehicles = manager.getVehicles();
	    model.addAttribute("vehicles", paidVehicles);
	    model.addAttribute("vehiclesList", vehicles);
		return "billing";
	}
	
	
	@PostMapping(
		    value = "/saveVehicle",
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
		)
		@ResponseBody
		public String saveVehicle(
		        @RequestPart("vehicle") VehicleDTO dto,
		        @RequestPart(value = "fcFile", required = false) MultipartFile fcFile,
		        @RequestPart(value = "nocFile", required = false) MultipartFile nocFile,
		        @RequestPart(value = "rcFile", required = false) MultipartFile rcFile,
		        @RequestPart(value = "licenseFile", required = false) MultipartFile licenseFile,
		        @RequestPart(value = "aadharFile", required = false) MultipartFile aadharFile,
		        @RequestPart(value = "pucFile", required = false) MultipartFile pucFile,
		        @RequestPart(value = "insuranceFile", required = false) MultipartFile insuranceFile
		) {
		    try {
		        Vehicle vehicle = new Vehicle();

		        // ================= TEXT =================
		        vehicle.setVehicleNo(dto.getVehicleNo());
		        vehicle.setCategory(dto.getCategory());
		        vehicle.setDriverName(dto.getDriverName());
		        vehicle.setDriverPhoneNo(dto.getDriverPhoneNo());

		        vehicle.setFcExpiryDate(dto.getFcExpdt());
		        vehicle.setNocExpiryDate(dto.getNocExpdt());
		        vehicle.setRcExpiryDate(dto.getRcExpdt());
		        vehicle.setLicenseExpiryDate(dto.getLicenseExpdt());
		        vehicle.setPucExpiryDate(dto.getPucExpdt());
		        vehicle.setInsuranceExpiryDate(dto.getInsuranceExpdt());

		        // ================= FILES =================
		        if (fcFile != null && !fcFile.isEmpty()) {
		            vehicle.setFcFile(fcFile.getBytes());
		            vehicle.setFcFileName(fcFile.getOriginalFilename());
		            vehicle.setFcFileType(fcFile.getContentType());
		        }

		        if (nocFile != null && !nocFile.isEmpty()) {
		            vehicle.setNocFile(nocFile.getBytes());
		            vehicle.setNocFileName(nocFile.getOriginalFilename());
		            vehicle.setNocFileType(nocFile.getContentType());
		        }

		        if (rcFile != null && !rcFile.isEmpty()) {
		            vehicle.setRcFile(rcFile.getBytes());
		            vehicle.setRcFileName(rcFile.getOriginalFilename());
		            vehicle.setRcFileType(rcFile.getContentType());
		        }

		        if (licenseFile != null && !licenseFile.isEmpty()) {
		            vehicle.setLicenseFile(licenseFile.getBytes());
		            vehicle.setLicenseFileName(licenseFile.getOriginalFilename());
		            vehicle.setLicenseFileType(licenseFile.getContentType());
		        }
		        
		        if (pucFile != null && !pucFile.isEmpty()) {
		        	vehicle.setPucFile(pucFile.getBytes());
		        	vehicle.setPucFileName(pucFile.getOriginalFilename());
		        	vehicle.setPucFileType(pucFile.getContentType());
		        }
		        if (aadharFile != null && !aadharFile.isEmpty()) {
		        	vehicle.setAadharFile(aadharFile.getBytes());
		        	vehicle.setAadharFileName(aadharFile.getOriginalFilename());
		        	vehicle.setAadharFileType(aadharFile.getContentType());
		        }
		        if (insuranceFile != null && !insuranceFile.isEmpty()) {
		        	vehicle.setInsuranceFile(insuranceFile.getBytes());
		        	vehicle.setInsuranceFileName(insuranceFile.getOriginalFilename());
		        	vehicle.setInsuranceFileType(insuranceFile.getContentType());
		        }


		        manager.saveVehicle(vehicle);
		        return "success";

		    } catch (Exception e) {
		        e.printStackTrace();
		        return "invalid";
		    }
		}

	@GetMapping("/findVehicle")
	@ResponseBody
	public VehicleViewDTO findVehicle(@RequestParam String vehicleNo) {
		Vehicle v = manager.findVehicleByVehicleNo(vehicleNo);

	    VehicleViewDTO dto = new VehicleViewDTO();
	    dto.setVehicleNo(v.getVehicleNo());
	    dto.setCategory(v.getCategory());
	    dto.setDriverName(v.getDriverName());
	    dto.setDriverPhoneNo(v.getDriverPhoneNo());

	    dto.setFcExpiryDate(v.getFcExpiryDate());
	    dto.setNocExpiryDate(v.getNocExpiryDate());
	    dto.setRcExpiryDate(v.getRcExpiryDate());
	    dto.setLicenseExpiryDate(v.getLicenseExpiryDate());
	    dto.setPucExpiryDate(v.getPucExpiryDate());
	    dto.setInsuranceExpiryDate(v.getInsuranceExpiryDate());

	    dto.setHasFc(v.getFcFile() != null);
	    dto.setHasNoc(v.getNocFile() != null);
	    dto.setHasRc(v.getRcFile() != null);
	    dto.setHasLicense(v.getLicenseFile() != null);
	    dto.setHasAadhar(v.getAadharFile() != null);
	    dto.setHasPuc(v.getPucFile() != null);
	    dto.setHasInsurance(v.getInsuranceFile() != null);

	    dto.setFcFileName(v.getFcFileName());
	    dto.setNocFileName(v.getNocFileName());
	    dto.setRcFileName(v.getRcFileName());
	    dto.setLicenseFileName(v.getLicenseFileName());
	    dto.setAadharFileName(v.getAadharFileName());
	    dto.setPucFileName(v.getPucFileName());
	    dto.setInsuranceFileName(v.getInsuranceFileName());

	    return dto;
	}
	
	
	 @GetMapping("/vehicle/{vehicleNo}/document/{type}")
	    public ResponseEntity<byte[]> previewDocument(
	            @PathVariable String vehicleNo,
	            @PathVariable String type) {

	        Vehicle v = manager.findVehicleByVehicleNo(vehicleNo);
	        if (v == null) {
	            return ResponseEntity.notFound().build();
	        }

	        byte[] file;
	        String fileName;
	        String fileType;

	        switch (type) {
	            case "fc" -> {
	                file = v.getFcFile();
	                fileName = v.getFcFileName();
	                fileType = v.getFcFileType();
	            }
	            case "noc" -> {
	                file = v.getNocFile();
	                fileName = v.getNocFileName();
	                fileType = v.getNocFileType();
	            }
	            case "rc" -> {
	                file = v.getRcFile();
	                fileName = v.getRcFileName();
	                fileType = v.getRcFileType();
	            }
	            case "license" -> {
	                file = v.getLicenseFile();
	                fileName = v.getLicenseFileName();
	                fileType = v.getLicenseFileType();
	            }
	            case "aadhar" -> {
	                file = v.getAadharFile();
	                fileName = v.getAadharFileName();
	                fileType = v.getAadharFileType();
	            }
	            case "puc" -> {
	                file = v.getPucFile();
	                fileName = v.getPucFileName();
	                fileType = v.getPucFileType();
	            }
	            case "insurance" -> {
	                file = v.getInsuranceFile();
	                fileName = v.getInsuranceFileName();
	                fileType = v.getInsuranceFileType();
	            }

	            default -> {
	                return ResponseEntity.badRequest().build();
	            }
	        }

	        if (file == null) {
	            return ResponseEntity.noContent().build();
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(
	                        fileType != null ? fileType : MediaType.APPLICATION_OCTET_STREAM_VALUE
	                ))
	                .header(
	                        HttpHeaders.CONTENT_DISPOSITION,
	                        "inline; filename=\"" + fileName + "\""
	                )
	                .body(file);
	    }

	
	    @GetMapping("/vehicle/{vehicleNo}/document/{type}/download")
	    public ResponseEntity<byte[]> downloadDocument(
	            @PathVariable String vehicleNo,
	            @PathVariable String type) {

	        Vehicle v = manager.findVehicleByVehicleNo(vehicleNo);
	        if (v == null) return ResponseEntity.notFound().build();

	        byte[] file;
	        String fileName;
	        String fileType;

	        switch (type) {
	            case "fc" -> {
	                file = v.getFcFile();
	                fileName = v.getFcFileName();
	                fileType = v.getFcFileType();
	            }
	            case "noc" -> {
	                file = v.getNocFile();
	                fileName = v.getNocFileName();
	                fileType = v.getNocFileType();
	            }
	            case "rc" -> {
	                file = v.getRcFile();
	                fileName = v.getRcFileName();
	                fileType = v.getRcFileType();
	            }
	            case "license" -> {
	                file = v.getLicenseFile();
	                fileName = v.getLicenseFileName();
	                fileType = v.getLicenseFileType();
	            }
	            case "aadhar" -> {
	                file = v.getAadharFile();
	                fileName = v.getAadharFileName();
	                fileType = v.getAadharFileType();
	            }
	            case "puc" -> {
	                file = v.getPucFile();
	                fileName = v.getPucFileName();
	                fileType = v.getPucFileType();
	            }
	            case "insurance" -> {
	                file = v.getInsuranceFile();
	                fileName = v.getInsuranceFileName();
	                fileType = v.getInsuranceFileType();
	            }

	            default -> {
	                return ResponseEntity.badRequest().build();
	            }
	        }

	        if (file == null) return ResponseEntity.noContent().build();

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(fileType))
	                .header(
	                    HttpHeaders.CONTENT_DISPOSITION,
	                    "attachment; filename=\"" + fileName + "\""
	                )
	                .body(file);
	    }
	    
	    @PostMapping("/savePayment")
	    @ResponseBody
	    public String savePayment(
	            @RequestParam String vehicleNo,
	            @RequestParam String driverName,
	            @RequestParam String paymentDate,
	            @RequestParam Double paymentAmount) {

	        Payment payment = new Payment();
	        payment.setVehicleNo(vehicleNo);
	        payment.setDriverName(driverName);
	        payment.setPaymentDate(LocalDate.parse(paymentDate));
	        payment.setPaymentAmount(paymentAmount);

	         paymentRepo.save(payment);

	        return "success";
	    }
	    
	    @PostMapping("/deleteVehicle")
		@ResponseBody
		public String deleteVehicle(@RequestParam String vehicleNo) {
	    	
	    	boolean isDeleted =  manager.deleteVehicle(vehicleNo);
	    	
	    	return isDeleted ? "success" : "invalid";
	    }
	    


}

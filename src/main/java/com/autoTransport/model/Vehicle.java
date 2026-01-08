package com.autoTransport.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleNo;
    private String category;
    private String driverName;
    private String driverPhoneNo;

    private String fcExpiryDate;
    private String nocExpiryDate;
    private String rcExpiryDate;
    private String licenseExpiryDate;
    private String pucExpiryDate;
    private String insuranceExpiryDate;
    
    private String fcFileType;
    private String nocFileType;
    private String rcFileType;
    private String licenseFileType;
    private String aadharFileType;
    private String pucFileType;
    private String insuranceFileType;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fcFile;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] nocFile;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] rcFile;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] aadharFile;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] pucFile;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] insuranceFile;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] licenseFile;

    // Optional: store original file names
    private String fcFileName;
    private String nocFileName;
    private String rcFileName;
    private String licenseFileName;
    private String aadharFileName;
    private String pucFileName;
    private String insuranceFileName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhoneNo() {
		return driverPhoneNo;
	}
	public void setDriverPhoneNo(String driverPhoneNo) {
		this.driverPhoneNo = driverPhoneNo;
	}
	public String getFcExpiryDate() {
		return fcExpiryDate;
	}
	public void setFcExpiryDate(String fcExpiryDate) {
		this.fcExpiryDate = fcExpiryDate;
	}
	public String getNocExpiryDate() {
		return nocExpiryDate;
	}
	public void setNocExpiryDate(String nocExpiryDate) {
		this.nocExpiryDate = nocExpiryDate;
	}
	public String getRcExpiryDate() {
		return rcExpiryDate;
	}
	public void setRcExpiryDate(String rcExpiryDate) {
		this.rcExpiryDate = rcExpiryDate;
	}
	public String getLicenseExpiryDate() {
		return licenseExpiryDate;
	}
	public void setLicenseExpiryDate(String licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}
	public byte[] getFcFile() {
		return fcFile;
	}
	public void setFcFile(byte[] fcFile) {
		this.fcFile = fcFile;
	}
	public byte[] getNocFile() {
		return nocFile;
	}
	public void setNocFile(byte[] nocFile) {
		this.nocFile = nocFile;
	}
	public byte[] getRcFile() {
		return rcFile;
	}
	public void setRcFile(byte[] rcFile) {
		this.rcFile = rcFile;
	}
	public byte[] getLicenseFile() {
		return licenseFile;
	}
	public void setLicenseFile(byte[] licenseFile) {
		this.licenseFile = licenseFile;
	}
	public String getFcFileName() {
		return fcFileName;
	}
	public void setFcFileName(String fcFileName) {
		this.fcFileName = fcFileName;
	}
	public String getNocFileName() {
		return nocFileName;
	}
	public void setNocFileName(String nocFileName) {
		this.nocFileName = nocFileName;
	}
	public String getRcFileName() {
		return rcFileName;
	}
	public void setRcFileName(String rcFileName) {
		this.rcFileName = rcFileName;
	}
	public String getLicenseFileName() {
		return licenseFileName;
	}
	public void setLicenseFileName(String licenseFileName) {
		this.licenseFileName = licenseFileName;
	}
	public String getFcFileType() {
		return fcFileType;
	}
	public void setFcFileType(String fcFileType) {
		this.fcFileType = fcFileType;
	}
	public String getNocFileType() {
		return nocFileType;
	}
	public void setNocFileType(String nocFileType) {
		this.nocFileType = nocFileType;
	}
	public String getRcFileType() {
		return rcFileType;
	}
	public void setRcFileType(String rcFileType) {
		this.rcFileType = rcFileType;
	}
	public String getLicenseFileType() {
		return licenseFileType;
	}
	public void setLicenseFileType(String licenseFileType) {
		this.licenseFileType = licenseFileType;
	}
	public String getAadharFileType() {
		return aadharFileType;
	}
	public void setAadharFileType(String aadharFileType) {
		this.aadharFileType = aadharFileType;
	}
	public byte[] getAadharFile() {
		return aadharFile;
	}
	public void setAadharFile(byte[] aadharFile) {
		this.aadharFile = aadharFile;
	}
	public byte[] getPucFile() {
		return pucFile;
	}
	public void setPucFile(byte[] pucFile) {
		this.pucFile = pucFile;
	}
	public byte[] getInsuranceFile() {
		return insuranceFile;
	}
	public void setInsuranceFile(byte[] insuranceFile) {
		this.insuranceFile = insuranceFile;
	}
	public String getAadharFileName() {
		return aadharFileName;
	}
	public void setAadharFileName(String aadharFileName) {
		this.aadharFileName = aadharFileName;
	}
	public String getPucFileName() {
		return pucFileName;
	}
	public void setPucFileName(String pucFileName) {
		this.pucFileName = pucFileName;
	}
	public String getInsuranceFileName() {
		return insuranceFileName;
	}
	public void setInsuranceFileName(String insuranceFileName) {
		this.insuranceFileName = insuranceFileName;
	}
	public String getPucExpiryDate() {
		return pucExpiryDate;
	}
	public void setPucExpiryDate(String pucExpiryDate) {
		this.pucExpiryDate = pucExpiryDate;
	}
	public String getInsuranceExpiryDate() {
		return insuranceExpiryDate;
	}
	public void setInsuranceExpiryDate(String insuranceExpiryDate) {
		this.insuranceExpiryDate = insuranceExpiryDate;
	}
	public String getPucFileType() {
		return pucFileType;
	}
	public void setPucFileType(String pucFileType) {
		this.pucFileType = pucFileType;
	}
	public String getInsuranceFileType() {
		return insuranceFileType;
	}
	public void setInsuranceFileType(String insuranceFileType) {
		this.insuranceFileType = insuranceFileType;
	}
	
	
}


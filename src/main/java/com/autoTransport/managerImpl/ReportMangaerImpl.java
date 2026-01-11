package com.autoTransport.managerImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autoTransport.manager.ReportManager;
import com.autoTransport.model.Payment;
import com.autoTransport.repository.PaymentRepository;

@Service
public class ReportMangaerImpl implements ReportManager{
	
	@Autowired
    private PaymentRepository repository;

	@Override
	public ByteArrayInputStream generateExcel(LocalDate fromDate, LocalDate toDate) throws IOException {

	    List<Payment> payments =
	            repository.findByPaymentDateBetween(fromDate, toDate);

	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Payment Report");

	    // ================= TITLE =================
	    Row titleRow = sheet.createRow(0);
	    titleRow.setHeightInPoints(30);

	    Cell titleCell = titleRow.createCell(0);
	    titleCell.setCellValue("VEHICLE PAYMENT REPORT");

	    CellStyle titleStyle = workbook.createCellStyle();
	    Font titleFont = workbook.createFont();
	    titleFont.setBold(true);
	    titleFont.setFontHeightInPoints((short) 18);
	    titleFont.setColor(IndexedColors.BLACK.getIndex());
	    titleStyle.setFont(titleFont);
	    titleStyle.setAlignment(HorizontalAlignment.CENTER);
	    titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

	    // Gold color (#FFCC00)
	    XSSFColor goldColor =
	            new XSSFColor(new java.awt.Color(255, 204, 0), null);
	    titleStyle.setFillForegroundColor(goldColor);
	    titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    titleCell.setCellStyle(titleStyle);
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

	    // ================= DATE RANGE =================
	    Row dateRow = sheet.createRow(1);
	    dateRow.setHeightInPoints(20);

	    Cell dateCell = dateRow.createCell(0);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    String dateRangeText =
	            "From : " + fromDate.format(formatter)
	            + "    To : " + toDate.format(formatter);

	    dateCell.setCellValue(dateRangeText);
	    sheet.createRow(2);
	    CellStyle dateRangeStyle = workbook.createCellStyle();
	    Font dateFont = workbook.createFont();
	    dateFont.setBold(true);
	    dateFont.setFontHeightInPoints((short) 11);
	    dateFont.setColor(IndexedColors.DARK_BLUE.getIndex());
	    dateRangeStyle.setFont(dateFont);
	    dateRangeStyle.setAlignment(HorizontalAlignment.CENTER);
	    dateRangeStyle.setVerticalAlignment(VerticalAlignment.CENTER);

	    dateCell.setCellStyle(dateRangeStyle);
	    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));

	    // ================= HEADER =================
	    Row headerRow = sheet.createRow(3);
	    headerRow.setHeightInPoints(22);

	    String[] headers = {
	            "Vehicle Number",
	            "Driver Name",
	            "Payment Amount (₹)",
	            "Payment Date"
	    };

	    CellStyle headerStyle = workbook.createCellStyle();
	    Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setColor(IndexedColors.WHITE.getIndex());
	    headerStyle.setFont(headerFont);
	    headerStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    headerStyle.setBorderBottom(BorderStyle.THIN);
	    headerStyle.setBorderTop(BorderStyle.THIN);
	    headerStyle.setBorderLeft(BorderStyle.THIN);
	    headerStyle.setBorderRight(BorderStyle.THIN);

	    for (int i = 0; i < headers.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headers[i]);
	        cell.setCellStyle(headerStyle);
	    }

	    // ================= STYLES =================
	    DataFormat format = workbook.createDataFormat();

	    // Currency Style (₹)
	    CellStyle currencyStyle = workbook.createCellStyle();
	    currencyStyle.setDataFormat(format.getFormat("₹#,##0.00"));
	    currencyStyle.setBorderBottom(BorderStyle.THIN);
	    currencyStyle.setBorderTop(BorderStyle.THIN);
	    currencyStyle.setBorderLeft(BorderStyle.THIN);
	    currencyStyle.setBorderRight(BorderStyle.THIN);

	    // Date Style (dd-MM-yyyy)
	    CellStyle dateStyle = workbook.createCellStyle();
	    dateStyle.setDataFormat(format.getFormat("dd-MM-yyyy"));
	    dateStyle.setBorderBottom(BorderStyle.THIN);
	    dateStyle.setBorderTop(BorderStyle.THIN);
	    dateStyle.setBorderLeft(BorderStyle.THIN);
	    dateStyle.setBorderRight(BorderStyle.THIN);

	    // Text Style
	    CellStyle textStyle = workbook.createCellStyle();
	    textStyle.setBorderBottom(BorderStyle.THIN);
	    textStyle.setBorderTop(BorderStyle.THIN);
	    textStyle.setBorderLeft(BorderStyle.THIN);
	    textStyle.setBorderRight(BorderStyle.THIN);

	    // ================= DATA =================
	    int rowIdx = 4;
	    for (Payment p : payments) {

	        Row row = sheet.createRow(rowIdx++);

	        Cell vCell = row.createCell(0);
	        vCell.setCellValue(p.getVehicleNo());
	        vCell.setCellStyle(textStyle);

	        Cell dCell = row.createCell(1);
	        dCell.setCellValue(p.getDriverName());
	        dCell.setCellStyle(textStyle);

	        Cell aCell = row.createCell(2);
	        aCell.setCellValue(p.getPaymentAmount());
	        aCell.setCellStyle(currencyStyle);

	        Cell pdCell = row.createCell(3);
	        pdCell.setCellValue(java.sql.Date.valueOf(p.getPaymentDate()));
	        pdCell.setCellStyle(dateStyle);
	    }

	    // ================= COLUMN WIDTH =================
	    sheet.setColumnWidth(0, 20 * 256);
	    sheet.setColumnWidth(1, 22 * 256);
	    sheet.setColumnWidth(2, 18 * 256);
	    sheet.setColumnWidth(3, 18 * 256);

	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    workbook.write(out);
	    workbook.close();

	    return new ByteArrayInputStream(out.toByteArray());
	}

	
    
}

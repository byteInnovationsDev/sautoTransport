package com.autoTransport.controller;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autoTransport.manager.*;

@RestController
public class PaymentReportController {

    private final ReportManager service;

    public PaymentReportController(ReportManager service) {
        this.service = service;
    }

    @GetMapping("/report")
    public ResponseEntity<InputStreamResource> downloadReport(
            @RequestParam String fromDate,
            @RequestParam String toDate) throws Exception {

        ByteArrayInputStream excel =
                service.generateExcel(
                        LocalDate.parse(fromDate),
                        LocalDate.parse(toDate)
                );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=payment_report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        ))
                .body(new InputStreamResource(excel));
    }
}

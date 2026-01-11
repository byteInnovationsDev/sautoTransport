package com.autoTransport.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

public interface ReportManager {
	
	ByteArrayInputStream generateExcel(LocalDate fromDate, LocalDate toDate) throws IOException;
}

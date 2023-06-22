package com.example.flightviewmodel.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ViewModelReceiver {
    private final String excelFile = "flight_data.xlsx";
    private static final Logger logger = LoggerFactory.getLogger(ViewModelReceiver.class);


    @KafkaListener(topics = "ds_firstSeen")
    public void receiveFirstSeen(List<Long> values) {
        writeToExcelSheet("firstSeen", values);
    }

    @KafkaListener(topics = "ds_lastSeen")
    public void receiveLastSeen(List<Long> values) {
        writeToExcelSheet("lastSeen", values);
    }

    private void writeToExcelSheet(String sheetName, List<Long> values) {
        try (Workbook workbook = getOrCreateWorkbook()) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

//            int lastRowNum = sheet.getLastRowNum();
//            for (int i = 0; i < values.size(); i++) {
//                Row row = sheet.createRow(lastRowNum + i + 1);
//                Cell cell = row.createCell(0);
//                cell.setCellValue(values.get(i));
//            }

            int lastRowNum = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRowNum + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue("abc");

            FileOutputStream outputStream = new FileOutputStream(excelFile);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Workbook getOrCreateWorkbook() {
        Workbook workbook;
        try {
            workbook = WorkbookFactory.create(new File(excelFile));
        } catch (IOException e) {
            workbook = new XSSFWorkbook();
        }
        logger.info("Workbook: " + workbook);
        return workbook;
    }
}

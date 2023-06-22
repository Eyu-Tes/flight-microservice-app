package com.example.flightviewmodel.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    private FileWriter fileWriter;

    public CsvWriter(String filename) throws IOException {
        fileWriter = new FileWriter(filename, true); // Open the file in append mode
    }

    public void appendData(List<String> data) throws IOException {
        for (String value : data) {
            fileWriter.write(value);
            fileWriter.write(System.lineSeparator());
        }
        fileWriter.flush(); // Flush the buffer to ensure the data is written to the file immediately
//        fileWriter.close();
    }

}


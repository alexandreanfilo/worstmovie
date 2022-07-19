package com.texoit.worstmovie;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
public class CSVService {

   public Iterable<CSVRecord> getCSVRecords(String fileName) {
      File moviesFile = new File(fileName);

      CSVFormat format;
      CSVParser csv;

      format = CSVFormat.newFormat(';')
            .withHeader("year", "title", "studios", "producers", "winner")
            .withSkipHeaderRecord(true);

      try {
         csv = CSVParser.parse(moviesFile, Charset.defaultCharset(), format);

         return csv.getRecords();
      } catch (IOException e) {
         e.printStackTrace();
      }

      return null;
   }

   public String findStringValue(CSVRecord row, String header) {
      String value = row.get(header).trim();

      if (value.isEmpty()) {
         return null;
      }

      return value;
   }

   public Integer findIntegerValue(CSVRecord row, String header) {
      return Integer.parseInt(row.get(header));
   }

   public Boolean findBooleanValue(CSVRecord row, String header, String trueCompare) {
      return row.get(header).trim().equals(trueCompare);
   }

   public List<String> findSplittedValues(CSVRecord row, String header, String split) {
      String[] splittedValues = row.get(header).split(split);
      List<String> values = new ArrayList<String>();

      for (String name : splittedValues) {
         name = name.trim();
         if (!name.equals("")) {
            values.add(name);
         }
      }

      return values;
   }

}

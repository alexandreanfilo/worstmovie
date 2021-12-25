package com.texoit.worstmovie;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
public class CSVService {

   public CSVService() {
      System.out.println("Service init");
   }

   public void importMovies() {
      try {
         System.out.println("Importing movies...");

         File moviesFile = new File("movielist.csv");

         CSVFormat format = CSVFormat.newFormat(';').withHeader("year", "title", "studios", "producers", "winner");
         CSVParser csv = CSVParser.parse(moviesFile, Charset.defaultCharset(), format);

         Iterable<CSVRecord> csvRecords = csv.getRecords();

         for (CSVRecord row : csvRecords) {
            System.out.println(row.get("year"));
            System.out.println(row.get("title"));
            System.out.println(row.get("studios"));
            System.out.println(row.get("producers"));
            System.out.println(row.get("winner"));
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}

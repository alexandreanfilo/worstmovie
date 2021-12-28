package com.texoit.worstmovie;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CSVService {

   private final String fileName = "movielist.csv";

   private final StudioRepository studioRepository;
   private final ProducerRepository producerRepository;
   private final MovieRepository movieRepository;

   @Autowired
   public CSVService(StudioRepository sR, ProducerRepository pR, MovieRepository mR) {
      this.studioRepository = sR;
      this.producerRepository = pR;
      this.movieRepository = mR;

      this.importMovies();
   }

   public void importMovies() {
      try {
         System.out.println("Importing movies...");

         File moviesFile = new File(this.fileName);

         CSVFormat format = CSVFormat.newFormat(';')
               .withHeader("year", "title", "studios", "producers", "winner")
               .withSkipHeaderRecord(true);
         CSVParser csv = CSVParser.parse(moviesFile, Charset.defaultCharset(), format);

         Iterable<CSVRecord> csvRecords = csv.getRecords();

         for (CSVRecord row : csvRecords) {
            var year = Integer.parseInt(row.get("year"));
            var title = row.get("title");
            var winner = row.get("winner").trim().equals("yes");

            Movie movie = new Movie(year, title, winner);

            String[] studioNames = row.get("studios").split(",| and ");
            for (String studioName : studioNames) {
               studioName = studioName.trim();
               if (studioName.equals(""))
                  continue;

               Studio studio = studioRepository.findByName(studioName);
               if (studio == null) {
                  studio = new Studio(studioName);
                  studioRepository.save(studio);
               }

               movie.addStudio(studio);
            }

            String[] producerNames = row.get("producers").split(",| and ");
            for (String producerName : producerNames) {
               producerName = producerName.trim();
               if (producerName.equals(""))
                  continue;

               Producer producer = producerRepository.findByName(producerName);
               if (producer == null) {
                  producer = new Producer(producerName);
                  producerRepository.save(producer);
               }

               movie.addProducer(producer);
            }

            this.movieRepository.save(movie);
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}

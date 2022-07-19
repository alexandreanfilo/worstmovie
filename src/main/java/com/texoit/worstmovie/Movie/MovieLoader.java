package com.texoit.worstmovie.Movie;

import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.texoit.worstmovie.CSVService;
import com.texoit.worstmovie.Producer.Producer;
import com.texoit.worstmovie.Producer.ProducerRepository;
import com.texoit.worstmovie.Studio.Studio;
import com.texoit.worstmovie.Studio.StudioRepository;

@Component
public class MovieLoader implements ApplicationRunner {

    private final String fileName = "movielist.csv";

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CSVService csvService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.importMovies();
    }

    public void importMovies() {
        System.out.print("Importing movies... ");

        for (CSVRecord row : this.csvService.getCSVRecords(this.fileName)) {
            var year = this.csvService.findIntegerValue(row, "year");
            var title = this.csvService.findStringValue(row, "title");
            var winner = this.csvService.findBooleanValue(row, "winner", "yes");

            if (year <= 0 || title.isEmpty()) {
                continue;
            }

            Movie movie = new Movie(year, title, winner);

            // Travar headers não existentes
            List<String> studiosNames = this.csvService.findSplittedValues(row, "studios", ",| and ");
            List<String> producersNames = this.csvService.findSplittedValues(row, "producers", ",| and ");

            for (String studioName : studiosNames) {
                Studio studio = studioRepository.findByName(studioName);
                if (studio == null) {
                    studio = new Studio(studioName);
                    studioRepository.save(studio);
                }

                movie.addStudio(studio);
            }

            for (String producerName : producersNames) {
                Producer producer = producerRepository.findByName(producerName);
                if (producer == null) {
                    producer = new Producer(producerName);
                    producerRepository.save(producer);
                }

                movie.addProducer(producer);
            }

            this.movieRepository.save(movie);
        }

        System.out.print("done.");
        System.out.println("");
    }

}

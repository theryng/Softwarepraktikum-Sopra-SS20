package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Occurrence;
import de.hohenheim.sopraproject.repository.OccurenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class OccurrenceService{

    @Autowired
    OccurenceRepository occurrenceRepository;

    public Occurrence saveOccurrence(Occurrence occurrence){
        return occurrenceRepository.save(occurrence);
    }

    public List<Occurrence> findAllOccurrences() {
        return occurrenceRepository.findAll();
    }

    public Occurrence findByOccurrenceID(Integer id) {
        return occurrenceRepository.findByOccurrenceID(id);
    }

    public void deleteByOccurrenceID(Integer id) {
        occurrenceRepository.deleteById(id);
    }

    /**
     * gets all the upcoming occurrences of the day and sorts them by time in ascending order
     */
    public List<Occurrence> findtAllUpcomingOccurrences(){
        List<Occurrence> allOccurrences = occurrenceRepository.findAll();
        List<Occurrence> upcoming = new LinkedList<>();
        LocalDate date = LocalDate.now();

        for(Occurrence occurrence : allOccurrences) {
            LocalDate occurrenceDate = occurrence.getDate();
            if (occurrenceDate.equals(date)) {
                upcoming.add(occurrence);
            }
        }
        Collections.sort(upcoming, new Comparator<Occurrence>() {
            @Override
            public int compare(Occurrence o1, Occurrence o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });
        return upcoming;
    }
}

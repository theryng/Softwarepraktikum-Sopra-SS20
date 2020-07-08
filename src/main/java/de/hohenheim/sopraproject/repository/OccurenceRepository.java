package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.Occurrence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccurenceRepository extends JpaRepository<Occurrence, Integer> {
    Occurrence findByOccurrenceID(Integer OccurrenceID);
}


package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}

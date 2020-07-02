package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }



    public void deleteByEventId(Integer id){
        eventRepository.deleteById(id);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }


    public Optional<Event> findByEventId(Integer id){
      return eventRepository.findById(id);
   }

}

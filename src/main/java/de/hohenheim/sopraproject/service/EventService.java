package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }


    public void deleteByEventID(Integer id){
        eventRepository.deleteById(id);
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }


    public Event findByEventID(Integer id){
     return  eventRepository.findByEventID(id);
   }

}

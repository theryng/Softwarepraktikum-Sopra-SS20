package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Contacthistory;
import de.hohenheim.sopraproject.repository.ContacthistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContacthistoryService {

    @Autowired
    private ContacthistoryRepository contacthistoryRepository;

    public Contacthistory saveContacthistory(Contacthistory contacthistory) {
        return contacthistoryRepository.save(contacthistory);
    }

    public List<Contacthistory> findAllContacthistory() {
        return contacthistoryRepository.findAll();
    }

}

package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactHistoryService {

    @Autowired
    private ContactHistoryRepository contacthistoryRepository;

    public ContactHistory saveContacthistory(ContactHistory contacthistory) {
        return contacthistoryRepository.save(contacthistory);
    }

    public List<ContactHistory> findAllContacthistory() {
        return contacthistoryRepository.findAll();
    }

}

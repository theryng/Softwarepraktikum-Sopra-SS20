package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactHistoryService {

    @Autowired
    private ContactHistoryRepository contactHistoryRepository;

    public ContactHistory saveContacthistory(ContactHistory contacthistory) {
        return contactHistoryRepository.save(contacthistory);
    }

    public List<ContactHistory> findAllContacthistory() {
        return contactHistoryRepository.findAll();
    }

}

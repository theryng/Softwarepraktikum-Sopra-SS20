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

    public List<ContactHistory> findAllContactHistory() {
        return contacthistoryRepository.findAll();
    }

    public ContactHistory findByContactHistoryID(Integer id) {
        return contacthistoryRepository.findByContactHistoryID(id);
    }

    public void deleteByContactHistoryID(Integer id) {
        contacthistoryRepository.deleteById(id);
    }
}

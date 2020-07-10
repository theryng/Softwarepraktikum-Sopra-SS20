package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> findAllContacts(){
            return contactRepository.findAll();
    }

    public Contact findByContactID(Integer id){
        return contactRepository.findByContactID(id);
    }

    public void deleteByContactID(Integer id){
        contactRepository.deleteById(id);
    }
}

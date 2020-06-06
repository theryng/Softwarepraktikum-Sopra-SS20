package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Contact findByContactID(Integer ContactID);
}

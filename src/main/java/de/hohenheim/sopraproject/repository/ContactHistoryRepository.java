package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactHistoryRepository extends JpaRepository<ContactHistory, Integer> {
    ContactHistory findByContactHistoryID(Integer ContactHistoryID);
}

package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.EditingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditingHistoryRepository extends JpaRepository<EditingHistory, Integer> {
    EditingHistory findByEditingHistoryID(Integer EditingHistoryID);
}

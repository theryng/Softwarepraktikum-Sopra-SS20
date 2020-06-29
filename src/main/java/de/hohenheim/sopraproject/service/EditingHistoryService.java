package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.EditingHistory;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.repository.EditingHistoryRepository;
import de.hohenheim.sopraproject.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditingHistoryService {

    @Autowired
    private EditingHistoryRepository editingHistoryRepository;

    public EditingHistory saveEditingHistory(EditingHistory editingHistory) {
        return editingHistoryRepository.save(editingHistory);
    }

    public List<EditingHistory> findAllEditingHistory() {
        return editingHistoryRepository.findAll();
    }

}

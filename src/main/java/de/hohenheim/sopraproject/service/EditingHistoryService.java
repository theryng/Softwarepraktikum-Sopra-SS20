package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.EditingHistory;
import de.hohenheim.sopraproject.repository.EditingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditingHistoryService {

    @Autowired
    private EditingHistoryRepository editingHistoryRepository;

    public EditingHistory saveEditingHistory(EditingHistory editingHistory) {
        List<EditingHistory> list = editingHistoryRepository.findAll();
        for(EditingHistory elem : list){
            if(elem.getDate().equals(editingHistory.getDate())
            && elem.getObjectEdited().equals(editingHistory.getObjectEdited())
            && elem.getUser().equals(editingHistory.getUser())
            ){
                System.out.println("Double Entry");
                return null;
            }
        }
        if(list.size()>8){
            System.out.println("oversized");
            list.remove(0);
            editingHistoryRepository.deleteAll();
            editingHistoryRepository.saveAll(list);
        }
        return editingHistoryRepository.save(editingHistory);
    }


    public List<EditingHistory> findAllEditingHistory() {
        return editingHistoryRepository.findAll();
    }

}

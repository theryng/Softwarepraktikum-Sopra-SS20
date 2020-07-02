package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Institute;
import de.hohenheim.sopraproject.repository.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteService {

    @Autowired
    private InstituteRepository instituteRepository;

    public Institute saveInstitute(Institute institute){
        return instituteRepository.save(institute);
    }

    public List<Institute> findAllInstitutes() {
        return instituteRepository.findAll();
    }

    public Institute findByInstitutesID(Integer id) {
        return instituteRepository.findByInstituteID(id);
    }

    public void deleteByInstituteID(Integer id) {
        instituteRepository.deleteById(id);
    }
}

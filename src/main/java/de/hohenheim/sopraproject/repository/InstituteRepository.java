package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituteRepository extends JpaRepository<Institute, Integer> {
    Institute findByInstituteID(Integer InstituteID);
}

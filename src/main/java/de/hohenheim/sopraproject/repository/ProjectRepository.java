package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findByProjectID(Integer projectID);
}

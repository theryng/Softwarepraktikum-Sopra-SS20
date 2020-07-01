package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project){
        return projectRepository.save(project);
    }

    public List<Project> findAllInstitutes() {
        return projectRepository.findAll();
    }
}

package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Role;
import de.hohenheim.sopraproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Roleservice class of the application
 *
 * This service helps the programm accessing the roles and bind it to a certain user of the application
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    //Saves a role to a user
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}

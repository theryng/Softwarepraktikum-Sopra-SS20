package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.User;
import de.hohenheim.sopraproject.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
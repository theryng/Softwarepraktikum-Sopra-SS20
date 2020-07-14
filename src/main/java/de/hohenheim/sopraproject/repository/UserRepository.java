package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.User;
import de.hohenheim.sopraproject.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class to find user by given attributes
 *
 * this class contains methods in order to find user objects by given attributes
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Searches for a user with a specific username declared in the method
     * @param username
     * @return user-Objekt
     */
    User findByUsername(String username);

    /**
     * Searches for a user with a specific userId declared in the method
     * @param UserId
     * @return user-Objekt
     */
    User findByUserId(Integer UserId);

    User findUsersByUsername(String username);
}
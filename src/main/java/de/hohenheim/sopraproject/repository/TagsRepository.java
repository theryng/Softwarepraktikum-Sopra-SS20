package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.Tags;
import de.hohenheim.sopraproject.entity.User;
import de.hohenheim.sopraproject.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags, Integer> {
    Tags findByTagsID(Integer tagsID);
}
package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {
    Relationship findByRelationshipID(Integer RelationshipID);
}

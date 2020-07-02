package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    public Relationship saveRelationship(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    public List<Relationship> findAllRelationships(){
        return relationshipRepository.findAll();
    }

    public Relationship findByRelationshipID(Integer id){
        return relationshipRepository.findByRelationshipID(id);
    }
    public void deleteByRelationshipID(Integer id){
        relationshipRepository.deleteById(id);
    }
}

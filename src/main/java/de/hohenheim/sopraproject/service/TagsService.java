package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.entity.Tags;
import de.hohenheim.sopraproject.repository.EventRepository;
import de.hohenheim.sopraproject.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    public Tags saveTags(Tags tags) {
        return tagsRepository.save(tags);
    }

    public List<Tags> findAllTags() {
        return tagsRepository.findAll();
    }

    public Tags findByTagID(Integer id) {
        return tagsRepository.findByTagsID(id);
    }

}
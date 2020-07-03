package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Tags;

public class TagsDTO {
    private Tags tag = new Tags();
    private String type;
    private String originalID;
    private String tagID;
    private String searchWord;

    public TagsDTO() {
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginalID() {
        return originalID;
    }

    public void setOriginalID(String originalID) {
        this.originalID = originalID;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}

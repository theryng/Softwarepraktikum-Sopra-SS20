package de.hohenheim.sopraproject.dto;

import java.util.LinkedList;
import java.util.List;

public class SearchDTO {
    private String searchWord;
    private String selectedSearchValue;
    private List<String> searchOptions = new LinkedList<String>();

    public SearchDTO() {
        searchOptions.add("Tag");
        searchOptions.add("Name");
        searchOptions.add("Studium");
    }

    public List getSearchOptions() {
        return searchOptions;
    }

    public void setSearchOptions(List searchOptions) {
        this.searchOptions = searchOptions;
    }

    public String getSearchWord() {
        return searchWord;
    }


    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getSelectedSearchValue() {
        return selectedSearchValue;
    }

    public void setSelectedSearchValue(String selectedSearchValue) {
        this.selectedSearchValue = selectedSearchValue;
    }
}

package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Institute;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class InstituteDTO {
    private Institute viewInstituteTemp;
    private List<Institute> allInstitutes = new LinkedList<>();
    private Set<Institute> foundInstitutes = new HashSet<>();
    public boolean hasError = false;
    private String searchWord;

    public InstituteDTO() {
    }

    public Institute getViewInstituteTemp() {
        return viewInstituteTemp;
    }

    public void setViewInstituteTemp(Institute viewInstituteTemp) {
        this.viewInstituteTemp = viewInstituteTemp;
    }

    public List<Institute> getAllInstitutes() {
        return allInstitutes;
    }

    public void setAllInstitutes(List<Institute> allInstitutes) {
        this.allInstitutes = allInstitutes;
    }

    public Set<Institute> getFoundInstitutes() {
        return foundInstitutes;
    }

    public void setFoundInstitutes(Set<Institute> foundInstitutes) {
        this.foundInstitutes = foundInstitutes;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}

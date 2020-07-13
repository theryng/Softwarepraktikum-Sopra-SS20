package de.hohenheim.sopraproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
public class EditingHistory {
    @Id
    @GeneratedValue
    private Integer editingHistoryID;
    private String user;
    private String objectEdited;
    private String date;


    public EditingHistory(){
    }

    public EditingHistory(String user, String objectEdited, String date){
        this.user = user;
        this.objectEdited = objectEdited;
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getObjectEdited() {
        return objectEdited;
    }

    public void setObjectEdited(String objectEdited) {
        this.objectEdited = objectEdited;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getEditingHistoryID() {
        return editingHistoryID;
    }

    public void setEditingHistoryID(Integer editingHistoryID) {
        this.editingHistoryID = editingHistoryID;
    }

    }



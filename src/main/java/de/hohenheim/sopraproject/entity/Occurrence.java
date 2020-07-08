package de.hohenheim.sopraproject.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class defines all the occurrences that the admin has
 */
@Entity
public class Occurrence {

    @Id
    @GeneratedValue
    private Integer occurrenceID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String description;

    private LocalTime startTime;

    private LocalTime endTime;

    private String title;

    public Occurrence(){
        //for hibernate
    }

    public Integer getOccurrenceID() {
        return occurrenceID;
    }

    public void setOccurrenceID(Integer occurenceID) {
        this.occurrenceID = occurenceID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(int year, int month, int day) {
        date = LocalDate.of(year, month, day);;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setStartTime(int hours, int minutes) {
        startTime = LocalTime.of(hours, minutes);
    }

    public void setEndTime(int hours, int minutes) {
        endTime = LocalTime.of(hours, minutes);
    }
}

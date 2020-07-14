package de.hohenheim.sopraproject.repository;

import de.hohenheim.sopraproject.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    Meeting findByMeetingID(Integer MeetingID);
}


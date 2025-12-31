package com.mindteck.common.repository;

import com.mindteck.common.models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Meeting getById(Long meetingId);

    Meeting getByFormUniqueId(Long formUniqueId);
}

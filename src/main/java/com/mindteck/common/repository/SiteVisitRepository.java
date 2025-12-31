package com.mindteck.common.repository;

import com.mindteck.common.models.SiteVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteVisitRepository  extends JpaRepository<SiteVisit, Long> {

    SiteVisit getByFormUniqueId(Long formUniqueId);
}

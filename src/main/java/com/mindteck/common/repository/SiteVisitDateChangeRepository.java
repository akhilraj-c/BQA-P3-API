package com.mindteck.common.repository;

import com.mindteck.common.models.SiteVisitDateChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteVisitDateChangeRepository extends JpaRepository<SiteVisitDateChange , Long> {

    SiteVisitDateChange findByFormUniqueId(Long formUniqueId);
}

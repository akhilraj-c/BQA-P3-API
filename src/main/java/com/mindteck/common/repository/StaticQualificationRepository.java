package com.mindteck.common.repository;

import com.mindteck.common.models.StaticQualificationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaticQualificationRepository extends JpaRepository<StaticQualificationDetails,Long> {
}

package com.mindteck.common.repository;

import com.mindteck.common.models.GDQDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GDQDocumentRepository extends JpaRepository<GDQDocument, Long> {

    GDQDocument findOneByFormUniqueId(Long formUniqueId);
}

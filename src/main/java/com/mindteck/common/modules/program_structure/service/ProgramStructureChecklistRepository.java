package com.mindteck.common.modules.program_structure.service;

import com.mindteck.common.modules.program_structure.model.ProgramStructureChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramStructureChecklistRepository extends JpaRepository<ProgramStructureChecklist, Long> {
    List<ProgramStructureChecklist> findByFormUniqueId(Long formUniqueId);

    @Query(value = "DELETE  " +
            "FROM tbl_program_structure_checklist WHERE form_unique_id =:formUniqueId AND sl_no=:slNo", nativeQuery = true)
    @Modifying
    void deleteByFormUniqueIdIdAndSlNo(Long formUniqueId,Integer slNo);

    @Query(value = "SELECT * FROM tbl_program_structure_checklist WHERE form_unique_id =:formUniqueId AND sl_no=:slNo",nativeQuery = true)
    List<ProgramStructureChecklist> getChecklistByFormUniqueIdAndSlNo(Long formUniqueId,Integer slNo);
}


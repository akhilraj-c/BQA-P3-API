package com.mindteck.common.modules.program_structure;

import com.mindteck.common.modules.program_structure.model.ProgramStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramStructureRepository extends JpaRepository<ProgramStructure, Long> {

    @Query(value = "SELECT * " +
            "FROM tbl_program_structure WHERE form_unique_id =:formUniqueId", nativeQuery = true)
   List<ProgramStructure> getProgramStructures(Long formUniqueId);

    @Query(value = "SELECT * " +
            "FROM tbl_program_structure WHERE form_unique_id =:formUniqueId and unit_code=:unitCode", nativeQuery = true)
    ProgramStructure getProgramStructure(Long formUniqueId,String unitCode);

    @Query(value = "DELETE  " +
            "FROM tbl_program_structure WHERE form_unique_id =:formUniqueId", nativeQuery = true)
    @Modifying
    void delete(Long formUniqueId);

    @Query(value = "DELETE  " +
            "FROM tbl_program_structure WHERE form_unique_id =:formUniqueId and " +
            "unit_code=:unitCode and " +
            "version=:version", nativeQuery = true)
    @Modifying
    void delete(Long formUniqueId, String unitCode, Integer version);

}

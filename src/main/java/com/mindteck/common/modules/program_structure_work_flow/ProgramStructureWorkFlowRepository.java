package com.mindteck.common.modules.program_structure_work_flow;

import com.mindteck.common.modules.program_structure_work_flow.model.ProgramStructureWorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramStructureWorkFlowRepository extends JpaRepository<ProgramStructureWorkFlow, Long> {

    @Query(value = "SELECT * " +
            "FROM tbl_program_structure_work_flow WHERE form_unique_id =:formUniqueId", nativeQuery = true)
   List<ProgramStructureWorkFlow> getProgramStructureFlows(Long formUniqueId);

    @Query(value = "SELECT * " +
            "FROM tbl_program_structure_work_flow WHERE form_unique_id =:formUniqueId and " +
            "program_tag=:programTag and " +
            "version=:version", nativeQuery = true)
    List<ProgramStructureWorkFlow> getProgramStructureFlow(Long formUniqueId, String programTag, Integer version);

    @Modifying
    @Query(value = "DELETE " +
            "FROM tbl_program_structure_work_flow WHERE form_unique_id =:formUniqueId and " +
            "program_tag=:programTag and " +
            "tag=:tag and " +
            "version=:version", nativeQuery = true)
    void deleteProgramStructureFlow(Long formUniqueId, String programTag,String tag, Integer version);

    @Modifying
    @Query(value = "DELETE " +
            "FROM tbl_program_structure_work_flow WHERE form_unique_id =:formUniqueId" , nativeQuery = true)
    void deleteProgramStructureFlow(Long formUniqueId);

}

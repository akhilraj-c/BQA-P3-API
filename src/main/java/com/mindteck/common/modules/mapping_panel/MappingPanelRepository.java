package com.mindteck.common.modules.mapping_panel;

import com.mindteck.common.modules.mapping_panel.model.MappingPanel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MappingPanelRepository extends JpaRepository<MappingPanel, Long> {

    @Query(value = "SELECT * " +
            "FROM tbl_mapping_panel WHERE form_unique_id =:formUniqueId", nativeQuery = true)
   List<MappingPanel> getProgramStructures(Long formUniqueId);
    @Query(value = "SELECT * " +
            "FROM tbl_mapping_panel WHERE id =:id", nativeQuery = true)
    MappingPanel getPanel(Long id);

    @Query(value = "DELETE " +
            "FROM tbl_mapping_panel WHERE form_unique_id =:formUniqueId", nativeQuery = true)
    @Modifying
    void delete(Long formUniqueId);

}

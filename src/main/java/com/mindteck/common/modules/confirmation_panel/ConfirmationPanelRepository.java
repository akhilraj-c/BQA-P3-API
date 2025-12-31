package com.mindteck.common.modules.confirmation_panel;

import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationPanelRepository extends JpaRepository<ConfirmationPanel, Long> {

    @Query(value = "SELECT * " +
            "FROM tbl_confirmation_panel WHERE form_unique_id =:formUniqueId", nativeQuery = true)
    List<ConfirmationPanel> getConfirmationPanels(Long formUniqueId);

    @Query(value = "SELECT * " +
            "FROM tbl_confirmation_panel WHERE id =:id", nativeQuery = true)
    ConfirmationPanel getMappingPanel(Long id);

    @Query(value = "DELETE " +
            "FROM tbl_confirmation_panel WHERE form_unique_id =:formUniqueId", nativeQuery = true)
    @Modifying
    void delete(Long formUniqueId);

}

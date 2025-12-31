package com.mindteck.common.modules.date_extension;

import com.mindteck.common.modules.date_extension.model.DateExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateExtensionRepository extends JpaRepository<DateExtension, Long> {


    @Query(value = "SELECT * FROM tbl_date_extension WHERE form_unique_id=:formUniqueId AND type=:type AND status=:status", nativeQuery = true)
    DateExtension getDateExtension(Long formUniqueId, Integer type, Integer status);


    @Query(value = "SELECT * FROM tbl_date_extension WHERE form_unique_id=:formUniqueId AND status=:status", nativeQuery = true)
    List<DateExtension> getDateExtensions(Long formUniqueId, Integer status);

    @Query(value = "SELECT * FROM tbl_date_extension WHERE form_unique_id=:formUniqueId", nativeQuery = true)
    List<DateExtension> getDateExtensions(Long formUniqueId);

    @Query(value = "DELETE FROM tbl_date_extension WHERE form_unique_id=:formUniqueId AND type=:type", nativeQuery = true)
    @Modifying
    void deleteDateExtension(Long formUniqueId, Integer type);
}

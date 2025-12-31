package com.mindteck.common.repository;

import com.mindteck.common.models.AppStatus;
import com.mindteck.common.modules.user.model.rest.GetStatusListResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<AppStatus, Long> {

    AppStatus getByStatusId(Long statusId);

    @Query(value = "select new com.mindteck.common.modules.user.model.rest.GetStatusListResponseModel" +
            "(" +
            "app.statusId, " +
            "app.statusNumber, " +
            "app.englishText, " +
            "app.arabText " +
            ") from AppStatus app ORDER BY app.statusNumber ASC",
            countQuery = "SELECT COUNT(*) FROM AppStatus app")
    Page<GetStatusListResponseModel> getStatusList(Pageable pageable);
}

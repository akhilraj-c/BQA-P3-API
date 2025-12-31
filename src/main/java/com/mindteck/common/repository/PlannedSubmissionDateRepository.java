package com.mindteck.common.repository;

import com.mindteck.common.models.PlannedSubmissionDate;
import com.mindteck.common.modules.user.model.rest.GetSubmissionDateListResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedSubmissionDateRepository extends JpaRepository<PlannedSubmissionDate, Long> {

    @Query(
            value = "SELECT new com.mindteck.common.modules.user.model.rest.GetSubmissionDateListResponseModel (" +
                    " psd.submissionDate," +
                    " psd.submissionEndDate,"+
                    " psd.dateId ) FROM PlannedSubmissionDate psd ORDER BY psd.submissionDate ASC",
            countQuery = "SELECT COUNT(*) FROM PlannedSubmissionDate"
    )
    Page<GetSubmissionDateListResponseModel> getSubmissionDateList(Pageable pageable);

    PlannedSubmissionDate getByDateId(Long dateId);
}

package com.mindteck.common.repository;

import com.mindteck.common.models.DueDates;
import com.mindteck.common.modules.user.model.rest.GetDueDateResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DueDatesRepository extends JpaRepository<DueDates, Long> {

    DueDates findByActionAndType(Integer action, Integer type);

    @Query(value = "select new com.mindteck.common.modules.user.model.rest.GetDueDateResponseModel" +
            "(" +
            "due.action, " +
            "due.actionName, " +
            "due.type, " +
            "due.noOfDays " +
            ") from DueDates due",
            countQuery = "SELECT COUNT(*) FROM DueDates due")
    Page<GetDueDateResponseModel> getDueDates(Pageable pageable);
    DueDates findByAction(Integer action);

}

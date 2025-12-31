package com.mindteck.common.repository;

import com.mindteck.common.models.Log;
import com.mindteck.common.models.rest.GetLogResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {


    @Query(value = "select new com.mindteck.common.models.rest.GetLogResponseModel" +
            "(" +
            "log.id," +
            "log.formUniqueId," +
            "log.userId," +
            "log.userType," +
            "log.userSubType," +
            "log.userName," +
            "log.previousMessage," +
            "log.previousStatus," +
            "log.changedMessage," +
            "log.changedStatus," +
            "log.createdTime," +
            "log.lastUpdatedTime" +
            ") from Log log where log.formUniqueId = :formUniqueId" ,
    countQuery = "select count(*) from Log log where log.formUniqueId = :formUniqueId")
    Page<GetLogResponseModel> getLogByFormUniqueId(Long formUniqueId , Pageable pageable);

}

package com.mindteck.repository_cas;

import com.mindteck.models_cas.UserLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserLoginInfoRepository extends JpaRepository<UserLoginInfo, Long> {

    UserLoginInfo getByUserIdAndAppId(Long userId, Long appId);

    UserLoginInfo getByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserLoginInfo u WHERE u.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}

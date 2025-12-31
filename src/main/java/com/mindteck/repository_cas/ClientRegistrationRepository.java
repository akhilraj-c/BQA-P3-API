package com.mindteck.repository_cas;

import com.mindteck.models_cas.ClientRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRegistrationRepository extends JpaRepository<ClientRegistration, Long> {

    @Query("select new java.lang.String(app.deviceToken) "
            + "from UserLoginInfo usr "
            + "join ClientRegistration app on app.appId = usr.appId "
            + "where usr.userId in :userId and usr.expiryTime > :currentTime")
    List<String> getFcmTokens(@Param("userId") List<Long> userId , @Param("currentTime")  Long currentTime);
}

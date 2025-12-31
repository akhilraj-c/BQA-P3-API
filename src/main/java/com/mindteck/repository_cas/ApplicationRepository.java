package com.mindteck.repository_cas;

import com.mindteck.models_cas.ClientRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApplicationRepository extends JpaRepository<ClientRegistration, Long> {

    ClientRegistration getByAppId(Long appId);

    ClientRegistration getByDeviceId(String deviceId);

 /*   @Query("select new java.lang.String(app.deviceToken) "
            + "from UserLoginInfo usr "
            + "join Application app on app.appId = usr.appId "
            + "where usr.userId in :userId and usr.expiryTime > :currentTime")
    List<String> getFcmTokens(@Param("userId") List<Long> userId , @Param("currentTime")  Long currentTime);*/
}

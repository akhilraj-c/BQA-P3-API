package com.mindteck.common.repository;

import com.mindteck.common.models.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    ForgotPassword getByEmailId(String emailId);

    ForgotPassword getByEmailIdAndOtp(String emailId, String otp);
}

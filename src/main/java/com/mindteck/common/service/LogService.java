package com.mindteck.common.service;

import com.mindteck.common.constants.Enum.ApplicationStatus;

public interface LogService {

    void writeLogToDatabase(Long currentUserId , ApplicationStatus currentStatus , ApplicationStatus newStatus, Long formUniqueId);
   // void writeLogToQPDatabase(Long currentUserId , ApplicationStatus currentStatus , ApplicationStatus newStatus, Long formUniqueId);

}

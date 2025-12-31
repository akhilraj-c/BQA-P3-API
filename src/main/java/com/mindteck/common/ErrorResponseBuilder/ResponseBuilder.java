package com.mindteck.common.ErrorResponseBuilder;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.utils.WebUtils;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {

   public ErrorResponse getIndividualResponse(Data data) {
        return buildIndividualResponse(data);
    }

    private ErrorResponse  buildIndividualResponse(Data data) {
       Status status = WebUtils.getStatus();
       status.setStatusCode(20002);
       status.setEndTime(System.currentTimeMillis());
        return ErrorResponse.builder()
                .status(status)
                .data(data)
                .build();
    }
}
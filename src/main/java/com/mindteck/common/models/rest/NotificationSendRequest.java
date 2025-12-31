package com.mindteck.common.models.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSendRequest {

    @NotNull(message = "tokens cannot be null")
    private List<String> tokens;

    @NotNull(message = "title cannot be null")
    private String title;

    @NotNull(message = "message cannot be null")
    private String message;

    private Map<String , String> payload;

    private Integer userType;

    private Boolean expiry = false;
}

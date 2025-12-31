package com.mindteck.common.modules.user.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GetDueDateResponseModel {
    private Integer action;
    private String actionName;
    private Integer type;
    private Integer noOfDays;
   

}

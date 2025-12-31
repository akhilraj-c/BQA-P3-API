package com.mindteck.common.modules.user.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetDueDateRequest {

    @NotNull(message = "setDueDateList is required")
    private List<SetDueDate> setDueDateList;

}

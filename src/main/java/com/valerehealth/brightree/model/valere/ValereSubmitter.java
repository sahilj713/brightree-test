package com.valerehealth.brightree.model.valere;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValereSubmitter {

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

}

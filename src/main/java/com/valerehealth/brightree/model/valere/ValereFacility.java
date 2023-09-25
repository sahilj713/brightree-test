package com.valerehealth.brightree.model.valere;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValereFacility {

    @NotNull
    private String id;

    @NotNull
    private String name;
}

package com.valerehealth.brightree.model.valere;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValereProduct {

    @NotNull
    private String id;

    private String categoryId;

    @NotNull
    private String type;

    private String name;
    private String serialNumber;

    @NotNull
    private String description;

    private Integer quantity;

}

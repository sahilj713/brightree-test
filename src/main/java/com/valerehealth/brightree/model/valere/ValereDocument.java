package com.valerehealth.brightree.model.valere;

import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ValereDocument {

    @NotNull
    private String id;

    @NotNull
    private String type;

    @NotNull
    private String content;

    private Instant createdAt;
    private String createdBy;

}

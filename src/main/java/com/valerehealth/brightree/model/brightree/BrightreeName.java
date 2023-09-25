package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreeName {

    // not_required
    private String title;

    // If the value is not provided the client will receive a 400 Bad Request with a response body
    // detailing the problem.
    // required
    private String first;

    // Only the first initial of middle names are supported.
    // If the value is longer than 1 character it will be truncated and a message will be returned
    // to the client application.
    // not_required
    private String middle;

    // If the value is not provided the client will receive a 400 Bad Request with a response body
    // detailing the problem.
    // required
    private String last;

    // not_required
    private String suffix;

}

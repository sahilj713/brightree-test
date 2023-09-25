package com.valerehealth.brightree.model.valere;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class ValerePayer {

    // primary, secondary, tertiary
    // No enum is added now to easily expand the categories in the future
    private String insuranceId;
    private String payerCategory;
    private String payerName;
    private String policyNumber;
    // <optional>
    private String planName;
    // <optional>
    private String groupNumber;
    private LocalDate insuranceBeginDate;
    private LocalDate insuranceEndDate;

    private ValereAddress payerAddress;
    private String payerPhoneNumber;
    private String relationship;
}

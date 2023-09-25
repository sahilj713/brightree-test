package com.valerehealth.brightree.model.valere;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValerePatient {

    @NotNull
    private String id;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    private String gender;
    private String maritalStatus;
    private String ssn;
    private LocalDate dateOfBirth;
    private LocalDate deathDate;
    private ValereAddress patientAddress;
    private ValereAddress fulfillmentAddress;
    private String email;
    private String cellPhoneNumber;
    private String workPhoneNumber;
    private String homePhoneNumber;
    private String contactPhoneNumber;
    private String contactName;
    private String language;
    private Integer height;
    private Integer weight;

}

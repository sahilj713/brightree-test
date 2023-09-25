package com.valerehealth.brightree.model.valere;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValerePrescriber {

    @NotNull
    private String npi;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    private ValereAddress prescriberAddress;
    private String fax;
    private String phoneNumber;

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

}

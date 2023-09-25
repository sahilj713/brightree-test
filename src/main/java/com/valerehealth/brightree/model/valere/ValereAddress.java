package com.valerehealth.brightree.model.valere;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ValereAddress {
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String zipCode;
        private String country;
}

package com.valerehealth.brightree.model.brightree;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreeInsuranceInfo {

    // not_required
    private List<BrightreePatientPayor> patientPayors;

    public BrightreeInsuranceInfo(List<BrightreePatientPayor> patientPayors) {
        this.patientPayors = patientPayors;
    }

}

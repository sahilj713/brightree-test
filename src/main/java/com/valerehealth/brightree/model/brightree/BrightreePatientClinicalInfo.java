package com.valerehealth.brightree.model.brightree;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.valerehealth.brightree.model.valere.ValereOrder;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreePatientClinicalInfo {

    // not_required
    private Integer heightInches;

    // not_required
    private Integer weightPounds;

    // not_required
    @JsonProperty("Diagnoses")
    private List<BrightreeDiagnosis> diagnoses;

    // not_required
    private BrightreeOrderingDoctor orderingDoctor;

    public static BrightreePatientClinicalInfo fromValereOrder(ValereOrder valereOrder) {
        BrightreePatientClinicalInfo clinicalInfo = new BrightreePatientClinicalInfo();

        if (valereOrder.getPrescriber() != null) {
            clinicalInfo.setOrderingDoctor(
                    BrightreeOrderingDoctor.fromValerePrescriber(valereOrder.getPrescriber()));
        }

        if (valereOrder.getDiagnosisCodes() != null) {
            clinicalInfo.setDiagnoses(
                    BrightreeDiagnosis.fromValereDiagnosisCodes(valereOrder.getDiagnosisCodes()));
        }

        clinicalInfo.setHeightInches(valereOrder.getPatient().getHeight());
        clinicalInfo.setWeightPounds(valereOrder.getPatient().getWeight());

        return clinicalInfo;
    }

}

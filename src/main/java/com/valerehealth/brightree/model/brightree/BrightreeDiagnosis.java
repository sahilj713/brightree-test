package com.valerehealth.brightree.model.brightree;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreeDiagnosis {

    // required
    private String code;

    // Must be "ICD10".
    // required
    private String codingMethod = "ICD10";

    // Must be between 1 and 12.
    // This value determines the order the diagnoses are recorded.
    // Duplicate or unset values will have their value set to fill any gaps in the sequence.
    // required
    private Integer sequence;

    public BrightreeDiagnosis(String code, Integer sequence) {
        this.code = code;
        this.sequence = sequence;
    }

    public static List<BrightreeDiagnosis> fromValereDiagnosisCodes(List<String> diagnosisCodes) {
        if (diagnosisCodes.size() > 12) {
            diagnosisCodes =
                    diagnosisCodes.subList(diagnosisCodes.size() - 12, diagnosisCodes.size());
        }

        int i = 1;
        List<BrightreeDiagnosis> diagnoses = new ArrayList<>();

        for (String diagnosisCode : diagnosisCodes) {
            diagnoses.add(new BrightreeDiagnosis(diagnosisCode, i++));
        }

        return diagnoses;
    }

}

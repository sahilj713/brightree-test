package com.valerehealth.brightree.model.brightree;

import java.time.LocalDate;
import java.time.ZoneOffset;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.valerehealth.brightree.model.valere.ValereDocument;
import com.valerehealth.brightree.util.BrightreeUtil;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(Include.NON_NULL)
public class BrightreeDocument {

    // If the value is not provided the document will be ignored and the client will be notified.
    // required
    private String id;

    // not_required
    private String author;

    // Format YYYYMMDD.
    // not_required
    private String documentDate;

    // not_required
    private String documentType;

    // Base64 encoded string.
    // Only Portable Document Format (PDF) content supported.
    // If the value is not provided the document will be ignored and the client will be notified.
    // required
    private String content;

    public static BrightreeDocument fromValereDocument(ValereDocument valereDocument) {
        if (valereDocument == null) {
            return null;
        }

        BrightreeDocument brightreeDocument = new BrightreeDocument();

        brightreeDocument.setId(valereDocument.getId());
        brightreeDocument.setAuthor(valereDocument.getCreatedBy());

        if (valereDocument.getCreatedAt() != null) {
            brightreeDocument.setDocumentDate(BrightreeUtil.localDateToYYYYMMDD(
                    LocalDate.ofInstant(valereDocument.getCreatedAt(), ZoneOffset.UTC)));
        }

        brightreeDocument.setDocumentType(valereDocument.getType());
        brightreeDocument.setContent(valereDocument.getContent());

        return brightreeDocument;
    }

}

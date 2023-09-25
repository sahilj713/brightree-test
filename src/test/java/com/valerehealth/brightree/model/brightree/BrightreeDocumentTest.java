package com.valerehealth.brightree.model.brightree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import com.valerehealth.brightree.model.valere.ValereDocument;

public class BrightreeDocumentTest {

    @Test
    void testFromValereDocument_null() {
        assertNull(BrightreeDocument.fromValereDocument(null));
    }

    @Test
    void testFromValereDocument_valid() {
        ValereDocument valereDocument = new ValereDocument();

        valereDocument.setId("testId");
        valereDocument.setContent("testContent");
        valereDocument.setCreatedAt(Instant.ofEpochMilli(1660855946905L));
        valereDocument.setCreatedBy("test user");
        valereDocument.setType("some type");

        BrightreeDocument document = BrightreeDocument.fromValereDocument(valereDocument);

        assertEquals(document.getId(), "testId");
        assertEquals(document.getContent(), "testContent");
        assertEquals(document.getAuthor(), "test user");
        assertEquals(document.getDocumentDate(), "20220818");
        assertEquals(document.getDocumentType(), "some type");
    }

}

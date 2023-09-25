package com.valerehealth.brightree.model.brightree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import com.valerehealth.brightree.model.valere.ValereAddress;
import com.valerehealth.brightree.model.valere.ValerePrescriber;

public class BrightreeOrderingDoctorTest {

    @Test
    void testFromValerePrescriber_null() {
        assertNull(BrightreeOrderingDoctor.fromValerePrescriber(null));
    }

    @Test
    void testFromValerePrescriber() {
        ValerePrescriber valerePrescriber = new ValerePrescriber();
        valerePrescriber.setNpi("1112223334");

        valerePrescriber.setFirstName("testFirst");
        valerePrescriber.setMiddleName("testMiddle");
        valerePrescriber.setLastName("testLast");

        ValereAddress address = new ValereAddress();

        address.setAddressLine1("test address1");
        address.setAddressLine2("test address2");
        address.setCity("testCity");
        address.setState("IN");
        address.setZipCode("12345");
        address.setCountry("USA");

        valerePrescriber.setPrescriberAddress(address);
        valerePrescriber.setFax("1234567890");
        valerePrescriber.setPhoneNumber("2345678901");

        BrightreeOrderingDoctor doctor =
                BrightreeOrderingDoctor.fromValerePrescriber(valerePrescriber);

        assertEquals(doctor.getNpi(), "1112223334");
        assertEquals(doctor.getFaxNumber(), "1234567890");
        assertEquals(doctor.getPhone(), "2345678901");

        assertEquals(doctor.getAddress().getAddressLine1(), "test address1");
        assertEquals(doctor.getAddress().getAddressLine2(), "test address2");
        assertEquals(doctor.getAddress().getCity(), "testCity");
        assertEquals(doctor.getAddress().getState(), "IN");
        assertEquals(doctor.getAddress().getCountry(), "USA");
        assertEquals(doctor.getAddress().getZipCode(), "12345");

        assertEquals(doctor.getName().getFirst(), "testFirst");
        assertEquals(doctor.getName().getMiddle(), "testMiddle");
        assertEquals(doctor.getName().getLast(), "testLast");
    }

}

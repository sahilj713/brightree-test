package com.valerehealth.brightree.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

public class BrightreeUtilTest {

    @Test
    void testLocalDateToYYYYMMDD() {
        assertEquals(BrightreeUtil.localDateToYYYYMMDD(LocalDate.parse("2022-12-12")), "20221212");
    }

    @Test
    void testLocalTimeToHHMM_null() {
        assertNull(BrightreeUtil.localTimeToHHMM(null));
    }

    @Test
    void testLocalTimeToHHMM_invalid() {

        assertThrowsExactly(DateTimeParseException.class,
                () -> BrightreeUtil.localTimeToHHMM(LocalTime.parse("13:24.57")),
                "Expected localTimeToHHMM() to throw an execption, but it didn't!");
    }

    @Test
    void testLocalTimeToHHMM_valid() {
        assertEquals(BrightreeUtil.localTimeToHHMM(LocalTime.parse("13:24:57")), "13:24");
    }

}

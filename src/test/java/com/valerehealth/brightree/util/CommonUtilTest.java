package com.valerehealth.brightree.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class CommonUtilTest {

    @Test
    void testIsEmpty_null() {
        assertEquals(CommonUtil.isEmpty(null), true);
    }

    @Test
    void testIsEmpty_empty() {
        assertEquals(CommonUtil.isEmpty(new ArrayList<>()), true);
    }

    @Test
    void testIsEmpty_withItems() {
        assertEquals(CommonUtil.isEmpty(Arrays.asList("test", "array")), false);
    }

    @Test
    void testHasValue_null() {
        assertEquals(CommonUtil.hasValue(null), false);
    }

    @Test
    void testHasValue_empty() {
        assertEquals(CommonUtil.hasValue(""), false);
    }

    @Test
    void testHasValue_withSpace() {
        assertEquals(CommonUtil.hasValue("   "), false);
    }

    @Test
    void testHasValue_validString() {
        assertEquals(CommonUtil.hasValue("test str"), true);
    }

}

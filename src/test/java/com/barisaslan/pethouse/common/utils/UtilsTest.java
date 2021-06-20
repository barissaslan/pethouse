package com.barisaslan.pethouse.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

    @Test
    void isValidEmailAddressShouldReturnTrue() {
        assertTrue(Utils.isValidEmailAddress("test@baris.com"));
        assertTrue(Utils.isValidEmailAddress("test@gmail.com"));
        assertTrue(Utils.isValidEmailAddress("test_3@baris.com"));
        assertTrue(Utils.isValidEmailAddress("test.4@baris.com"));
        assertTrue(Utils.isValidEmailAddress("test.test2@baris.com"));
        assertTrue(Utils.isValidEmailAddress("test-5@baris.com"));
    }

    @Test
    void isValidEmailAddressShouldReturnFalse() {
        assertFalse(Utils.isValidEmailAddress("test@.baris.com"));
        assertFalse(Utils.isValidEmailAddress("test@baris.com."));
        assertFalse(Utils.isValidEmailAddress("test@%baris.com"));
        assertFalse(Utils.isValidEmailAddress("test@*baris.com"));
        assertFalse(Utils.isValidEmailAddress("test@baris.1a"));
    }

}

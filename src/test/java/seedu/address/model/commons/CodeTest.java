package seedu.address.model.commons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CodeTest {

    @Test
    void isValidCode() {
        assertTrue(Code.isValidCode("CS1231S"));
        assertTrue(Code.isValidCode("MA1101R"));
        assertTrue(Code.isValidCode("MA1102"));
        assertTrue(Code.isValidCode("CFG1101"));
        assertFalse(Code.isValidCode("A3BBBB"));
    }
}

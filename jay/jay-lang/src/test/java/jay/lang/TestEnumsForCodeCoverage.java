package jay.lang;

import org.junit.Test;

import corvid.unit.EnumTestHelper;

/**
 * This test class is just for code coverage.
 */
public class TestEnumsForCodeCoverage {
    @Test
    public void test() {
        EnumTestHelper.testAllMethods(FaultCode.class, Severity.class);
    }
}

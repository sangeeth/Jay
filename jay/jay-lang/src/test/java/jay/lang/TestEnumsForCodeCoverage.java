package jay.lang;

import org.junit.Test;

import corvid.unit.EnumTestHelper;

/**
 * This test class is just for code coverage.
 * 
 * @author sangkuma
 *
 */
public class TestEnumsForCodeCoverage {
    @Test
    public void test() {
        EnumTestHelper.testAllMethods(FaultCode.class, Severity.class);
    }
}

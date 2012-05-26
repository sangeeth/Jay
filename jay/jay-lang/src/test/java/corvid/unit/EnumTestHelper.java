package corvid.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EnumTestHelper {
    public static void testAllMethods(Class<? extends Enum>...types) {
        for(Class<? extends Enum> type:types) {
            test_values(type);
            test_valueOf(type);
        }
    }
    private static void test_values(Class<? extends Enum> type) {
        try {
            Method method = type.getMethod("values");
            Object values = method.invoke(null, new Object[]{});
            assertNotNull(values);
            assertTrue(values.getClass().isArray());
        }catch(Exception e) {
            fail(e.getMessage());
        }
    }
    private static void test_valueOf(Class<? extends Enum> type) {
        try {
            // Find the first enum object from the type.
            Enum e = null;
            Field [] fields = type.getFields();
            for(Field field:fields) {
                if (field.isEnumConstant()) {
                    e = (Enum)field.get(null);
                }
            }
            Method method = type.getMethod("valueOf",String.class);
            Object value = method.invoke(null, new Object[]{e.name()});
            assertNotNull(value);
            assertEquals(value,e);
        }catch(Exception e) {
            fail(e.getMessage());
        }
    }
}
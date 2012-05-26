package jay.lang;

import org.junit.Assert;
import org.junit.Test;


public class TestAbstractEnum {
    static class MyEnum extends AbstractEnum {
        private static final long serialVersionUID = 1L;

        public static final MyEnum ENTRY_1 = new MyEnum("entry1");
        public static final MyEnum ENTRY_2 = new MyEnum("entry2");

        private static int count = 0;
        protected MyEnum(String name) {
            super(name, count++);
        }
    }
    
    @Test
    public void test_ordinal() {
       Assert.assertEquals(1, MyEnum.ENTRY_2.ordinal()); 
    }
    
    @Test
    public void test_name() {
       Assert.assertEquals("entry2", MyEnum.ENTRY_2.name()); 
    }    
}

package jay.lang;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import corvid.unit.TestHelper;

@Ignore("Need to fix test data")
public class TestResource {
    private static File testCaseWorkDir;
    
    @BeforeClass
    public static void loadTestCaseData() {
        testCaseWorkDir = TestHelper.loadTestCaseData(TestResourceManager.class);
    }
    @AfterClass
    public static void unloadTestCaseData() {
        TestHelper.cleanTestCaseWorkDir(testCaseWorkDir);
    }
    
    @Before
    public void setUp() {
        ResourceManager.getInstance().clearCache();
    }
    
    @Test
    public void test_resource_resourceBundleSource_locale_classloader() {
        String resourceBundleSource = "test.resource.StringResourceBundle";
        Locale locale = Locale.FRENCH;
        
        File bundle1 = new File(testCaseWorkDir, "test_bundle1.jar");
        
        Assert.assertTrue("test_bundle1.jar doesn't exist",bundle1.exists());
        
        URLClassLoader classLoader = null;
        
        try {
            classLoader = new URLClassLoader(new URL[]{bundle1.toURI().toURL()});
        } catch(MalformedURLException e) {
            Assert.fail(e.getLocalizedMessage());
        }
        
        Resource resource = ResourceManager.getInstance().getResource(resourceBundleSource, locale, classLoader);
        
        Assert.assertNotNull(resource);
        
        Assert.assertEquals(classLoader, resource.getBundleLoader());
        
        Assert.assertEquals(locale, resource.getLocale());
        
        String defaultValue = "defaultValue for key3";
        String value = resource.getString("key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals("Bundle1: FRENCH: The arguments passed are param1 and param2", value);        
    }
 
    @Test
    public void test_resource_resourceBundleSource_locale() {
        String resourceBundleSource = "corvid.lang.resource.StringResourceBundle";
        Locale locale = Locale.FRENCH;
        
        Resource resource = Resource.valueOf(resourceBundleSource, locale);
        
        Assert.assertNotNull(resource);
        
        String defaultValue = "defaultValue for key3";
        String value = resource.getString("key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals("FRENCH: The arguments passed are param1 and param2", value);
        
        value = resource.getString(Locale.ENGLISH, "key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals("The arguments passed are param1 and param2", value);
    }
    
    @Test
    public void test_resource_resourceBundleSource() {
        String resourceBundleSource = "corvid.lang.resource.StringResourceBundle";
        
        Resource resource = Resource.valueOf(resourceBundleSource);
        
        Assert.assertNotNull(resource);
        
        String defaultValue = "defaultValue for key3";
        String value = resource.getString("key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals("The arguments passed are param1 and param2", value);        
    }
    
    @Test
    public void test_resource_null_resourceBundleSource() {
        String resourceBundleSource = null;
        
        Resource resource = Resource.valueOf(resourceBundleSource);
        
        Assert.assertNotNull(resource);
        
        String defaultValue = "defaultValue for key3";
        String value = resource.getString("key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals(defaultValue, value);        
    }
    
    @Test
    public void test_resource_getString_unmapped_key() {
        String resourceBundleSource = null;
        
        Resource resource = Resource.valueOf(resourceBundleSource);
        
        Assert.assertNotNull(resource);
        
        String key = null;
        String defaultValue = "defaultValue for key3";
        String value = resource.getString(key, defaultValue, "param1", "param2");
        
        Assert.assertEquals(defaultValue, value);        
    }    
    
    @Test
    public void test_resource_getNumber() {
        String resourceBundleSource = "corvid.lang.resource.StringResourceBundle";
        
        Resource resource = Resource.valueOf(resourceBundleSource);
        
        Assert.assertNotNull(resource);
        
        String key = "key2";
        int defaultValue = 20;
        int value = resource.getNumber(Integer.class, key, defaultValue);
        
        Assert.assertEquals(10, value);        
    }     
    
    @Test
    public void test_resource_getNumber_unmapped_key() {
        String resourceBundleSource = "corvid.lang.resource.StringResourceBundle";
        
        Resource resource = Resource.valueOf(resourceBundleSource);
        
        Assert.assertNotNull(resource);
        
        String key = "unmapped_key";
        int defaultValue = 20;
        int value = resource.getNumber(Integer.class, key, defaultValue);
        
        Assert.assertEquals(defaultValue, value);        
    }     
    
    @Test
    public void test_resource_getNumber_CustomNumber_unmapped_key() {
        String resourceBundleSource = "corvid.lang.resource.StringResourceBundle";
        
        Resource resource = Resource.valueOf(resourceBundleSource);
        
        Assert.assertNotNull(resource);
        
        String key = "key2";
        VoidNumber defaultValue = new VoidNumber();
        VoidNumber value = resource.getNumber(VoidNumber.class, key, defaultValue);
        
        Assert.assertEquals(defaultValue, value);        
    }    
    
    @Test
    public void test_getResourceBundle() {
        String resourceBundleSource = "corvid.lang.resource.StringResourceBundle";
        Locale locale = Locale.FRENCH;
        
        Resource resource = Resource.valueOf(resourceBundleSource, locale);
        
        Assert.assertNotNull(resource);
        
        String defaultValue = "defaultValue for key3";
        String value = resource.getString("key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals("FRENCH: The arguments passed are param1 and param2", value);
        
        ResourceBundle resourceBundle = resource.getResourceBundle();
        Assert.assertNotNull(resourceBundle);
        
        value = resourceBundle.getString("key3");
        Assert.assertEquals("FRENCH: The arguments passed are {0} and {1}", value);
    }
    
    static class VoidNumber extends Number {
        private static final long serialVersionUID = 1L;

        @Override
        public double doubleValue() {
            return 0;
        }

        @Override
        public float floatValue() {
            return 0;
        }

        @Override
        public int intValue() {
            return 0;
        }

        @Override
        public long longValue() {
            return 0;
        }
    }
}

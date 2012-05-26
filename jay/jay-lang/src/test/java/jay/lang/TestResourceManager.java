package jay.lang;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import corvid.unit.TestHelper;

@Ignore("Need to fix test data")
public class TestResourceManager {
    private static File testCaseWorkDir;
    
    @BeforeClass
    public static void loadTestCaseData() {
        testCaseWorkDir = TestHelper.loadTestCaseData(TestResourceManager.class);
    }
    @AfterClass
    public static void unloadTestCaseData() {
        TestHelper.cleanTestCaseWorkDir(testCaseWorkDir);
    }
    
    @Test
    public void test_resource_nullResourceBundleSource() {
        Resource resource = Resource.valueOf(null);
        Assert.assertNotNull(resource);
        
        String expectedValue = "defaultValue for key1";
        String value = resource.getString("key1", expectedValue);
        
        Assert.assertEquals(expectedValue, value);
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
    public void test_resource_resourceBundleSource_locale() {
        String resourceBundleSource = "corvid.lang.resource.StringResourceBundle";
        Locale locale = Locale.FRENCH;
        
        Resource resource = Resource.valueOf(resourceBundleSource, locale);
        
        Assert.assertNotNull(resource);
        
        String defaultValue = "defaultValue for key3";
        String value = resource.getString("key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals("FRENCH: The arguments passed are param1 and param2", value);        
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
        
        String defaultValue = "defaultValue for key3";
        String value = resource.getString("key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals("Bundle1: FRENCH: The arguments passed are param1 and param2", value);        
    }
    
    @Test
    public void test_resource_resourceBundleSource_locale_multi_classloader() {
        String resourceBundleSource = "test.resource.StringResourceBundle";
        Locale locale = Locale.FRENCH;
        
        File bundle1 = new File(testCaseWorkDir, "test_bundle1.jar");
        File bundle2 = new File(testCaseWorkDir, "test_bundle2.jar");
        
        Assert.assertTrue("test_bundle1.jar doesn't exist",bundle1.exists());
        Assert.assertTrue("test_bundle2.jar doesn't exist",bundle2.exists());
        
        URLClassLoader classLoader = null;
        
        try {
            classLoader = new URLClassLoader(new URL[]{bundle1.toURI().toURL()});
        } catch(MalformedURLException e) {
            Assert.fail(e.getLocalizedMessage());
        }
        
        Resource resource = ResourceManager.getInstance().getResource(resourceBundleSource, locale, classLoader);
        
        Assert.assertNotNull(resource);
        
        String defaultValue = "defaultValue for key3";
        String value = resource.getString("key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals("Bundle1: FRENCH: The arguments passed are param1 and param2", value);    
        
        try {
            classLoader = new URLClassLoader(new URL[]{bundle2.toURI().toURL()});
        } catch(MalformedURLException e) {
            Assert.fail(e.getLocalizedMessage());
        }
        
        resource = ResourceManager.getInstance().getResource(resourceBundleSource, null, classLoader);
        
        Assert.assertNotNull(resource);
        
        defaultValue = "defaultValue for key3";
        value = resource.getString("key3", defaultValue, "param1", "param2");
        
        Assert.assertEquals("Bundle2: The arguments passed are param1 and param2", value);         
    }     
    
    @Test
    public void test_getResource_unknown_bundle() {
       ResourceManager resourceManager = ResourceManager.getInstance();
       String resourceBundleSource = "some.unknown.bundle";
       Locale locale = Locale.FRENCH;
       
       Resource resource = resourceManager.getResource(resourceBundleSource, locale, null);
       String value = resource.getString(Locale.GERMAN, "some.key", "some value");
    }
}

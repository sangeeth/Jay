package corvid.unit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Assert;

public class TestHelper {
    private static final String DEFAULT_DATA_LOCATION = "test/data";
    private static final String DEFAULT_WORK_LOCATION = System.getProperty("java.io.tmpdir") + "/cisco-junit";
    
    public static File getTestDataDir() {
        String dataDirValue = System.getProperty("test.data.dir", DEFAULT_DATA_LOCATION);
        File dataDir = new File(dataDirValue);
        return dataDir;
    }
    
    public static File getTestWorkDir() {
        String workDirValue = System.getProperty("test.work.dir", DEFAULT_WORK_LOCATION);
        File workDir = new File(workDirValue);
        return workDir;
    }
    
    public static File loadTestCaseData(Class<?> testCaseType) {
        String testCaseFQN = testCaseType.getName();
        String testCaseId = testCaseFQN.replace(".", "_");
        
        // Find the test data file for the given test case
        
        File testDataFile = new File(getTestDataDir(), String.format("%s.zip",testCaseId));
        
        // Find the work directory for the given test case
        File testCaseWorkDir = new File(getTestWorkDir(), testCaseId);
        
        testCaseWorkDir.mkdirs();
        
        try {
            unpack(testDataFile, testCaseWorkDir);
        } catch(IOException e) {
            Assert.fail(String.format("Failed to load test data for %s.\nReason: %s\n", testCaseFQN, e.getCause()));
        }
        
        return testCaseWorkDir;
    }
    
    public static void cleanTestCaseWorkDir(File testCaseWorkDir) {
        delete(testCaseWorkDir);
    }
    
    private static void delete(File path)  {
        if (path.exists()) {
            if (path.isDirectory()) {
                File [] children = path.listFiles();
                for (File file:children)    {
                    delete(file);
                }
            } 
            path.delete();
        }
    }    
       
    private static void unpack(File dataFile, File destDir) throws IOException {
        ZipFile zipFile = new ZipFile(dataFile);

        Enumeration<? extends ZipEntry> e = zipFile.entries();
        while(e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            InputStream zin = zipFile.getInputStream(entry);
            String entryName = entry.getName();
            File file = new File(destDir,entryName);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()){
                parentDir.mkdirs();
            }
            if (!entryName.endsWith("/")) {
                BufferedInputStream bin = new BufferedInputStream(zin);
                BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buffer = new byte[512];
                int len = -1;
                while((len=bin.read(buffer))!=-1) {
                    bout.write(buffer, 0, len);
                    bout.flush();
                }
                bout.close();
                bin.close();
            }
        }
    }
}

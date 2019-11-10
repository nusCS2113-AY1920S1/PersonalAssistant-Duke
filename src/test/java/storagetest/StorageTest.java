package storagetest;

import Storage.Storage;
import common.AlphaNUSException;
import org.junit.jupiter.api.Test;
import project.Project;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {

    private Storage storage = new Storage();

    @Test
    void test_WriteAndReadCurrentProjectName() throws AlphaNUSException {
        try {
            String expectedcurrentprojectname = "Rag";
            storage.writeTocurrentprojectnameFile("Rag");
            String outputcurrentprojectname = storage.readFromcurrentprojectnameFile();

            assertEquals(expectedcurrentprojectname, outputcurrentprojectname);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void test_WriteAndReadProjectsFile() throws AlphaNUSException {
        try {
            LinkedHashMap<String, Project> expectedprojectmap = new LinkedHashMap<>();
            expectedprojectmap.put("Arts Night", new Project("Arts Night"));
            expectedprojectmap.put("Rag", new Project("Rag", 100.0));

            LinkedHashMap<String, Project> outputprojectmap = expectedprojectmap;
            String outputcurrentprojectname = storage.readFromcurrentprojectnameFile();

            assertEquals(expectedprojectmap, outputprojectmap);
        } catch (Exception e) {
            fail();
        }
    }
}

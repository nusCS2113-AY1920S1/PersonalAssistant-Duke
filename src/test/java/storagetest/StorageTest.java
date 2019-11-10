package storagetest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.Fund;
import project.Project;
import storage.Storage;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//@@author leowyh
public class StorageTest {

    private Storage storage = new Storage();

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void test_WriteAndReadCurrentProjectName() {
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
    void test_writeAndReadProjectsFile_sucess() {
        try {
            LinkedHashMap<String, Project> expectedprojectmap = new LinkedHashMap<>();
            expectedprojectmap.put("Arts Night", new Project("Arts Night"));
            expectedprojectmap.put("Rag", new Project("Rag", 100.0));

            storage.writeToProjectsFile(expectedprojectmap);

            LinkedHashMap<String, Project> outputprojectmap = storage.readFromProjectsFile();
            assertEquals(expectedprojectmap.size(), outputprojectmap.size());
            for (int i = 0; i < expectedprojectmap.size(); i++) {
                Project expectedproject = (Project) expectedprojectmap.values().toArray()[i];
                Project outputproject = (Project) outputprojectmap.values().toArray()[i];
                assertEquals(expectedproject.giveProject(), outputproject.giveProject());
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void test_WriteAndReadFundFile() {
        try {
            Fund expectedfund = new Fund();
            expectedfund.loadFund(1000, 100,900);

            storage.writeToFundFile(expectedfund);

            Fund outputfund = storage.readFromFundFile();
            assertEquals(expectedfund.giveFund(), outputfund.giveFund());

        } catch (Exception e) {
            fail();
        }
    }
}

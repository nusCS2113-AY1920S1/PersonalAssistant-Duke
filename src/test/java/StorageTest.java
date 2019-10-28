import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javacake.tasks.Task;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    File profileFile;
    File storageFile;

    /**
     * Initialise test files.
     */
    @BeforeEach
    public void init() {
        profileFile = new File("testProfile");
        storageFile = new File("testStorage");
    }

    /**
     * Deletes test files.
     */
    @AfterEach
    public void delete() {
        try {
            FileUtils.deleteDirectory(profileFile);
            FileUtils.deleteDirectory(storageFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void newUserUsername() {
        Profile profile;
        try {
            profile = new Profile(profileFile.getPath());
            assertEquals("NEW_USER_!@#", profile.getUsername());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void newUserScore() {
        Profile profile;
        try {
            profile = new Profile(profileFile.getPath());
            assertEquals(0, profile.getContentMarks(0));
            assertEquals(0, profile.getContentMarks(1));
            assertEquals(0, profile.getContentMarks(2));
            assertEquals(0, profile.getContentMarks(3));
            assertEquals(0, profile.getTotalProgress());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void newStorage() {
        Storage storage;
        try {
            storage = new Storage(storageFile.getPath());
            assertEquals(new ArrayList<Task>(), storage.getData());
            assertEquals(0, Storage.getInternalDataSize());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
}

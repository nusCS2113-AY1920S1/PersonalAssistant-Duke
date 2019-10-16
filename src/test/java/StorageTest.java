import org.junit.jupiter.api.Test;
import spinbox.Storage;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.StorageException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {

    @Test
    public void createDataAndReadData_varietyOfStrings_successfulCreatesAndReads() throws StorageException {
        File file = new File("SpinBox/storageTest.txt");
        file.delete();
        ArrayList<String> testStrings = new ArrayList<>();

        testStrings.add("Houston we have a problem");
        testStrings.add("Houston we have a merge conflict");
        testStrings.add("Houston we have a pull request");
        testStrings.add("Houston we have a user story");
        testStrings.add("Houston pls");

        Storage test = new Storage("SpinBoxData/storageTest.txt");
        test.saveData(testStrings);
        List<String> loadedStrings = test.loadData();

        for (int i = 0; i < loadedStrings.size(); i++) {
            assertEquals(loadedStrings.get(i), testStrings.get(i));
        }
    }
}
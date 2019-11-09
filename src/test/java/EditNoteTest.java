import javacake.JavaCake;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class EditNoteTest {

    private static JavaCake javaCake;

    @BeforeAll
    static void init() {
        try {
            FileUtils.deleteDirectory(new File("testPath"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        javaCake = new JavaCake("testPath");
    }

    /**
     * Deletes test files.
     */
    @AfterAll
    static void delete() {
        try {
            FileUtils.deleteDirectory(new File("testPath"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



}

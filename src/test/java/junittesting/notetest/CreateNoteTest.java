package junittesting.notetest;

import javacake.JavaCake;
import org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class CreateNoteTest {

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

    @Test
    void illegalCharacterTestInCreateNoteCommand() {
        String actualOutput;
        String expectedOutput;

        actualOutput = javaCake.getResponse("createnote ../hi");
        expectedOutput = "Invalid file name: Illegal character in file name detected!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote hi,hello");
        expectedOutput = "Invalid file name: Illegal character in file name detected!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote >__<");
        expectedOutput = "Invalid file name: Illegal character in file name detected!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote txt.txt");
        expectedOutput = "Invalid file name: Illegal character in file name detected!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote txt/");
        expectedOutput = "Invalid file name: Illegal character in file name detected!";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void validTestCasesInCreateNoteCommand() {
        String actualOutput;
        String expectedOutput;

        actualOutput = javaCake.getResponse("createnote");
        expectedOutput = "File [Notes] has been created successfully!\n";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote ");
        expectedOutput = "File [Notes1] has been created successfully!\n";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote @notes");
        expectedOutput = "File [@notes] has been created successfully!\n";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote ____");
        expectedOutput = "File [____] has been created successfully!\n";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote editnote");
        expectedOutput = "File [editnote] has been created successfully!\n";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote 2113rocks!");
        expectedOutput = "File [2113rocks!] has been created successfully!\n";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void fileAlreadyExistNotificationInCreateNoteCommand() {
        String actualOutput;
        String expectedOutput;
        actualOutput = javaCake.getResponse("createnote Notes");
        expectedOutput = "File already exists, please type 'editnote Notes' to edit the file instead";
        assertEquals(expectedOutput, actualOutput);
    }

}

package junittesting.notetest;

import javacake.JavaCake;
import org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class NoteTest {

    private JavaCake javaCake;

    @BeforeEach
    void init() {
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
    @AfterEach
    void delete() {
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
        expectedOutput = "Special Character(s) detected! Please use another file name!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote hi,hello");
        expectedOutput = "Special Character(s) detected! Please use another file name!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote >__<");
        expectedOutput = "Special Character(s) detected! Please use another file name!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote txt.txt");
        expectedOutput = "Special Character(s) detected! Please use another file name!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote txt/");
        expectedOutput = "Special Character(s) detected! Please use another file name!";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void validTestCasesInCreateNoteCommand() {
        String actualOutput;
        String expectedOutput;

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
        expectedOutput = "File [Notes] has been created successfully!\n";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote Notes");
        expectedOutput = "File already exists, please type 'editnote Notes' to edit the file instead";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void errorMessageInViewNoteTest() {

        String actualOutput;
        String expectedOutput;

        actualOutput = javaCake.getResponse("viewnote    ");
        expectedOutput = "Please input the name of the file you wish to view!"
                + "E.g. viewnote [name of file]";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("viewnote createnote deletenote");
        expectedOutput = "Please only enter one file name! E.g. viewnote [name of file]";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("viewnote .___.");
        expectedOutput = "Invalid file name: Special character(s) in file name detected!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("viewnote dummyfile");
        expectedOutput = "File specified does not exist! "
                + "Please refer to the notes window to view existing notes file!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("viewnote 2113rocks!");
        expectedOutput = "File specified does not exist! Please refer to the notes window to view existing notes file!";
        assertEquals(expectedOutput, actualOutput);
    }

}

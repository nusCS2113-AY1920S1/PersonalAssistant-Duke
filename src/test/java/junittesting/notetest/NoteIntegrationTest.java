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

public class NoteIntegrationTest {

    private static JavaCake javaCake;

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
    void crudNoteTest() {
        String actualOutput;
        String expectedOutput;

        actualOutput = javaCake.getResponse("createnote");
        expectedOutput = "File [Notes] has been created successfully!\n";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote Notes");
        expectedOutput = "File already exists, please type 'editnote Notes' to edit the file instead";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote Notes1");
        expectedOutput = "File [Notes1] has been created successfully!\n";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("createnote Notes1");
        expectedOutput = "File already exists, please type 'editnote Notes1' to edit the file instead";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("deletenote   ");
        expectedOutput = "Please indicate the file name you wish to delete";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("deletenote Notes1 Notes2 ");
        expectedOutput = "Please only enter one file name! E.g. deletenote [name of file]";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("deletenote >___<");
        expectedOutput = "Invalid file name: Special character in file name detected!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("deletenote Notes2");
        expectedOutput = "Invalid file name: No such file!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("deletenote Notes1");
        expectedOutput = "File [Notes1] has been deleted successfully!\n";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("viewnote ");
        expectedOutput = "Please input the name of the file you wish to view!"
                + "E.g. viewnote [name of file]";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("viewnote deletenote createnote");
        expectedOutput = "Please only enter one file name! E.g. viewnote [name of file]";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("viewnote Notes1");
        expectedOutput = "File specified does not exist! "
                + "Please refer to the notes window to view existing notes file!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("viewnote Notes    ");
        expectedOutput = "File specified is empty! "
                + "Use 'editnote [name of file]' to write new content!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("editnote Notes1 ");
        expectedOutput = "Pls enter a valid file name! Type 'listnote' to view available notes!";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("editnote viewnote createnote ");
        expectedOutput = "Pls enter a valid editnote command:"
                + " 'editnote - [name of the file you wish you edit]'";
        assertEquals(expectedOutput, actualOutput);

        actualOutput = javaCake.getResponse("deletenote Notes");
        expectedOutput = "File [Notes] has been deleted successfully!\n";
        assertEquals(expectedOutput, actualOutput);

    }
}
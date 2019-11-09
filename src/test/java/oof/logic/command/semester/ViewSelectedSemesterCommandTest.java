package oof.logic.command.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import oof.Oof;
import oof.commons.exceptions.ParserException;
import oof.commons.exceptions.command.CommandException;
import oof.model.university.SelectedInstance;
import oof.model.university.Semester;

public class ViewSelectedSemesterCommandTest {

    /**
     * Tests the behaviour when semester is not selected.
     */
    @Test
    public void execute_SemesterNotSelected_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            SelectedInstance selectedInstance = SelectedInstance.getInstance();
            selectedInstance.resetSemester();
            new Oof().executeCommand("semester");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! No semester selected.", e.getMessage());
        }
    }

    /**
     * Tests the behaviour when semester is selected.
     */
    @Test
    public void execute_SemesterSelected_ThrowsException() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        new Oof().executeCommand("semester /select 1");
        ByteArrayOutputStream actualMessagePrinted = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualMessagePrinted));
        new Oof().executeCommand("semester");
        actualMessagePrinted.close();
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        String expectedMessage = "________________________________________________________________________________"
                + System.lineSeparator() + " Currently Selected: " + semester.toString();
        assertEquals(expectedMessage, actualMessagePrinted.toString().trim());
    }
}

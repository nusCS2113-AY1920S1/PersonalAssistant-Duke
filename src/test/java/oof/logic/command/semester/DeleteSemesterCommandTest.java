package oof.logic.command.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import oof.Oof;
import oof.commons.exceptions.ParserException;
import oof.commons.exceptions.command.CommandException;
import oof.model.university.Semester;
import oof.model.university.SemesterList;

public class DeleteSemesterCommandTest {

    @Test
    public void execute_InvalidIndex_ExceptionThrown() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /delete -1");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! The index is invalid.", e.getMessage());
        }
    }

    @Test
    public void execute_CorrectCommandEntered_DeleteSemester() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        Oof oof = new Oof();
        SemesterList semesterList = oof.getSemesterList();
        Semester originalLastSemester = semesterList.getSemester(semesterList.getSize() - 1);
        oof.executeCommand("semester /add 19/20 /name Semester 2 /from 05-01-2020 /to 05-05-2020");
        oof.executeCommand("semester /delete " + (semesterList.getSize()));
        Semester newLastSemester = semesterList.getSemester(semesterList.getSize() - 1);
        assertEquals(originalLastSemester, newLastSemester);
    }
}

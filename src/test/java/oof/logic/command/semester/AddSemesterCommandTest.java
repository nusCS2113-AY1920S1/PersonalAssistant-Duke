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

//@@author KahLokKee

public class AddSemesterCommandTest {

    @Test
    public void execute_MissingYearArgument_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add  /name Semester 2 /from 05-01-2020 /to 05-05-2020");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The semester needs a year.", e.getMessage());
        }
    }

    @Test
    public void execute_MissingNameArgument_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add 19/20 /name /from 05-01-2020 /to 05-05-2020");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The semester needs a name.", e.getMessage());
        }
    }

    @Test
    public void execute_MissingStartDateArgument_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add 19/20 /name Semester 2 /from /to 05-05-2020");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The semester needs a start date.", e.getMessage());
        }
    }

    @Test
    public void execute_MissingEndDateArgument_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add 19/20 /name Semester 2 /from 05-01-2020 /to ");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The semester needs an end date.", e.getMessage());
        }
    }

    @Test
    public void execute_StartDateAfterEndDate_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add 19/20 /name Semester 2 /from 05-05-2020 /to 05-01-2020");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The start date of a semester cannot be after the end date.", e.getMessage());
        }
    }

    @Test
    public void execute_InvalidStartDate_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add 19/20 /name Semester 2 /from a /to 05-01-2020");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The date is invalid.", e.getMessage());
        }
    }

    @Test
    public void execute_InvalidEndDate_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add 19/20 /name Semester 2 /from 05-01-2020 /to a");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The date is invalid.", e.getMessage());
        }
    }

    @Test
    public void execute_ClashWithOtherSemesters_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add 19/20 /name Semester 2 /from 05-08-2019 /to 05-12-2019");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The semester clashes with another semester.", e.getMessage());
        }
    }

    @Test
    public void execute_DescriptionExceedsMaxLength_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add 19/20 /name xiwangniyihoubuhuihouweimeixuanzewo"
                    + "yexiangxingniyougenhaodeshenghuowohuizaixinlimomodeweinierzhizhuobijingwomenyecengshenaiguo"
                    + " /from 05-01-2020 /to 05-05-2020");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!!! Semester Name exceeds maximum length of 100!", e.getMessage());
        }
    }

    @Test
    public void execute_ClashWithExistingSemester_ThrowsException() {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            new Oof().executeCommand("semester /add 19/20 /name Semester 2 /from 05-01-2020 /to a");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS!! The date is invalid.", e.getMessage());
        }
    }

    @Test
    public void execute_CorrectCommandEntered_AddSemester() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        new Oof().executeCommand("semester /add 19/20 /name Semester 2 /from 05-01-2020 /to 05-05-2020");
        SemesterList semesterList = new Oof().getSemesterList();
        Semester semester = semesterList.getSemester(semesterList.getSize() - 1);
        assertEquals("Academic Year 19/20, Semester 2 (05-01-2020-05-05-2020)", semester.toString());
        new Oof().executeCommand("semester /delete " + (semesterList.getSize()));
    }
}

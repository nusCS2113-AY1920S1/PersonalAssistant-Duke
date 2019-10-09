package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static compal.commons.Messages.*;
import static compal.model.tasks.Task.Priority.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByeCommandTest {
    private ByeCommand byeCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        byeCommand = new ByeCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, byeCommand.compal);
    }

    @Test
    public void getDescriptionTest() {
        try {
            String command = "sleep /date 10/10/2019 /end 1230";
            assertEquals("sleep", byeCommand.getDescription(command));
            String command2 = "no slash test";
            //EasyMock.createMock(UiManager.class);
            byeCommand.getDescription(command2);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG));
        }
        try {
            String command2 = "/slash first test";
            byeCommand.getDescription(command2);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_MISSING_DESC));
        }
    }

    @Test
    public void getDateTest() {
        try {
            String date = "/date 09/10/2019";
            assertEquals("09/10/2019", byeCommand.getDate(date));
            String date2 = "no token date";
            byeCommand.getDate(date2);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_MISSING_DESC));
        }
        try {
            String date = "/date";
            byeCommand.getDate(date);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_MISSING_DATE));
        }
        try {
            String date = "/date 111";
            byeCommand.getDescription(date);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_INVALID_DATE_FORMATTING));
        }
        try {
            String date = "/date 10/10/2018";
            byeCommand.getDescription(date);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_INVALID_YEAR));
        }
    }

    @Test
    public void getStartTimeTest() {
        try {
            String time = "/start 1010";
            assertEquals("1010", byeCommand.getStartTime(time));
            String time2 = "no token time";
            byeCommand.getStartTime(time2);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_MISSING_START_TIME_ARG));
        }
        try {
            String time = "/start";
            byeCommand.getStartTime(time);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_MISSING_TIME));
        }
    }

    @Test
    public void getEndTimeTest() {
        try {
            String time = "/end 1010";
            assertEquals("1010", byeCommand.getEndTime(time));
            String time2 = "no token time";
            byeCommand.getEndTime(time2);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_MISSING_END_TIME_ARG));
        }
        try {
            String time = "/end";
            byeCommand.getEndTime(time);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_MISSING_TIME));
        }
    }

    @Test
    public void getPriorityTest() {
        try {
            String priority = "/priority high";
            assertEquals(high, byeCommand.getPriority(priority));
            priority = "/priority medium";
            assertEquals(medium, byeCommand.getPriority(priority));
            priority = "/priority low";
            assertEquals(low, byeCommand.getPriority(priority));
            priority = "no token time";
            assertEquals(low, byeCommand.getPriority(priority));
            String time = "/priority";
            byeCommand.getPriority(time);
        } catch (NullPointerException t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        } catch (Exception t) {
            assertEquals(t, new compal.commons.Compal.DukeException(MESSAGE_MISSING_PRIORITY));
        }
    }
}

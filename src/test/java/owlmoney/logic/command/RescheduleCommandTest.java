package owlmoney.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import owlmoney.logic.exception.DukeException;
import owlmoney.model.task.Deadline;
import owlmoney.model.task.TaskList;

public class RescheduleCommandTest {
    /**
     * Tests if the correct exception is thrown when input is empty.
     */
    @Test
    void rescheduleCommand_NoDateAndIndex_throwsDukeException() {
        String input = "";
        DukeException err = assertThrows(DukeException.class, () -> new RescheduleCommand(input));
        assertEquals("Reschedule inputs cannot be blank or space.",err.toString());
    }

    /**
     * Tests if the correct exception is thrown when input does not have the correct number of fields.
     */
    @Test
    void rescheduleCommand_WrongNumberOfFields_throwsDukeException() {
        String input = "1 12/12/2019";
        DukeException err = assertThrows(DukeException.class, () -> new RescheduleCommand(input));
        assertEquals("A task, date and time must be specified.", err.toString());
        String input2 = "1 12/12/2019 1200 1200";
        err = assertThrows(DukeException.class, () -> new RescheduleCommand(input2));
        assertEquals("Invalid input."
                + " Reschedule format is \"reschedule <task number> <date> <time>\"", err.toString());
    }

    /**
     * Tests if the correct exception is thrown when the task index is invalid.
     */
    @Test
    void rescheduleCommand_NonIntTaskIndex_throwsDukeException() {
        String input = "g 12/12/2019 1200";
        DukeException err = assertThrows(DukeException.class, () -> new RescheduleCommand(input));
        assertEquals("The task number should be numeric only", err.toString());
    }

    /**
     * Tests if the correct exception is thrown when the task index is too large.
     */
    @Test
    void rescheduleCommand_TooBigNumberIndex_throwsDukeException() {
        String input = "99999999999 12/12/2019 1200";
        DukeException err = assertThrows(DukeException.class, () -> new RescheduleCommand(input));
        assertEquals("The number must be an integer and cannot exceed 9 digits", err.toString());
    }

    /**
     * Test if the correct exception is thrown when the date is of invalid format.
     */
    @Test
    void rescheduleCommand_WrongDateFormat_throwsDukeException() {
        String input = "2 r/r/rrrr 1200";
        DukeException err = assertThrows(DukeException.class, () -> new RescheduleCommand(input));
        assertEquals("The date format is wrong, please try in DD/MM/YYYY format",err.toString());
    }

    /**
     * Test if the date is successfully changed.
     */
    @Test
    void rescheduleTask_SuccessfulChangeOfDate_expectChangedDate() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        TaskList tempList = new TaskList();
        try {
            tempList.add(Deadline.create("testing /by 12/12/2019 1200"));
        } catch (DukeException err) {
            System.out.println("Error...");
        }
        System.out.println(tempList.size());
        String date = "11/11/2020 2345";
        LocalDateTime formattedDate = LocalDateTime.parse(date, inputFormatter);
        try {
            tempList.rescheduleTask(0, formattedDate);
        } catch (DukeException err) {
            System.out.println("Error...");
        }
        assertEquals("[D][✘] testing (by: Wednesday 11 November 2020 11:45 PM)",
                tempList.get(1).toString());
    }
}

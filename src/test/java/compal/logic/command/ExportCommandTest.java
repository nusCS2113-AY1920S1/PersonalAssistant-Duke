package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//@@author SholihinK
class ExportCommandTest {


    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private TaskList taskListMain = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("CS2105 Lecture", Task.Priority.medium, "01/10/2019", "01/10/2019", "1400", "1500");
        event1.markAsDone();
        event1.setHasReminder(true);

        taskArrListMain.add(event1);

        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "03/10/2019", "1500");
        deadline1.markAsDone();
        deadline1.setHasReminder(true);

        taskArrListMain.add(deadline1);

        this.taskListMain.setArrList(taskArrListMain);
    }

    /*@Test
    public void execute_export_success() throws CommandException {
        String filePathz = new File("testExport1").getAbsolutePath();
        String filePath = new File("testExport1.ics").getAbsolutePath();
        new ExportCommand(filePathz).commandExecute(taskListMain);
        BufferedReader reader;
        String testedString = "";
        try {
            reader = new BufferedReader(new FileReader(
                filePath));
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("UID:") || line.contains("DTSTAMP:")) {
                    line = reader.readLine();
                }
                testedString += (line) + "\n";
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String expectedString = "BEGIN:VCALENDAR\n" + "PRODID:-//Ben Fortuna//iCal4j 1.0//EN\n"
            + "VERSION:2.0\n" + "CALSCALE:GREGORIAN\n" + "BEGIN:VEVENT\n"
            + "SUMMARY:CS2105 Lecture\n" + "DESCRIPTION: Priority:medium\n" + "DTSTART:20191001T140000\n"
            + "DTEND:20191001T150000\n" + "BEGIN:VALARM\n"
            + "TRIGGER;VALUE=DATE-TIME:20191001T060000Z\n" + "ACTION:DISPLAY\n" + "DESCRIPTION:CS2105 Lecture\n"
            + "END:VALARM\n" + "END:VEVENT\n" + "BEGIN:VEVENT\n"
            + "SUMMARY:Deadline 1\n" + "DESCRIPTION: Priority:high\n" + "DTSTART:20191003T150000\n"
            + "DTEND:20191003T150000\n" + "BEGIN:VALARM\n"
            + "TRIGGER;VALUE=DATE-TIME:20191003T070000Z\n" + "ACTION:DISPLAY\n" + "DESCRIPTION:Deadline 1\n"
            + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR\n";
        Assertions.assertEquals(expectedString, testedString);
        File file = new File("testExport1.ics");
        file.delete();
    }*/
}
package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static compal.logic.command.CommandTestUtil.assertCommandFailure;
import static compal.logic.command.ImportCommand.MESSAGE_FILE_NON_EXIST;
import static compal.logic.command.ImportCommand.MESSAGE_FILE_NON_ICS;

//@@author SholihinK
class ImportCommandTest {
    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListEmpty = new ArrayList<>();
    private TaskList taskListMain = new TaskList();
    private TaskList taskListEmpty = new TaskList();

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
        this.taskListEmpty.setArrList(taskArrListEmpty);
    }

    @Test
    void execute_fileNonExist_fail() {
        ImportCommand importCommand = new ImportCommand("~?!@");
        assertCommandFailure(importCommand, taskListMain, MESSAGE_FILE_NON_EXIST);
    }

    @Test
    void execute_NonIcsFile_fail() {

        String fileData = "fake ics";
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("fake.ics");
            try {
                fos.flush();
                fos.write(fileData.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImportCommand importCommand = new ImportCommand("fake");
        assertCommandFailure(importCommand, taskListMain, MESSAGE_FILE_NON_ICS);
        File file = new File("fake.ics");
        file.delete();
    }

    @Test
    public void execute_import_success() throws CommandException {
        new ExportCommand("testExport").commandExecute(taskListMain);
        CommandResult test = new ImportCommand("testExport").commandExecute(taskListEmpty);
        String status = "\u2718";
        String expectedString = "I have tried importing the given File!\n" + "The results are below:\n" + "\n"
            + "The following tasks were added: \n" + "\n" + " \n"
            + "Task ID:0\n" + "[E][" + status + "] CS2105 Lecture \n" + "Date: 01/10/2019 \n" + "Start Time: 1400 \n"
            + "End Time: 1500 \n" + "Priority: medium\n" + "***************\n" + "\n\n"
            + "The following deadline were added: \n" + "\n" + " \n" + "Task ID:1\n"
            + "[D][" + status + "] Deadline 1 \n"
            + "Date: 03/10/2019 \n" + "End Time: 1500 \n" + "Priority: high\n" + "***************\n" + "\n";

        String testedString = test.feedbackToUser;
        Assertions.assertEquals(expectedString, testedString);
        File file = new File("testExport.ics");
        file.delete();
    }
}
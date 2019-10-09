package compal.model;

import compal.commons.Compal;
import compal.logic.commands.DeleteCommand;
import compal.model.tasks.Deadline;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static compal.model.tasks.Task.Priority.high;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class TaskListTest {
    private Compal compal;
    private TaskList taskList;
    private String description = "Test content";
    private String date = "01/10/2019";
    private Deadline deadline;
    private Task.Priority priority = high;
    private String endTime = "1230";

    @BeforeEach
    public void setup() {
        //model.parser.processCommands("deadline return book /by 2/12/2019 1800");
        compal = new Compal();
        taskList = new TaskList(compal);
        deadline = new Deadline(description, priority, date, endTime);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, taskList.compal);
    }
}
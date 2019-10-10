package compal.model;

import compal.commons.Compal;
import compal.model.tasks.Deadline;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static compal.model.tasks.Task.Priority.high;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskListTest {
    private Compal compal;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        //model.parser.processCommands("deadline return book /by 2/12/2019 1800");
        compal = new Compal();
        taskList = new TaskList(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, taskList.compal);
    }
}
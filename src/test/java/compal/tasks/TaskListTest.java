package compal.tasks;

import compal.compal.Compal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskListTest {
    private Compal compal;
    private TaskList taskList;

    @BeforeAll
    public void setup() {
        //model.parser.processCommands("deadline return book /by 2/12/2019 1800");
        compal = new Compal();
        taskList = new TaskList(compal);
    }

    @Test
    public void initest() {
        assertEquals(compal, taskList.compal);
    }
}
package compal.tasks;

import compal.main.Duke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    private Duke model = new Duke();
    private Duke expectedModel = new Duke();

    @BeforeEach
    public void setup() throws Exception {
        //model.parser.processCommands("deadline return book /by 2/12/2019 1800");
    }

    @Test
    void viewDate() {
    }
}
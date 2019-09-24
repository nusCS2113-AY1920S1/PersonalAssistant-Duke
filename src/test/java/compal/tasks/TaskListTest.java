package compal.tasks;

import compal.compal.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskListTest {
    private Compal model = new Compal();
    private Compal expectedModel = new Compal();

    @BeforeEach
    public void setup() throws Exception {
        //model.parser.processCommands("deadline return book /by 2/12/2019 1800");
    }

    @Test
    void viewDate() {
    }
}
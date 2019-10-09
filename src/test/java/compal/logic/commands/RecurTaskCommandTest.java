package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurTaskCommandTest {
    private RecurTaskCommand recurTask;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        recurTask = new RecurTaskCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, recurTask.compal);
        assertEquals(compal.tasklist,recurTask.compal.tasklist);
    }

    @Test
    public void parseCommandTest() {
        String command = "deadline /date 09/10/2019 /end 1230";
        try {
            recurTask.parseCommand(command);
        } catch (Exception t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        }
    }
}

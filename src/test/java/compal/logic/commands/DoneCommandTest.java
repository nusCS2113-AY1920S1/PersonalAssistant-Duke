package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoneCommandTest {
    private DoneCommand doneCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        doneCommand = new DoneCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, doneCommand.compal);
        assertEquals(compal.tasklist,doneCommand.compal.tasklist);
    }
}

package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineCommandTest {
    private DeadlineCommand deadlineCommand;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        deadlineCommand = new DeadlineCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, deadlineCommand.compal);
        assertEquals(compal.tasklist,deadlineCommand.compal.tasklist);
    }
}

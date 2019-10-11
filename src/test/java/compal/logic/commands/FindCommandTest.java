package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindCommandTest {
    private FindCommand findCommand;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        findCommand = new FindCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, findCommand.compal);
        assertEquals(compal.tasklist,findCommand.compal.tasklist);
    }
}

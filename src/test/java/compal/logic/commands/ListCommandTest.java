package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {
    private ListCommand listCommand;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        listCommand = new ListCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, listCommand.compal);
        assertEquals(compal.tasklist,listCommand.compal.tasklist);
    }
}

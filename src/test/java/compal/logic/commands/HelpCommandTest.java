package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {
    private HelpCommand helpCommand;
    private Compal compal;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        helpCommand = new HelpCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, helpCommand.compal);
    }
}

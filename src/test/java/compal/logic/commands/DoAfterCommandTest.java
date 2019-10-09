package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoAfterCommandTest {
    private DoAfterCommand doAfterCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        doAfterCommand = new DoAfterCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, doAfterCommand.compal);
        assertEquals(compal.tasklist,doAfterCommand.compal.tasklist);
    }
}

package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCommandTest {
    private DeleteCommand deleteCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        deleteCommand = new DeleteCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, deleteCommand.compal);
        assertEquals(compal.tasklist,deleteCommand.compal.tasklist);
    }
}

package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;

public class ByeCommand {
    private ByeCommand byeCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        byeCommand = new ByeCommand();
    }
}

package compal.logic.commands;

import compal.Main;
import compal.commons.Compal;
import compal.model.tasks.Deadline;
import compal.model.tasks.Task;
import javafx.application.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static compal.model.tasks.Task.Priority.high;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineCommandTest {
    private DeadlineCommand deadlineCommand;
    private Compal compal;
    private Thread t;

    @BeforeEach
    public void setUp() {
        compal = new Compal();
        deadlineCommand = new DeadlineCommand(compal);
        t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(Main.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }

    @Test
    public void iniTest() {
        assertEquals(compal, deadlineCommand.compal);
        assertEquals(compal.tasklist,deadlineCommand.compal.tasklist);
    }
}

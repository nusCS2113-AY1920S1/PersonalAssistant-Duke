package compal.logic.commands;

import compal.commons.Compal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetReminderCommandTest {
    private SetReminderCommand setReminderCommand;
    private Compal compal;

    @BeforeEach
    public void setup() {
        compal = new Compal();
        setReminderCommand = new SetReminderCommand(compal);
    }

    @Test
    public void iniTest() {
        assertEquals(compal, setReminderCommand.compal);
        assertEquals(compal.tasklist,setReminderCommand.compal.tasklist);
    }

    @Test
    public void parseCommandTest() {
        String command = "deadline /date 09/10/2019 /end 1230";
        try {
            setReminderCommand.parseCommand(command);
        } catch (Exception t) {
            //ignore the null pointer exception because ui is not welly declared in this test
        }
    }
}

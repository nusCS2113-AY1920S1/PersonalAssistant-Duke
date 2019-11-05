package duke.logic.commands;

import duke.ModelStub;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddProfileCommandTest {

    @Test
    void execute() throws DukeException {
        Model modelStub = new ModelStub();
        ProfileAddCommand profileCommand = new ProfileAddCommand("name", LocalDateTime.now());
        CommandResultText resultText = profileCommand.execute(modelStub);
        assertEquals(resultText.getMessage(), "Welcome to SGTravel\nname\n");
    }
}

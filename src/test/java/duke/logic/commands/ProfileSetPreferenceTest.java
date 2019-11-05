package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.results.CommandResult;
import duke.model.Model;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProfileSetPreferenceTest {
    @Test
    void execute() throws DukeException {
        Model modelStub = new ModelStub();
        assertThrows(ParseException.class, () -> new ProfileSetPreferenceCommand("sports", "falseee"));
        assertThrows(ParseException.class, () -> new ProfileSetPreferenceCommand("spoorts", "true"));
        ProfileSetPreferenceCommand profileSetPreferenceCommand =
                new ProfileSetPreferenceCommand("sports", "false");
        CommandResult resultText = profileSetPreferenceCommand.execute(modelStub);
        assertEquals(resultText.getMessage(), "Your preference for sports is set to false");
    }
}

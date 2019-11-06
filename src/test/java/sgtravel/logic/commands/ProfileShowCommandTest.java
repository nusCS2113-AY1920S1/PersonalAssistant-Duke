package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileShowCommandTest {

    @Test
    void execute() throws DukeException {
        Model modelStub = new ModelStub();
        ProfileShowCommand profileShowCommand = new ProfileShowCommand();
        CommandResultText resultText = profileShowCommand.execute(modelStub);
        assertEquals(resultText.getMessage(), "PROFILE:\n\n" + "Name: User\n"
                + "Age: 0\n" + "Likes:\n" + "sports : false\n" + "entertainment : false\n" + "arts : false\n"
                + "lifestyle : false\n");
    }
}

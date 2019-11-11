package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.SingaporeTravelException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileShowCommandTest {

    @Test
    void execute() throws SingaporeTravelException {
        Model modelStub = new ModelStub();
        ProfileShowCommand profileShowCommand = new ProfileShowCommand();
        CommandResultText resultText = profileShowCommand.execute(modelStub);
        assertEquals(resultText.getMessage(), "PROFILE:\n"
                + "\n"
                + "Name: New User\n"
                + "Age: 0\n"
                + "\n"
                + "Likes:\n"
                + "\n"
                + "Favorite Itinerary:\n");
    }
}

package seedu.hustler.logic.command.avatar;

import seedu.hustler.Hustler;
import seedu.hustler.data.AvatarStorage;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.SetNameAnomaly;
import seedu.hustler.ui.Ui;
import java.io.IOException;

/**
 * Command that sets the name of the avatar.
 */
public class SetNameCommand extends Command {

    /**
     * The user input containing the name to be set.
     */
    private String[] userInput;

    /**
     * The anomaly class to check if the user input is valid.
     */
    private SetNameAnomaly anomaly = new SetNameAnomaly();


    /**
     * Constructs a new SetNameCommand using the user inputs.
     * @param userInput the String array of the user input.
     */
    public SetNameCommand(String[] userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute() throws IOException {
        Ui ui = new Ui();
        try {
            anomaly.detect(userInput);
            AvatarStorage.save(Hustler.avatar.setName(userInput[1]));
            ui.showNameChangeSuccess();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }

    }
}

package seedu.hustler.command.avatar;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.data.AvatarStorage;
import seedu.hustler.ui.Ui;
import java.io.IOException;

/**
 * Command that sets the name of the avatar.
 */
public class SetNameCommand extends Command {

    /**
     * The new name of the avatar.
     */
    private String name;

    /**
     * Constructs a new SetNameCommand using the user inputs.
     * @param userInput the String array of the user input.
     */
    public SetNameCommand(String[] userInput) {
        this.name = userInput[1].split(" ", 2)[1];
    }

    @Override
    public void execute() throws IOException {
        Ui ui = new Ui();
        Hustler.avatar.setName(name);
        AvatarStorage.save(Hustler.avatar);
        ui.showNameChangeSuccess();
    }
}

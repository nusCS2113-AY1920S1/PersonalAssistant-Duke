package owlmoney.logic.command.profile;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.model.profile.exception.ProfileException;
import owlmoney.ui.Ui;

import java.util.logging.Logger;

import static owlmoney.commons.log.LogsCenter.getLogger;

/**
 * Executes EditProfileCommand to edit profile name.
 */
public class EditProfileCommand extends Command {
    private final String name;
    private final String newName;
    private static final Logger logger = getLogger(EditProfileCommand.class);

    /**
     * Creates an instance of EditProfileCommand.
     *
     * @param name    Name of goal object.
     * @param newName New name of object.
     */
    public EditProfileCommand(String name, String newName) {
        this.name = name;
        this.newName = newName;
    }

    /**
     * Executes the function to edit profile name.
     *
     * @param profile Profile of the user.
     * @param ui      Ui of OwlMoney.
     * @return false so OwlMoney will not terminate yet.
     * @throws ProfileException If profile name does not exists / invalid parameters provided.
     */
    @Override
    public boolean execute(Profile profile, Ui ui) throws ProfileException {
        profile.profileSetUsername(this.name, this.newName, ui);
        logger.info("Successful execution of editing profile");
        return this.isExit;
    }
}

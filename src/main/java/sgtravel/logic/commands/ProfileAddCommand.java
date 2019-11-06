package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

import java.time.LocalDateTime;

/**
 * Sets up the profile.
 */
public class ProfileAddCommand extends Command {

    private String name;
    private LocalDateTime birthday;

    /**
     * Creates a new AddProfileCommand with name and birthday.
     *
     * @param name The name of the person.
     * @param birthday The birthday of the person.
     */
    public ProfileAddCommand(String name, LocalDateTime birthday) {

        this.name = name;
        this.birthday = birthday;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing the profile.
     * @throws FileNotSavedException If the data could not be saved.
     */
    @Override
    public CommandResultText execute(Model model) throws FileNotSavedException {
        model.getProfileCard().setPerson(name, birthday);
        model.save();
        return new CommandResultText(Messages.STARTUP_WELCOME_MESSAGE + name + "\n");
    }
}

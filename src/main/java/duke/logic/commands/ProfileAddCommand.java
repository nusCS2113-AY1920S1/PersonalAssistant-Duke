package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

import java.time.LocalDateTime;

public class ProfileAddCommand extends Command {
    private String name;
    private LocalDateTime birthday;

    public ProfileAddCommand(String name, LocalDateTime birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public CommandResultText execute(Model model) throws DukeException {
        model.getProfileCard().setPerson(name, birthday);
        model.save();
        return new CommandResultText(Messages.STARTUP_WELCOME_MESSAGE + name + "\n");
    }
}

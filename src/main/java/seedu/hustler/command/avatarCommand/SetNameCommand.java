package seedu.hustler.command.avatarCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.game.avatar.Avatar;

public class SetNameCommand extends Command {

    private String name;

    public SetNameCommand(String[] userInput) {
        this.name = userInput[1].split(" ", 2)[1];
    }
    public void execute() {
        Hustler.avatar.setName(name);
    }
}

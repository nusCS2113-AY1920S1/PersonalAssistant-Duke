package seedu.hustler.command.avatarCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.data.AvatarStorage;
import seedu.hustler.game.avatar.Avatar;

import java.io.IOException;

public class SetNameCommand extends Command {

    private String name;

    public SetNameCommand(String[] userInput) {
        this.name = userInput[1].split(" ", 2)[1];
    }
    public void execute() throws IOException {
        Hustler.avatar.setName(name);
        AvatarStorage.save(Hustler.avatar);

    }
}

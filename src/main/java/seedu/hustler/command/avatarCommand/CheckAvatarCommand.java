package seedu.hustler.command.avatarCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;

/**
 * Command that checks the avatar's current status.
 */
public class CheckAvatarCommand extends Command {

    public CheckAvatarCommand() {

    }

    @Override
    public void execute() {
        System.out.println("\t_____________________________________");
        System.out.println(Hustler.avatar.toString());
        System.out.println("\t_____________________________________");
    }
}

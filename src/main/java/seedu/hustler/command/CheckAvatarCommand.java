package seedu.hustler.command;

import seedu.hustler.Hustler;

/**
 * Command that checks the avatar's current status.
 */
public class CheckAvatarCommand extends Command{

    public CheckAvatarCommand() {

    }

    @Override
    public void execute() {
        System.out.println("\t_____________________________________");
        System.out.println(Hustler.avatar.toString());
        System.out.println("\t_____________________________________");
    }
}

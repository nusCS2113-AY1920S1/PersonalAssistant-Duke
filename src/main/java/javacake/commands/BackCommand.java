package javacake.commands;

import javacake.*;

public class BackCommand extends Command {

    public BackCommand() { type = CmdType.BACK; }

    public void execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        if (progressStack.checkProgress() == 1) {
            Duke frontPage = new Duke("data/saved_data.txt");
        }
    }

}

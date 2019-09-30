package javacake.commands;

import javacake.*;

import java.io.IOException;

public class BackCommand extends Command {

    public BackCommand() { type = CmdType.BACK; }

    public void execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException, IOException {
        if (progressStack.checkProgress() == 2) {
            progressStack.listIndexToMainList();
            ListCommand listCommand = new ListCommand();
            listCommand.execute(progressStack, ui, storage, profile);
        } else if (progressStack.checkProgress() == 1) {
            ListCommand listCommand = new ListCommand();
            listCommand.execute(progressStack, ui, storage, profile);
        } else if (progressStack.checkProgress() == 3 && progressStack.checkPreviousState() == 3) {
            progressStack.clearPreviousState();
            GoToCommand goToCommand = new GoToCommand("goto 3");
            goToCommand.execute(progressStack, ui, storage, profile);
        }
    }

}

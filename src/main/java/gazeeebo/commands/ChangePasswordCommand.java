package gazeeebo.commands;

import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class ChangePasswordCommand extends Command {
    /**
     *  This method will verify current password and write the new password to the Password.txt file.
     * @param list task lists
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Enter your current password:");
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals(storage.password().get(0))) {
                System.out.println("Enter new password:");
                ui.readCommand();
                storage.storagesPassword(ui.fullCommand);
                System.out.println("Password successfully changed :)");
                break;
            } else {
                System.out.println("Wrong password, exit by entering esc or try again:");
                ui.readCommand();
            }
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}

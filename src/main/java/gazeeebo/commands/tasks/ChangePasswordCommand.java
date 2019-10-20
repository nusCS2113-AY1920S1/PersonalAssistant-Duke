
package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class ChangePasswordCommand extends Command {
    /**
     * This method will verify current password and write the new password to the Password.txt file.
     *
     * @param list    task lists
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException catch the error if the read file fails.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Enter your current password:");
        ui.readCommand();
        ArrayList<String> password_list = new ArrayList<>();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals(storage.Password().get(0).toString())) {
                System.out.println("Enter new password:");
                ui.readCommand();
                String realPassword = ui.fullCommand;
                char[] decryption = realPassword.toCharArray();
                StringBuilder decodedPassword = new StringBuilder();
                for (int i = realPassword.length() - 1; i >= 0; i--) {
                    decodedPassword.append(decryption[i]);
                }
                storage.Storages_password(decodedPassword.toString());
                System.out.println("Password successfully changed.");
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
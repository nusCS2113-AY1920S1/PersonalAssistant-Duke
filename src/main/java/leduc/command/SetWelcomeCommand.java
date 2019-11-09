/* @@author rshah918 */
package leduc.command;

import leduc.Ui;
import leduc.exception.FileException;
import leduc.storage.Storage;
import leduc.task.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class SetWelcomeCommand extends Command{

    public SetWelcomeCommand(String user){
       super(user);
    }

    /**
     * static variable used for shortcut
     */
    private static String setWelcomeShortcut = "setwelcome";

    /**
     * Allow to change the welcome message.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws FileException Exception caught when the file can't be open or read or modify.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FileException {
        FileWriter fileWriter = null;
        File file = storage.getWelcomeFile();
        //open fileWriter object
        String welcomeMessage;
        try {
            fileWriter = new FileWriter(file);
            try {
                //removes the first word of the user input
                welcomeMessage = String.join(" ", Arrays.copyOfRange(user.split(" "), 1, user.split( " ").length));
                fileWriter.write(welcomeMessage);
            }
            finally{
                fileWriter.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new FileException();
        }
        ui.showNewWelcome(welcomeMessage);
    }
/* @@author */
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getSetWelcomeShortcut() {
        return setWelcomeShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param setWelcomeShortcut the new shortcut
     */
    public static void setSetWelcomeShortcut(String setWelcomeShortcut) {
        SetWelcomeCommand.setWelcomeShortcut = setWelcomeShortcut;
    }
}

package leduc.command;

import leduc.Ui;
import leduc.exception.FileException;
import leduc.storage.ConfigStorage;
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
    public static String setWelcomeShortcut = "setwelcome";

    /**
     * Allow to change the welcome message.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param configStorage
     * @throws FileException Exception caught when the file can't be open or read or modify.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage, ConfigStorage configStorage) throws FileException {
        FileWriter fileWriter = null;
        String filepath = System.getProperty("user.dir")+ "/data/welcome.txt";//get location of welcome message file
        File file = Ui.openFile(filepath);
        //open fileWriter object
        try {
            fileWriter = new FileWriter(file);
            try {
                //removes the first word of the user input
                fileWriter.write(String.join(" ", Arrays.copyOfRange(user.split(" "), 1, user.split( " ").length)));
            }
            finally{
                fileWriter.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new FileException();
        }
    }
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

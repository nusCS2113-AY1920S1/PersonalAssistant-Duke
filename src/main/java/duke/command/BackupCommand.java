package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

//@@author maxxyx96
/**
 * Representing a command that updates the current data file, and shows the folder
 * that the file is contained in to the user.
 */
public class BackupCommand extends Command {

    protected static File filePath = new File("./data");

    /**
     * Opens the stored data file in the user's computer.
     *
     * @throws IOException  If there is an error reading the file.
     */
    public static void openBackupFolder() throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(filePath);
    }

    /**
     * Executes a command using task list and outputs the result.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        ui.showBackupMessage();
        ui.showBackupFolderMessage();
    }

    /**
     * Executes a command using task list and outputs the result (GUI).
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return ui.showBackupMessageGui();
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that the program is exiting.
     * @param storage The storage to be overwritten.
     * @throws IOException  If there is an error reading the file.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {
        openBackupFolder();
    }

}
//@@author
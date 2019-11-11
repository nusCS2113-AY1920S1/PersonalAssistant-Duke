package rims.command;

import java.io.IOException;

import rims.core.ResourceList;
import rims.core.Rims;
import rims.core.Storage;
import rims.core.Ui;

import rims.exception.RimsException;
import rims.resource.TagList;

//@@author rabhijit
/**
 * Carries out the necessary operations to close RIMS; saving all existing
 * Resources and Reservations to disk in text format, sending the farewell
 * message, and setting the exit code to terminate the program.
 */
public class CloseCommand extends Command {
    /**
     * Saves all existing Resources and Reservations to disk in a text format, sends
     * a farewell message, and sets the exit code to true to halt the RIMS program.
     *
     * @param ui        An instance of the user interface.
     * @param storage   An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus
     *                  far.
     * @throws IOException if there is an error in saving Resources or Reservations
     *                     to disk
     */
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException {
        TagList newTagList = Rims.tags;
        storage.saveToFile(resources.getResources());
        try {
            storage.saveToFileTags(newTagList.getTags());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.farewell();
        setExitCode();
    }
}
package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

import rims.exception.RimsException;

//@@author hin1
/**
 * Undoes the last command (e.g. AddCommand, LoanCommand) that was executed that
 * made a change in Model component (ResourceList, ReservationList).
 *
 * <p>This is done by reloading a screenshot of the previous state of Model from
 * the external .txt files.
 *
 * <p>Current list of commands supported:
 * Add, Delete, Loan, Reserve, Return
 */
public class UndoCommand extends Command {
    protected Command prevCommand;

    /**
     * Constructor of an UndoCommand, which takes in the parameter of a Command
     * object, for the Ui to notify the user about the command that was undone.
     *
     * @param previousCommand Command inputted by the user that last changed
     *                        ResourceList.
     */
    public UndoCommand(Command previousCommand) {
        prevCommand = previousCommand;
        canModifyData = false;
        commandUserInput = "undo";
    }

    /**
     * Undoes the previous Command by reloading the previous state captured in the
     * external .txt files back into ResourceList resources by using storage. Not
     * required to amend if new commands are present.
     *
     * @param ui        An instance of the user interface.
     * @param storage   An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus
     *                  far.
     * @throws RimsException for any data-related error in undoing the command.
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException {
        if (prevCommand == null) {
            ui.formattedPrint("No command has modified this inventory yet!");
            return;
        }
        storage.readResourceFile();
        resources.setResources(storage.getResources());

        ui.formattedPrint("The following command has been undone: " + prevCommand.getCommandUserInput());
    }
}

package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimsException;

import java.io.IOException;
import java.text.ParseException;

//@@author hin1
/**
 * Undoes the last command (e.g. AddCommand, LoanCommand) that was executed that
 * made a change in Model component (ResourceList, ReservationList).
 *
 * This is done by reloading a screenshot of the previous state of Model
 * from the external .txt files.
 */
public class UndoCommand extends Command {

    protected Command prevCommand;

    /**
     * Constructor of an UndoCommand, which takes in the parameter of a Command object, for the Ui to
     * notify the user about the command that was undone.
     * @param previousCommand
     */
    public UndoCommand(Command previousCommand) {
        prevCommand = previousCommand;
    }

    @Override
    /**
     * Undoes the previous Command by reloading the previous state captured in the external .txt files
     * back into ResourceList resources by using storage. Not required to amend if new commands are present.
     */
    public void execute(Ui ui, Storage storage, ResourceList resources) throws ParseException, IOException, RimsException {
        storage.readResourceFile();
        resources = new ResourceList(ui, storage.getResources());

        ui.printLine();
        ui.print("The previous command has been undone!");
        ui.printLine();
    }
}

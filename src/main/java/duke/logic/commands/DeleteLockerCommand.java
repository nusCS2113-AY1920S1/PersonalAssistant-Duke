package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.ui.Ui;

import java.util.List;

public class DeleteLockerCommand extends Command {

    private List<String> splitInput;

    public DeleteLockerCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        try {
            int temp = Integer.parseInt(splitInput.get(0)) - 1;
            String lockerA = lockerList.getLocker(temp).toString();
            lockerList.deleteLocker(lockerList.getLocker(temp));
            ui.deleteMessage(lockerList.getAllLockers(), lockerA);
            storage.saveData(lockerList);
        } catch (NumberFormatException obj) {
            throw new DukeException(" OOPS! Enter a positive integer after \"delete\"");
        } catch (IndexOutOfBoundsException obj) {
            throw new DukeException(" OOPS! Enter a number that is present in the list");
        }
    }
}

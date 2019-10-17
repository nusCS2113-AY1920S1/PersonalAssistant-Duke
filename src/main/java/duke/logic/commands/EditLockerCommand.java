package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.ui.Ui;

import java.util.List;

public class EditLockerCommand extends Command {
    private List<String> splitInput;

    public EditLockerCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        try {
            int temp = Integer.parseInt(splitInput.get(1)) - 1;
            lockerList.getLocker(temp).setTagAs(splitInput.get(2));
            String lockerA = lockerList.getLocker(temp).toString();
            ui.editMessage(lockerA);
            storage.saveData(lockerList);
        } catch (NumberFormatException obj) {
            throw new DukeException(" OOPS! Enter a positive integer after \"edit\"");
        } catch (IndexOutOfBoundsException obj) {
            throw new DukeException(" OOPS! Enter a number that is present in the list");
        }
    }
}

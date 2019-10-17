package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.Locker;
import duke.models.LockerList;
import duke.models.Tag;
import duke.storage.FileHandling;
import duke.ui.Ui;

import java.util.List;

public class AddLockerCommand extends Command {

    private List<String> splitInput;

    public AddLockerCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {

        if (splitInput.size() == 1) {
            throw new DukeException(" Please provide details of the locker to be added");
        }
        int serialNumber = Integer.parseInt(splitInput.get(1).substring(2));
        String address = splitInput.get(2).substring(2);
        String zone = splitInput.get(3).substring(2);
        lockerList.addLocker(new Locker(serialNumber,address,zone,new Tag("not-in-use")));
        String lockerA = lockerList.getLocker(lockerList.numLockers() - 1).toString();
        ui.printAddLocker(lockerList.getAllLockers(),lockerA);
        storage.saveData(lockerList);
    }
}

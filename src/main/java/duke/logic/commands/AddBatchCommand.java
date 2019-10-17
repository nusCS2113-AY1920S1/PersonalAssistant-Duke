package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.Locker;
import duke.models.LockerList;
import duke.models.Tag;
import duke.storage.FileHandling;
import duke.ui.Ui;

import java.util.List;

public class AddBatchCommand extends Command {

    private List<String> splitInput;

    public AddBatchCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {

        if (splitInput.size() == 1) {
            throw new DukeException(" You must include the serial numbers of the lockers to be added");
        }
        int numOfLockers = Integer.parseInt(splitInput.get(1).substring(2));
        int serialNumber = Integer.parseInt(splitInput.get(2).substring(2));
        String address = splitInput.get(3).substring(2);
        String zone = splitInput.get(4).substring(2);
        for (int i = 0; i < numOfLockers; i++) {
            lockerList.addLocker(new Locker(serialNumber + i,address,zone,new Tag("not-in-use")));
        }
        ui.printBatch(numOfLockers,serialNumber);
        storage.saveData(lockerList);
    }
}

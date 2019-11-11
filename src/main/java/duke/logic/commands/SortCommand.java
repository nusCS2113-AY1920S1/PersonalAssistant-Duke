package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.ObjectComparator;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Collections;

import static java.util.Objects.requireNonNull;

public class SortCommand extends Command {
    private final String sortBy;
    private final int checkAscOrDes;

    public static final String COMMAND_WORD = "sortby";
    public static final String INVALID_FORMAT =  " Invalid command format for sort command."
            + "\n     1. Only one of the token should be present (asc/ or dsc/)"
            + "\n     2. Keywords to use are, 'serialnumber', 'address', 'zone', 'tags'. ";

    /**
     * This constructor instantiates the SortCommand object.
     * @param sortBy stores the attribute of the locker to be sorted by.
     * @param checkAscOrDes stores a flag to indicate if the command is ascending or descending.
     */

    public SortCommand(String sortBy, int checkAscOrDes) {
        this.sortBy = sortBy;
        this.checkAscOrDes = checkAscOrDes;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {

        requireNonNull(lockerList);

        if (checkAscOrDes == 1) {

            if (this.sortBy.equals("serialNumber")) {
                Collections.sort(lockerList.getLockerList(), ObjectComparator.SerialNumberComparatorAsc);
                ui.printSortedLockers(lockerList.getLockerList());
            } else if (this.sortBy.equals("address")) {
                Collections.sort(lockerList.getLockerList(), ObjectComparator.AddressComparatorAsc);
                ui.printSortedLockers(lockerList.getLockerList());
            } else if (this.sortBy.equals("zone")) {
                Collections.sort(lockerList.getLockerList(), ObjectComparator.ZoneComparatorAsc);
                ui.printSortedLockers(lockerList.getLockerList());
            } else if (this.sortBy.equals("tags")) {
                Collections.sort(lockerList.getLockerList(), ObjectComparator.TagComparatorAsc);
                ui.printSortedLockers(lockerList.getLockerList());
            }
        }

        if (checkAscOrDes == 0) {

            if (this.sortBy.equals("serialNumber")) {
                Collections.sort(lockerList.getLockerList(), ObjectComparator.SerialNumberComparatorDes);
                ui.printSortedLockers(lockerList.getLockerList());
            } else if (this.sortBy.equals("address")) {
                Collections.sort(lockerList.getLockerList(), ObjectComparator.AddressComparatorDes);
                ui.printSortedLockers(lockerList.getLockerList());
            } else if (this.sortBy.equals("zone")) {
                Collections.sort(lockerList.getLockerList(), ObjectComparator.ZoneComparatorDes);
                ui.printSortedLockers(lockerList.getLockerList());
            } else if (this.sortBy.equals("tags")) {
                Collections.sort(lockerList.getLockerList(), ObjectComparator.TagComparatorDes);
                ui.printSortedLockers(lockerList.getLockerList());
            }

        }

    }
}
package duke.logic.commands;


import duke.exceptions.DukeException;
import duke.models.LockerList;

import duke.models.locker.Address;
import duke.models.locker.Usage;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.locker.Zone;
import duke.models.tag.Tag;
import duke.storage.FileHandling;
import duke.ui.Ui;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class EditLockerCommand extends Command {

    private final SerialNumber serialNumberOfLockerToEdit;
    private final EditLocker editLocker;
    private static final int GET_FIRST_INDEX = 0;

    private static final String EDIT_LOCKER_ERROR = " The following constraints are to be satisfied"
            + " for editing locker states:"
            + "\n     1. If a locker is currently not in the InUse state then its state cannot"
            + " be edited to InUse state"
            + "\n     2. A locker currently in InUse state can only be changed to broken state"
            + "\n     3. If case you change InUse to Broken then a new locker is"
            + " allocated to the student, if there are available lockers."
            + "\n     4. If there are no available lockers for the student, then his/hers subscription"
            + " is terminated.";

    /**
     * This constructor instantiates the editLockerCommand object.
     * @param serialNumber stores the serial number of the locker to be edited.
     * @param editLocker stores the contents that are to be edited.
     */
    public EditLockerCommand(SerialNumber serialNumber,
                             EditLocker editLocker) {
        requireNonNull(serialNumber);
        requireNonNull(editLocker);
        serialNumberOfLockerToEdit = serialNumber;
        this.editLocker = new EditLocker(editLocker);
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        Locker editedLocker = editLockerDetails(lockerList,ui);
        ui.showSuccessfullyEdited(editedLocker.toString());
        storage.saveData(lockerList);
    }

    private Locker editLockerDetails(LockerList lockerList, Ui ui) throws DukeException {
        Locker lockerToEdit = lockerList.getLockerToEdit(serialNumberOfLockerToEdit);
        Locker editedLocker = createEditedLocker(lockerToEdit,editLocker);
        if (!(editedLocker.hasSameSerialNumber(lockerToEdit))) {
            if (lockerList.isPresentLocker(editedLocker)) {
                throw new DukeException(" Duplicate entries not allowed. The serial number "
                        + "should be unique.");
            }
        }

        if (!validationChecks(lockerToEdit,editedLocker)) {
            throw new DukeException(EDIT_LOCKER_ERROR);
        }

        if (lockerToEdit.isOfTypeInUse() && !editedLocker.isOfTypeInUse()) {
            assignNewLocker(lockerToEdit, lockerList, ui);
        }
        lockerList.setLockerInPosition(editedLocker,lockerList.getIndexOfLocker(lockerToEdit));
        return editedLocker;
    }


    private void assignNewLocker(Locker lockerToEdit, LockerList lockerList, Ui ui) throws DukeException {
        assert lockerToEdit.getUsage().isPresent();
        List<Locker> getFreeLockers = lockerList.getAnyAvailableLocker(new Tag(Tag.NOT_IN_USE));
        if (getFreeLockers.size() == 0) {
            ui.showNoAvailableLockers();
        } else {
            Locker freeLocker = getFreeLockers.get(GET_FIRST_INDEX);
            freeLocker.setStatusAsInUse();
            freeLocker.setUsage(lockerToEdit.getUsage().get());
            int storeIndex = lockerList.getIndexOfLocker(freeLocker);
            lockerList.setLockerInPosition(freeLocker,
                    storeIndex);
            ui.printSuccessfulAllocation(lockerList.getLocker(storeIndex).toString());
        }
    }

    private boolean validationChecks(Locker lockerToEdit,Locker editedLocker) throws DukeException {
        Tag testInUse = new Tag(Tag.IN_USE);
        Tag testBroken = new Tag(Tag.BROKEN);

        if (lockerToEdit.hasSameTagAs(editedLocker.getTag())) {
            return true;
        }

        if (editedLocker.hasSameTagAs(testInUse)) {
            return false;
        }

        if (lockerToEdit.hasSameTagAs(testInUse)
                && !(editedLocker.hasSameTagAs(testBroken))) {
            return false;
        }
        return true;
    }

    private Locker createEditedLocker(Locker lockerToEdit,EditLocker editLocker) {
        assert lockerToEdit != null;
        SerialNumber editedSerialNumber = editLocker.getSerialNumber()
                .orElse(lockerToEdit.getSerialNumber());
        Address editedAddress = editLocker.getAddress().orElse(lockerToEdit.getAddress());
        Zone editedZone = editLocker.getZone().orElse(lockerToEdit.getZone());
        Tag editedTag = editLocker.getCondition().orElse(lockerToEdit.getTag());
        Usage usage = lockerToEdit.getUsage().orElse(null);

        return new Locker(editedSerialNumber,editedAddress,editedZone,editedTag,usage);
    }

    public static class EditLocker {
        private SerialNumber serialNumber;
        private Address address;
        private Zone zone;
        private Tag tag;

        public EditLocker() {
        }

        /**
         * A copy constructor used to copy the contents of the edited locker.
         */
        public EditLocker(EditLocker copyEditLocker) {
            setSerialNumber(copyEditLocker.serialNumber);
            setAddress(copyEditLocker.address);
            setZone(copyEditLocker.zone);
            setCondition(copyEditLocker.tag);
        }

        public boolean checkAnyFieldUpdated() {
            return (serialNumber != null || address != null
                    || zone != null || tag != null);
        }

        public void setSerialNumber(SerialNumber serialNumber) {
            this.serialNumber = serialNumber;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public void setZone(Zone zone) {
            this.zone = zone;
        }

        public void setCondition(Tag tag) {
            this.tag = tag;
        }

        public Optional<SerialNumber> getSerialNumber() {
            return Optional.ofNullable(serialNumber);
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public Optional<Zone> getZone() {
            return Optional.ofNullable(zone);
        }

        public Optional<Tag> getCondition() {
            return Optional.ofNullable(tag);
        }
    }
}

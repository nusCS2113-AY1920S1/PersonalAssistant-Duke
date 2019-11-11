package duke.storage;

import duke.models.LockerList;

import duke.exceptions.DukeException;
import duke.ui.Ui;

import java.util.ArrayList;

public class StateList {
    private Ui ui;
    private ArrayList<LockerList> stateList;
    private int statePointer;
    private static final String INITIAL = "initial";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";

    public StateList() {
        stateList = new ArrayList<>();
        ui = new Ui();
    }

    /**
     * This function initialise the statelist with the lockerlist loaded from saved data.
     * @param lockerList the list of lockers loaded from saved  data.
     */
    public void initializeStateList(LockerList lockerList) {
        updateStateList(lockerList);
        shiftStatePointer(INITIAL);
    }

    /**
     * This function updates the stateList by storing all the versions of lockerlist inside stateList.
     * @param lockerList version of the list of lockers after command.
     */
    public void updateStateList(LockerList lockerList) {
        LockerList tempLockerState = new LockerList(lockerList);
        stateList.add(tempLockerState);
    }

    /**
     * This function returned the requested version of lockerList after undo/redo.
     * @return version of lockerList after undo/redo.
     */
    public LockerList getStateList() {
        return stateList.get(statePointer);
    }

    public void commit() {
        removeStatesAfterCurrentPointer();
        shiftStatePointer(RIGHT);
    }

    /**
     * This function removes all the outdated version of lockerList after new lockerList is added to stateList.
     */
    private void removeStatesAfterCurrentPointer() {
        int unwantedStateBegin = statePointer + 1;
        int unwantedStateEnd = stateList.size();
        stateList.subList(unwantedStateBegin, unwantedStateEnd).clear();
    }

    /**
     * This function perform undo operation on the current version of lockerList.
     * @return the requested version of lockerList after undo.
     * @throws DukeException when there are no version of lockerList that can be undo.
     * @throws DukeException when user try to undo more than 10 versions of lockerList consecutively.
     */
    public LockerList undo() throws DukeException {
        if (cannotUndo()) {
            throw new DukeException(" There are no more commands to undo!");
        }
        if (reachedMaxUndo()) {
            throw new DukeException(" Oops, you can only undo up to 10 commands!");
        }
        ui.printSuccessfulUndo();
        shiftStatePointer(LEFT);
        return getStateList();
    }

    /**
     * This function perform redo operation on the current version of lockerList.
     * @return the requested version of lockerList after redo.
     * @throws DukeException when there are no version of lockerList that can be redo.
     */
    public LockerList redo() throws DukeException {
        if (cannotRedo()) {
            throw new DukeException(" There are no more commands to redo!");
        }
        ui.printSuccessfulRedo();
        shiftStatePointer(RIGHT);
        return getStateList();
    }

    /**
     * This function is used to check if user has undo more than 10 versions of lockerList consecutively.
     * @return true if the user has undo more than 10 versions of lockerList consecutively.
     */
    public boolean reachedMaxUndo() {
        return (stateList.size() - statePointer) > 10;
    }

    /**
     * This function is used to check if user can perform an undo operation.
     * @return true if the pointer is already pointing at the initial state of lockerList in stateList.
     */
    public boolean cannotUndo() {
        return statePointer <= 0;
    }

    /**
     * This function is used to check if user can perform a redo operation.
     * @return true if the pointer is already pointing at the last state of lockerList in stateList.
     */
    public boolean cannotRedo() {
        return statePointer >= stateList.size() - 1;
    }

    /**
     * This function is used to shift the pointer pointing to the state of lockerlist inside stateList.
     * @param shift instruction that either sets pointer as zero, or shifts towards left, or shifts towards right.
     */
    public void shiftStatePointer(String shift) {
        switch (shift) {
        case INITIAL:
            statePointer = 0;
            break;
        case LEFT:
            statePointer--;
            break;
        case RIGHT:
            statePointer++;
            break;
        default:
        }
    }
}
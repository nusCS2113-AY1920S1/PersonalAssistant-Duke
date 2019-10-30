//@@author WEIFENG-NUSCEG
package duke.util.mementopattern;

import duke.exceptions.DukeException;

import java.util.LinkedList;

public class MementoManager {
    private LinkedList<Memento> mementos = new LinkedList<>();

    /**
     *  .
     * @Param state
     */
    public void add(Memento state) {
        if (mementos.size() < 10) {
            mementos.addLast(state);
        } else {
            mementos.pollFirst();
            mementos.addLast(state);
        }
    }

    /**
     *  .
     * @return .
     */
    public Memento pop() throws DukeException {
        try {
            if (!mementos.isEmpty()) {
                return mementos.pollLast();
            } else {
                throw new DukeException("There are no more steps to undo!");
            }
        } catch (DukeException e) {
            throw new DukeException("You have reach the undo limits!" + e.getMessage());
        }
    }
}

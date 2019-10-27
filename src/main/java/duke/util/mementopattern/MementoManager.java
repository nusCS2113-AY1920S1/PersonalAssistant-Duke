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
        if (mementos.size() <= 5) {
            mementos.add(state);
        } else {
            mementos.poll();
            mementos.add(state);
        }
    }

    /**
     *  .
     * @return .
     */
    public Memento pop() throws DukeException {
        try {
            if (mementos.size() != 0) {
                return mementos.pop();
            } else {
                throw new DukeException("There are no more steps to undo!");
            }
        } catch (DukeException e) {
            throw new DukeException("You have reach the undo limits!" + e.getMessage());
        }
    }
}

package duke.MementoPattern;

import duke.core.DukeException;

import java.util.Stack;

public class MementoManager {
    private Stack<Memento> mementos = new Stack<>();

    public void add(Memento state){
        mementos.add(state);
    }

    public Memento pop() throws DukeException {
        try {
            return mementos.pop();
        } catch (Exception e) {
            throw new DukeException("You have reach the undo limits!");
        }
    }
}

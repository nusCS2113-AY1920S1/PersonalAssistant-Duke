package dolla.command.action.state;

import dolla.model.Record;

import java.util.ArrayList;

public class ShortcutState extends State {
    /**
     * This method will set the shortcutState in State class to be the input param.
     * @param shortcutStateState The shortcutState to be set in State class.
     */
    public ShortcutState(ArrayList<Record> shortcutStateState) {
        this.shortcutState = new ArrayList<>(shortcutStateState);
    }
}

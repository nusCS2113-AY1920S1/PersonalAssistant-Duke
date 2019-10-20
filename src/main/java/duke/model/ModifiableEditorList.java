package duke.model;

import duke.model.events.BindableEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifiableEditorList extends UnmodifiableEditorList {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ModifiableEditorList(TaskList tasks) {
        super(tasks);
    }

    public void setEvent(BindableEvent target, BindableEvent editedEvent) {
        int index = list.indexOf(target);
        if (index == -1) {
            logger.log(Level.SEVERE, "Illegal access on BindableEvent target");
        }
        if (contains(editedEvent)) {
            logger.log(Level.INFO, "Alrdy exist, Change this to Exception later");
        }
        if (isClash(editedEvent)) {
            logger.log(Level.INFO, "Timing clash, Change this to Exception later");
        }
        list.set(index, editedEvent);
    }

    public int size() {
        return list.size();
    }

    public boolean contains(BindableEvent e) {
        return list.stream().anyMatch(e::isSame);
    }

    public boolean isClash(BindableEvent e) {
        return list.stream().anyMatch(e::isClash);
    }
}

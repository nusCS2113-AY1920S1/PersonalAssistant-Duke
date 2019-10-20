package duke.model;

import duke.model.events.BindableEvent;
import duke.model.events.Event;
import duke.model.events.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UnmodifiableEditorList implements Iterable<BindableEvent> {
    protected List<BindableEvent> list = new ArrayList<>();

    public UnmodifiableEditorList(TaskList tasks) {
        for (Task t : tasks.getEventList()) {
            list.add(new BindableEvent((Event) t));
        }
    }

    @Override
    public Iterator<BindableEvent> iterator() {
        return list.iterator();
    }
}

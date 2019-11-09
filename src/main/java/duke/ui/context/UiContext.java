package duke.ui.context;

import duke.data.DukeObject;
import duke.exception.DukeException;
import javafx.util.Pair;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Stack;

/**
 * UI context of the application.
 */
public class UiContext {
    private DukeObject object;
    private Context context;
    private Stack<Pair<Context, DukeObject>> contexts;
    private PropertyChangeSupport pcs;

    /**
     * Constructs the UI context with HOME as the default value.
     */
    public UiContext() {
        this.context = Context.HOME;
        this.object = null;
        this.pcs = new PropertyChangeSupport(this);
        this.contexts = new Stack<>();
    }

    /**
     * Adds a listener to listen for changes in {@code context}.
     *
     * @param pcl PropertyChangeListener object.
     */
    public void addListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    /**
     * Displays the context associated with a DukeObject.
     *
     * @param obj     DukeObject whose context we wish to view.
     */
    public void open(DukeObject obj) {
        Context context = (obj == null) ? Context.HOME : obj.toContext();
        contexts.push(new Pair<>(this.context, this.object));
        updateContext(context, obj);
    }

    /**
     * Moves up one in the hierarchy of contexts.
     */
    public String moveUpOneContext() throws DukeException {
        if (context == Context.HOME) {
            throw new DukeException("You are already in the Home context.");
        } else {
            DukeObject parent = object.getParent();
            open(parent);
            return getViewingStr(context, parent);
        }
    }

    /**
     * Moves back one context.
     */
    public String moveBackOneContext() throws DukeException {
        if (contexts.empty()) {
            throw new DukeException("No previous context before this!");
        }

        Pair<Context, DukeObject> pair = contexts.pop();
        Context newContext = pair.getKey();
        DukeObject newObj = pair.getValue();
        updateContext(newContext, newObj);
        return getViewingStr(newContext, newObj);
    }

    private void updateContext(Context newContext, DukeObject object) {
        Context oldContext = this.context;
        this.context = newContext;
        this.object = object;
        pcs.firePropertyChange("context", oldContext, this.context);
    }

    public Context getContext() {
        return context;
    }

    public DukeObject getObject() {
        return object;
    }

    private String getViewingStr(Context context, DukeObject obj) {
        if (obj != null) {
            return "You are now viewing '" + obj.getName() + "' in the " + context.toString() + " context";
        } else {
            return "You are now in the home context!";
        }
    }
}

package duke.ui.context;

import duke.data.DukeObject;
import duke.exception.DukeException;
import javafx.util.Pair;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Stack;

//@@author gowgos5
/**
 * UI context of Dr. Duke.
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
     * Stores the current context and object, then shows the context associated with a DukeObject.
     *
     * @param obj DukeObject whose context we wish to access.
     */
    public void open(DukeObject obj) {
        contexts.push(new Pair<>(this.context, this.object));
        openWithoutHistory(obj);
    }

    /**
     * Displays the context associated with a DukeObject.
     *
     * @param obj DukeObject whose context we wish to view.
     */
    public void openWithoutHistory(DukeObject obj) {
        Context newContext = Context.HOME;
        if (obj != null) {
            obj.update();
            newContext = obj.toContext();
        }

        updateContext(newContext, obj);
    }

    /**
     * Moves up one in the hierarchy of contexts.
     *
     * @return Printable message that states the current {@code context} Dr. Duke is in.
     * @throws DukeException If Dr. Duke is currently in the HOME context.
     */
    public String moveUpOneContext() throws DukeException {
        if (context == Context.HOME) {
            throw new DukeException("You are already in the HOME context!");
        } else {
            DukeObject parent = object.getParent();
            open(parent);
            return getViewingStr(context, parent);
        }
    }

    /**
     * Moves back one context.
     *
     * @return Printable message that states the current {@code context} Dr. Duke is in.
     * @throws DukeException If there is no previous context stored in Dr. Duke.
     */
    public String moveBackOneContext() throws DukeException {
        if (contexts.empty()) {
            throw new DukeException("No previous context before this!");
        }

        Pair<Context, DukeObject> pair = contexts.pop();
        Context newContext = pair.getKey();
        DukeObject newObj = pair.getValue();
        openWithoutHistory(newObj);

        return getViewingStr(newContext, newObj);
    }

    /**
     * Updates current {@code context} of Dr. Duke with {@code newContext}.
     *
     * @param newContext New {@link Context}.
     * @param object     New {@link DukeObject}.
     */
    private void updateContext(Context newContext, DukeObject object) {
        Context oldContext = this.context;
        this.context = newContext;
        this.object = object;
        pcs.firePropertyChange("context", oldContext, this.context);
    }

    /**
     * Gets the printable message that states the current context Dr. Duke is currently in.
     *
     * @param context {@link Context} object.
     * @param obj     Associated {@link DukeObject}.
     * @return Printable message that states the current context Dr. Duke is in.
     */
    private String getViewingStr(Context context, DukeObject obj) {
        if (obj != null) {
            return "You are now viewing '" + obj.getName() + "' in the " + context.toString() + " context";
        } else {
            return "You are now in the HOME context!";
        }
    }

    public Context getContext() {
        return context;
    }

    public DukeObject getObject() {
        return object;
    }
}

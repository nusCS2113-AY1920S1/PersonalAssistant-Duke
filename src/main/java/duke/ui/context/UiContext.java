/* @@author gowgos5 */

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
     * Stores the current context and object, then shows the context associated with a DukeObject.
     *
     * @param obj     DukeObject whose context we wish to view.
     */
    public void open(DukeObject obj) {
        contexts.push(new Pair<>(this.context, this.object));
        openWithoutHistory(obj);
    }

    /**
     * Displays the context associated with a DukeObject.
     *
     * @param obj     DukeObject whose context we wish to view.
     */
    public void openWithoutHistory(DukeObject obj) {
        Context newContext = Context.HOME;
        if (obj != null) {
            obj.update();
            newContext = obj.toContext();
        }

        Context oldContext = this.context;
        this.context = newContext;
        this.object = obj;
        pcs.firePropertyChange("context", oldContext, this.context);
    }

    /**
     * Moves up one in the hierarchy of contexts.
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
            return "You are now in the HOME context!";
        }
    }
}

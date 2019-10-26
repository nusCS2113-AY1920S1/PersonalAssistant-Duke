package duke.ui;

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
     * Sets context for the application. An associated DukeObject should be provided as well.
     * For example, from Home -> Patient, a Patient object should be passed in.
     *
     * @param newContext New context
     * @param object     DukeObject associated with the new object.
     */
    public void setContext(Context newContext, DukeObject object) {
        if (newContext != Context.HOME) {
            contexts.push(new Pair<>(this.context, this.object));
        }

        Context oldContext = this.context;
        this.context = newContext;
        this.object = object;
        pcs.firePropertyChange("context", oldContext, this.context);
    }

    /**
     * Moves up one in the hierarchy of contexts.
     */
    public void moveUpOneContext() throws DukeException {
        if (context == Context.HOME) {
            throw new DukeException("You are already in the Home context.");
        }

        // TODO: find what the next context up is

    }

    /**
     * Moves up one context.
     */
    public void moveBackOneContext() throws DukeException {
        if (context == Context.HOME) {
            throw new DukeException("You are already in the Home context.");
        }

        Pair<Context, DukeObject> pair = contexts.pop();
        setContext(pair.getKey(), pair.getValue());
    }

    public Context getContext() {
        return context;
    }

    public DukeObject getObject() {
        return object;
    }
}

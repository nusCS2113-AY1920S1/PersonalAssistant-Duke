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
        updateContext(newContext, object);
    }

    public void open(DukeObject obj) {
        setContext(obj.toContext(), obj);
    }

    /**
     * Moves up one in the hierarchy of contexts.
     */
    public void moveUpOneContext() throws DukeException {
        if (context == Context.HOME) {
            throw new DukeException("You are already in the Home context.");
        } else if (context == Context.PATIENT) {
            setContext(Context.HOME, null);
        } else if (context == Context.IMPRESSION) {
            setContext(Context.PATIENT, object.getParent());
        } else {
            setContext(Context.IMPRESSION, object.getParent());
        }
    }

    /**
     * Moves back one context.
     */
    public void moveBackOneContext() throws DukeException {
        if (contexts.empty()) {
            throw new DukeException("No previous context before this!");
        }

        Pair<Context, DukeObject> pair = contexts.pop();
        updateContext(pair.getKey(), pair.getValue());
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
}

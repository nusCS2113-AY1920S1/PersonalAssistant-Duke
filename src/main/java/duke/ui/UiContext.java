package duke.ui;

import duke.data.DukeObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * UI context of the application.
 */
public class UiContext {
    private DukeObject object;
    private Context context;
    private PropertyChangeSupport pcs;

    /**
     * Constructs the UI context with HOME as the default value.
     */
    public UiContext() {
        this.context = Context.HOME;
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Adds a listener to listen for changes in {@code context}.
     *
     * @param pcl PropertyChangeListener object.
     */
    public void addListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void setContext(Context newContext, DukeObject object) {
        pcs.firePropertyChange("context", this.context, newContext);
        this.context = newContext;
        this.object = object;
    }

    public Context getContext() {
        return context;
    }

    public DukeObject getObject() {
        return object;
    }
}

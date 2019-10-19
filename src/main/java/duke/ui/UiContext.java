package duke.ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * UI context of the application.
 */
public class UiContext {
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

    public void setContext(Context newContext) {
        pcs.firePropertyChange("context", this.context, newContext);
        this.context = newContext;
    }

    /**
     * Enum defining the types of contexts that {@code context} can take on.
     */
    public enum Context {
        HOME,
        PATIENT,
        TREATMENT,
        EVIDENCE,
        INVESTIGATION
    }
}

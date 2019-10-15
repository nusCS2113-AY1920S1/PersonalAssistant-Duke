package duke.gui;

import duke.DukeCore;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

import static java.util.Objects.requireNonNull;

/**
 * Represents a distinct UI component in the application, e.g. windows, panels, dialogs, etc.
 * It contains a scene graph with a root node of type {@code T}.
 *
 * @param <T> Type.
 */
public abstract class UiComponent<T> {
    /**
     * Resource folder where FXML files are stored.
     */
    private static final String FXML_FILE_FOLDER = "/view/";
    private final FXMLLoader fxmlLoader = new FXMLLoader();

    /**
     * Constructs a UiComponent with the specified FXML file URL.
     * The FXML file need not specify the {@code fx:controller} attribute as it will be specified
     * in {@link #loadFxmlFile(URL, T)}.
     */
    private UiComponent(URL fxmlFileUrl) {
        loadFxmlFile(fxmlFileUrl, null);
    }

    /**
     * Constructs a UiComponent with the specified FXML file URL and root object.
     * The FXML file need not specify the {@code fx:controller} attribute as it will be specified
     * in {@link #loadFxmlFile(URL, T)}.
     */
    private UiComponent(URL fxmlFileUrl, T root) {
        loadFxmlFile(fxmlFileUrl, root);
    }

    /**
     * Constructs a UiComponent using the specified FXML file within {@link #FXML_FILE_FOLDER}.
     *
     * @see #UiComponent(URL)
     */
    public UiComponent(String fxmlFileName) {
        this(getFxmlFileUrl(fxmlFileName));
    }

    /**
     * Constructs a UiComponent with the specified FXML file within {@link #FXML_FILE_FOLDER} and root object.
     *
     * @see #UiComponent(URL, T)
     */
    public UiComponent(String fxmlFileName, T root) {
        this(getFxmlFileUrl(fxmlFileName), root);
    }

    /**
     * Returns the FXML file URL for the specified FXML file name within {@link #FXML_FILE_FOLDER}.
     */
    private static URL getFxmlFileUrl(String fxmlFileName) {
        requireNonNull(fxmlFileName);
        URL fxmlFileUrl = DukeCore.class.getResource(FXML_FILE_FOLDER + fxmlFileName);
        return requireNonNull(fxmlFileUrl);
    }

    /**
     * Loads the object hierarchy from a FXML document.
     *
     * @param location Location of the FXML document.
     * @param root     Specifies the root of the object hierarchy.
     */
    private void loadFxmlFile(URL location, T root) {
        requireNonNull(location);
        fxmlLoader.setLocation(location);
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Returns the root object of the scene graph of this UiComponent.
     */
    public T getRoot() {
        return fxmlLoader.getRoot();
    }
}

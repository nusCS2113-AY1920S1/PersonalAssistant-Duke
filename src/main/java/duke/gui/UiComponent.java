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
 * @param <T> Root node's type.
 */
public abstract class UiComponent<T> {
    private static final String FXML_FILE_FOLDER = "/view/";

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    /**
     * Constructs a UiComponent with the specified FXML file name and root object.
     * The FXML file MUST not specify the {@code fx:controller} attribute as it will be specified
     * in {@link #loadFxmlFile(URL, T)}.
     *
     * @param fxmlFileName Name of FXML file.
     */
    public UiComponent(String fxmlFileName, T root) {
        loadFxmlFile(getFxmlFileUrl(fxmlFileName), root);
    }

    /**
     * Returns the FXML file URL for the specified FXML file name within {@link #FXML_FILE_FOLDER}.
     *
     * @param fxmlFileName Name of FXML file.
     * @return FXML file URL for the FXML file.
     */
    private static URL getFxmlFileUrl(String fxmlFileName) {
        requireNonNull(fxmlFileName);
        URL fxmlFileUrl = DukeCore.class.getResource(FXML_FILE_FOLDER + fxmlFileName);
        return requireNonNull(fxmlFileUrl);
    }

    /**
     * Loads the object hierarchy from a FXML file.
     *
     * @param url  Location of the FXML file.
     * @param root Root of the object hierarchy.
     */
    private void loadFxmlFile(URL url, T root) {
        fxmlLoader.setLocation(url);
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Returns the root object of this UiComponent.
     */
    public T getRoot() {
        return fxmlLoader.getRoot();
    }
}

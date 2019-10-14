package duke.gui;

import duke.DukeGui;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public abstract class UiElement<T> {
    /**
     * Resource folder where FXML files are stored.
     */
    public static final String FXML_FILE_FOLDER = "/view/";
    private final FXMLLoader fxmlLoader = new FXMLLoader();

    /**
     * Constructs a UiElement with the specified FXML file URL.
     * The FXML file must not specify the {@code fx:controller} attribute.
     */
    public UiElement(URL fxmlFileUrl) {
        loadFxmlFile(fxmlFileUrl, null);
    }

    /**
     * Constructs a UiElement using the specified FXML file within {@link #FXML_FILE_FOLDER}.
     *
     * @see #UiElement(URL)
     */
    public UiElement(String fxmlFileName) {
        this(getFxmlFileUrl(fxmlFileName));
    }

    /**
     * Constructs a UiElement with the specified FXML file URL and root object.
     * The FXML file must not specify the {@code fx:controller} attribute.
     */
    public UiElement(URL fxmlFileUrl, T root) {
        loadFxmlFile(fxmlFileUrl, root);
    }

    /**
     * Constructs a UiElement with the specified FXML file within {@link #FXML_FILE_FOLDER} and root object.
     *
     * @see #UiElement(URL, T)
     */
    public UiElement(String fxmlFileName, T root) {
        this(getFxmlFileUrl(fxmlFileName), root);
    }

    /**
     * Returns the FXML file URL for the specified FXML file name within {@link #FXML_FILE_FOLDER}.
     */
    private static URL getFxmlFileUrl(String fxmlFileName) {
        requireNonNull(fxmlFileName);
        String fxmlFileNameWithFolder = FXML_FILE_FOLDER + fxmlFileName;
        URL fxmlFileUrl = DukeGui.class.getResource(fxmlFileNameWithFolder);
        return requireNonNull(fxmlFileUrl);
    }

    /**
     * Returns the root object of the scene graph of this UiElement.
     */
    public T getRoot() {
        return fxmlLoader.getRoot();
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
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(root);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}

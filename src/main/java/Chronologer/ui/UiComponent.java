package chronologer.ui;

import chronologer.ChronologerMain;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;


/**
 * Represents a distinct UI component in the application, e.g. windows, panels, chatbox, etc.
 * 
 * @param <T> Root node's type.
 */
abstract class UiComponent<T> {
    private static final String FXML_FILE_FOLDER = "/view/";

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    /**
     * Constructs a UiComponent with the corresponding FXML file name and root object.
     * The FXML file written should not have a controller attribute as this is handled by the loadFXMLFile.
     *
     * @param fxmlFileName Holds the name of the corresponding FXML file.
     */
    UiComponent(String fxmlFileName, T root) {
        loadFxmlFile(getFxmlFileUrl(fxmlFileName), root);
    }

    /**
     * Loads the object from its corresponding FXML file.
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
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the FXML file's location path for a specified FXML file name.
     * @param fxmlFileName Holds the name of the FXML file.
     * @return FXML file''s location for its corresponding FXML file.
     */
    private static URL getFxmlFileUrl(String fxmlFileName){
        if(fxmlFileName.isEmpty()) {
            System.out.println("fxmlFileName is empty");
        }
        URL fxmlFileUrl = ChronologerMain.class.getResource(FXML_FILE_FOLDER + fxmlFileName);
        return fxmlFileUrl;
    }


    /**
     * Returns the root element of this UiComponent.
     */
    T getRoot() {
        return fxmlLoader.getRoot();
    }
}

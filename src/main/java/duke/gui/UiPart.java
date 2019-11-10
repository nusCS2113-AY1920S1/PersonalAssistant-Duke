package duke.gui;

import duke.Launcher;
import duke.Main;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

//@@author HUANGXUANKUN
/**
 * Represents GUI component with FXML given.
 */
public abstract class UiPart<T> {

    /**
     * Resource folder where FXML files are stored.
     */
    private static final String FXML_FILE_FOLDER = "/view/";

    private final FXMLLoader fxmlLoader = new FXMLLoader();


    /**
     * Constructs a UiPart with a FXML file.
     */
    UiPart(String fxmlFileName, T root) {
        loadFxmlFile(getFxmlFileUrl(fxmlFileName), root);
        System.out.println("For debugging, UiPart " + fxmlFileName + " is loaded.");
    }

    /**
     * Loads the object hierarchy from a FXML document.
     *
     * @param url  Location of the FXML document.
     * @param root Specifies the root of the object hierarchy.
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
     *
     * @param fxmlFileName Name of the fxml file.
     * @return  File's location.
     */
    private static URL getFxmlFileUrl(String fxmlFileName) {
        if (fxmlFileName.isEmpty()) {
            System.out.println(fxmlFileName + " does not exist");
        }
        URL fxmlFileUrl = Main.class.getResource(FXML_FILE_FOLDER + fxmlFileName);
        System.out.println("Loading: " + fxmlFileUrl);
        return fxmlFileUrl;
    }

    /**
     * Returns the root of the scene from the fxml controller extended from this UiPart.
     */
    public T getRoot() {
        return fxmlLoader.getRoot();
    }
}

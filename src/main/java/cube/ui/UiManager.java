package cube.ui;


import javafx.fxml.FXMLLoader;
import cube.MainApp;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class UiManager<Type> {

    /** Resource folder where FXML files are stored. */
    public static final String FXML_FILE_FOLDER = File.separator + "view" + File.separator;

    private FXMLLoader fxmlLoader = new FXMLLoader();

    /**
     * Main Constructor for Root
     * @param FXML File name for the FXML user interface design file.
     * @param root Type of the JavaFX Object type to load.
     */
    public UiManager(String FXML, Type root) {
        super();
        URL fxmlUrl = getFxmlUrl(FXML);
        setRoot(root);
        loadFxmlFile(fxmlUrl);
    }

    /**
     * Secondary Constructor
     * @param FXML File name for the FXML user interface design file.
     */
    public UiManager(String FXML) {
        super();
        URL fxmlUrl = getFxmlUrl(FXML);
        loadFxmlFile(fxmlUrl);
    }

    /**
     * Returns the root object of the FXML Loader.
     */
    public Type getRoot() {
        return fxmlLoader.getRoot();
    }

    /**
     * Sets he root object of the FXML Loader.
     * @param root The root FXML Object to be set.
     */
    private void setRoot(Type root) {
        fxmlLoader.setRoot(root);
    }

    /**
     * Loads the FXML object of the provided type.
     * @param location Location where the FXML files are being stored.
     */
    private void loadFxmlFile(URL location) {
        fxmlLoader.setLocation(location);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the FXML file URL for the specified FXML file name within FXML_FILE_FOLDER.
     */
    private static URL getFxmlUrl(String FXML) {
        String fxmlPath = FXML_FILE_FOLDER + FXML;
        URL fxmlUrl = MainApp.class.getClassLoader().getResource(fxmlPath);

        return fxmlUrl;
    }
}

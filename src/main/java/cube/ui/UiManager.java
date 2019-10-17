package cube.ui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import cube.MainApp;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UiManager<Type> {

    /** Resource folder where FXML files are stored. */
    public static final String FXML_FILE_FOLDER = File.separator + "view" + File.separator;

    private FXMLLoader fxmlLoader = new FXMLLoader();

    public UiManager(String FXML, Type root) {
        super();
        URL fxmlUrl = getFxmlUrl(FXML);
        loadFxmlFile(fxmlUrl, root);
    }

    /**
     * Returns the root object of the scene graph of this UiPart.
     */
    public Type getRoot() {
        return fxmlLoader.getRoot();
    }


    private void loadFxmlFile (URL location, Type root) {
        fxmlLoader.setLocation(location);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(root);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the FXML file URL for the specified FXML file name within {@link #FXML_FILE_FOLDER}.
     */
    private static URL getFxmlUrl(String FXML) {
        String fxmlPath = FXML_FILE_FOLDER + FXML;
        URL fxmlUrl = MainApp.class.getClassLoader().getResource(fxmlPath);

        return fxmlUrl;
    }
}

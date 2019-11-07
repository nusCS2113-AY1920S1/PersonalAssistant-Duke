package cube.ui;


import cube.CubeApp;
import cube.logic.parser.Parser;
import cube.util.LogUtil;
import cube.util.exception.CubeUtilException;
import cube.util.exception.UtilErrorMessage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public abstract class UiManager<Type> {

    /**
     * Resource folder where FXML files are stored.
     */
    public static final String FXML_FILE_FOLDER = "view/";

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private final Logger logger = LogUtil.getLogger(UiManager.class);

    /**
     * Main Constructor for Root
     *
     * @param FXML File name for the FXML user interface design file.
     * @param root Type of the JavaFX Object type to load.
     */
    public UiManager(String FXML, Type root) {
        super();
        try {
            URL fxmlUrl = getFxmlUrl(FXML);
            setRoot(root);
            loadFxmlFile(fxmlUrl);
        } catch (CubeUtilException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Secondary Constructor
     *
     * @param FXML File name for the FXML user interface design file.
     */
    public UiManager(String FXML) {
        super();
        try {
            URL fxmlUrl = getFxmlUrl(FXML);
            loadFxmlFile(fxmlUrl);
        } catch (CubeUtilException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Returns the root object of the FXML Loader.
     */
    public Type getRoot() {
        return fxmlLoader.getRoot();
    }

    /**
     * Sets he root object of the FXML Loader.
     *
     * @param root The root FXML Object to be set.
     */
    private void setRoot(Type root) {
        fxmlLoader.setRoot(root);
    }

    /**
     * Loads the FXML object of the provided type.
     *
     * @param location Location where the FXML files are being stored.
     */
    private void loadFxmlFile(URL location) throws CubeUtilException {
        fxmlLoader.setLocation(location);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new CubeUtilException(UtilErrorMessage.READ_ERROR + location.toString());
        }
    }

    /**
     * Returns the FXML file URL for the specified FXML file name within FXML_FILE_FOLDER.
     */
    private static URL getFxmlUrl(String FXML) {
        String fxmlPath = FXML_FILE_FOLDER + FXML;
        URL fxmlUrl = CubeApp.class.getClassLoader().getResource(fxmlPath);

        return fxmlUrl;
    }
}

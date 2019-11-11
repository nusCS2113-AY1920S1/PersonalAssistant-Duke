/**
 * Manages several GUI related functions such as loading fxml, get root classes etc.
 * Some design has been referenced from Address Book (Level 3).
 * https://github.com/se-edu/addressbook-level3
 */

package cube.ui;


import cube.CubeApp;
import cube.util.LogUtil;
import cube.util.exception.CubeUtilException;
import cube.util.exception.UtilErrorMessage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public abstract class UiManager<T> {

    /**
     * Resource folder where FXML files are stored.
     */
    public static final String FXML_FILE_FOLDER = "view/";

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private final Logger logger = LogUtil.getLogger(UiManager.class);

    /**
     * Main Constructor for Root.
     *
     * @param fxml File name for the FXML user interface design file.
     * @param root Type of the JavaFX Object type to load.
     */
    public UiManager(String fxml, T root) {
        super();
        try {
            URL fxmlUrl = getFxmlUrl(fxml);
            setRoot(root);
            loadFxmlFile(fxmlUrl);
        } catch (CubeUtilException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Secondary Constructor.
     *
     * @param fxml File name for the FXML user interface design file.
     */
    public UiManager(String fxml) {
        super();
        try {
            URL fxmlUrl = getFxmlUrl(fxml);
            loadFxmlFile(fxmlUrl);
        } catch (CubeUtilException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Returns the root object of the FXML Loader.
     */
    public T getRoot() {
        return fxmlLoader.getRoot();
    }

    /**
     * Sets he root object of the FXML Loader.
     *
     * @param root The root FXML Object to be set.
     */
    private void setRoot(T root) {
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
    private static URL getFxmlUrl(String fxml) {
        String fxmlPath = FXML_FILE_FOLDER + fxml;
        URL fxmlUrl = CubeApp.class.getClassLoader().getResource(fxmlPath);

        return fxmlUrl;
    }
}

package cube;

import cube.exception.CubeException;
import cube.storage.StorageManager;
import cube.ui.MainWindow;
import cube.util.FileUtilJson;
import cube.util.LogUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class CubeApp extends Application {

    private StorageManager storageManager;
    private FileUtilJson<StorageManager> storage;
    private final Logger logger = LogUtil.getLogger(CubeApp.class);


    @Override
    public void init() throws Exception {
        super.init();
        logger.info("=============================[ Initializing Cube ]===========================");
        storageManager = new StorageManager();
        storage = new FileUtilJson<>("data", "cube.json", storageManager);

        try {
            storageManager = storage.load();
        } catch (CubeException e) {
            storageManager = new StorageManager();
        }
    }

    @Override
    public void start(Stage stage) {
        MainWindow mwc = new MainWindow(stage, storageManager, storage);

        mwc.initComponents();
        mwc.show();
    }

    @Override
    public void stop() {
        logger.info("=============================[  Exiting Cube  ]==============================");
    }
}

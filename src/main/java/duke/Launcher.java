package duke;

import duke.commons.core.Config;
import duke.commons.core.LogsCenter;
import duke.logic.Logic;
import duke.logic.LogicManager;
import duke.logic.command.exceptions.DataConversionException;
import duke.model.BakingHome;
import duke.model.Model;
import duke.model.ModelManager;
import duke.model.ReadOnlyBakingHome;
import duke.storage.BakingHomeStorage;
import duke.storage.JsonBakingHomeStorage;
import duke.ui.Ui;
import duke.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Launcher class for the app.
 */
public class Launcher extends Application {
    protected Ui ui;
    protected Logic logic;
    protected BakingHomeStorage storage = new JsonBakingHomeStorage(Config.BAKING_HOME_DATA_PATH);
    protected Model model;

    private static final Logger logger = LogsCenter.getLogger(Launcher.class);

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing BakingHome ]===========================");
        super.init();

        model = initModelManager(storage);

        logic = LogicManager.getInstance(model, storage);

        ui = UiManager.getInstance(logic);
    }

    private Model initModelManager(BakingHomeStorage storage) {
        Optional<ReadOnlyBakingHome> bakingHomeOptional;
        ReadOnlyBakingHome initialData = new BakingHome();

        try {
            bakingHomeOptional = storage.readBakingHome();
            if (bakingHomeOptional.isEmpty()) {
                logger.info("Data file not found.");
            } else {
                initialData = bakingHomeOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file");
        }

        return new ModelManager(initialData);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting BakingHome");
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(Launcher.class, args);
    }
}

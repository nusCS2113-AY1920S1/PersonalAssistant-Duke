package duke;

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
import duke.storage.Storage;
import duke.ui.Ui;
import duke.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(Launcher.class, args);
    }

    private static final Logger logger = LogsCenter.getLogger(Launcher.class);

    protected Ui ui;
    protected Logic logic;
    protected BakingHomeStorage storage = new JsonBakingHomeStorage(Paths.get("data", "baking.json"));
    protected Model model;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing BakingHome ]===========================");
        super.init();

        model = initModelManager(storage);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
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
                  System.out.println("hehe");
                  System.out.println(initialData.getInventoryList().get(0).getItem().name);
              }
          } catch (DataConversionException e) {
            e.printStackTrace();
              logger.warning("Data file not in the correct format");
          } catch (IOException e) {
            e.printStackTrace();
              logger.warning("Problem while reading from the file");
        }

        return new ModelManager(initialData);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting BakingHome");
        ui.start(primaryStage);
    }

}

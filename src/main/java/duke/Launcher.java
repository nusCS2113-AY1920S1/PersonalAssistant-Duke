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
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.Objects;
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
        ReadOnlyBakingHome initialData;
        try {
            bakingHomeOptional = storage.readBakingHome();
            if (bakingHomeOptional.isEmpty()) {
                logger.info("Data file not found. Using demo data");
                initialData = getDemoBakingHome();
            } else {
                initialData = bakingHomeOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Using demo data.");
            initialData = getDemoBakingHome();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Using demo data.");
            initialData = getDemoBakingHome();
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

    private ReadOnlyBakingHome getDemoBakingHome() {
        Optional<ReadOnlyBakingHome> demoBakingHome;
        try {
            demoBakingHome = storage.readBakingHome(getDemoFilePath());
        } catch (IOException | URISyntaxException | DataConversionException e) {
            logger.warning("Problem while getting demo resources. Using empty data.");
            return new BakingHome();
        }

        if (demoBakingHome.isEmpty()) {
            logger.warning("Problem while loading demo file. Using empty data.");
            return new BakingHome();
        }

        return demoBakingHome.get();
    }

    private Path getDemoFilePath() throws IOException, URISyntaxException {
        URI demoUri = Objects.requireNonNull(getClass()
            .getClassLoader()
            .getResource("data/demo.json"))
            .toURI();

        //@@author liujiajun-reused
        /*
         * The following is a workaround for the error
         * "java.nio.file.FileSystemNotFoundException" when trying to access files in jar.
         * Adapted from
         * stackoverflow.com/questions/22605666/
         * java-access-files-in-jar-causes-java-nio-file-filesystemnotfoundexception.
         */

        if ("jar".equals(demoUri.getScheme())) {
            for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
                if (provider.getScheme().equalsIgnoreCase("jar")) {
                    try {
                        provider.getFileSystem(demoUri);
                    } catch (FileSystemNotFoundException e) {
                        // in this case we need to initialize it first:
                        provider.newFileSystem(demoUri, Collections.emptyMap());
                    }
                }
            }
        }

        //@@author

        return Paths.get(demoUri);
    }
}

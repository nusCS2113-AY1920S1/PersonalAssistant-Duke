package duke;

import duke.commons.core.LogsCenter;
import duke.logic.Logic;
import duke.logic.LogicManager;
import duke.model.BakingHome;
import duke.model.Model;
import duke.model.ModelManager;
import duke.model.ReadOnlyBakingHome;
import duke.storage.Storage;
import duke.ui.Ui;
import duke.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.logging.Logger;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(Launcher.class, args);
    }

    private static final Logger logger = LogsCenter.getLogger(Launcher.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing BakingHome ]===========================");
        super.init();

        model = initModelManager(storage);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    private Model initModelManager(Storage storage) {
        Optional<ReadOnlyBakingHome> bakingHomeOptional;
        ReadOnlyBakingHome initialData = new BakingHome();
        //TODO: Read from storage.
        //        try {
//            addressBookOptional = storage.readAddressBook();
//            if (!addressBookOptional.isPresent()) {
//                logger.info("Data file not found. Will be starting with a sample AddressBook");
//            }
//            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
//        } catch (DataConversionException e) {
//            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
//            initialData = new AddressBook();
//        } catch (IOException e) {
//            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
//            initialData = new AddressBook();
//        }

        return new ModelManager(initialData);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting BakingHome");
        ui.start(primaryStage);
    }

}

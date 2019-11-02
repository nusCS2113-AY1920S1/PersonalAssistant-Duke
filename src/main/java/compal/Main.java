package compal;

import compal.commons.LogUtils;
import compal.logic.LogicManager;
import compal.logic.command.CommandResult;
import compal.logic.command.ListCommand;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.ui.Ui;
import compal.ui.UiManager;
import compal.ui.UiUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.logging.Logger;

/**
 * Initializes GUI.
 */
public class Main extends Application {

    private Ui ui;
    private UiUtil uiUtil;
    private LogicManager logicManager;
    private static final Logger logger = LogUtils.getLogger(Main.class);

    /**
     * Constructs a new Main object.
     */
    public Main() {
        this.uiUtil = new UiUtil();
        this.ui = new UiManager(uiUtil);
        this.logicManager = new LogicManager();
    }

    public static void main(String[] args) {
        logger.info("Starting COMPal");
        launch(args);
    }

    /**
     * Initializes and sets up the GUI.
     *
     * @param primaryStage The stage for GUI.
     */
    @Override
    public void start(Stage primaryStage) throws ParseException, ParserException, CommandException {
        ui.start(primaryStage);
        CommandResult cmdResult = logicManager.logicExecute("list");

        if (ListCommand.LIST_EMPTY.equals(cmdResult.feedbackToUser)) {
            uiUtil.printg("Hello there!\n\n" + cmdResult.feedbackToUser);
        } else {
            cmdResult = logicManager.logicExecute("view week");
            uiUtil.printg("Hello there!\n\n" + cmdResult.feedbackToUser);
        }
    }
}

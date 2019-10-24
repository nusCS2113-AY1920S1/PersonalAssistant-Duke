package compal;

import compal.logic.LogicManager;
import compal.logic.command.CommandResult;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.ui.Ui;
import compal.ui.UiManager;
import compal.ui.UiUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.text.ParseException;

/**
 * Initializes GUI.
 */
public class Main extends Application {

    private Ui ui;
    private UiUtil uiUtil;
    LogicManager logicManager;

    /**
     * Constructs a new Main object.
     */
    public Main() {
        this.uiUtil = new UiUtil();
        this.ui = new UiManager(uiUtil);
        this.logicManager = new LogicManager();
    }

    public static void main(String[] args) {
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
        CommandResult cmdResult = logicManager.logicExecute("view week");
        uiUtil.printg("Hello there!\n" + cmdResult.feedbackToUser);
    }
}

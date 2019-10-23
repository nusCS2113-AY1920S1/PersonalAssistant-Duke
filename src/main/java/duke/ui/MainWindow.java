package duke.ui;

import com.jfoenix.controls.JFXButton;
import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.CommandResult;
import duke.logic.Logic;
import duke.logic.util.InputHistory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class MainWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(MainWindow.class);

    private static final String FXML_FILE_NAME = "MainWindow.fxml";

    private Stage primaryStage;
    private Logic logic;

    private ExpensePane expensePane;
    private TrendingPane trendingPane;

    // todo: create controller for trendingPage;

    private InputHistory inputHistory;


    private PlanPane planPane;

    private CommandResult.DisplayedPane displayedPane;
    /* todo: create controller for trendingPage;
    private TrendingPage trendingPage;
     */

    // The area that can be switched.
    @FXML
    private Label boardTitle;

    @FXML
    private AnchorPane commonBoard;

    // TextInput and TextOutput
    @FXML
    private Label console;

    @FXML
    private TextField userInput;

    // Duke++ Menu
    @FXML
    private JFXButton expenseButton;

    @FXML
    private JFXButton incomeButton;

    @FXML
    private JFXButton loanButton;

    @FXML
    private JFXButton trendingButton;

    @FXML
    private JFXButton planButton;

    // Utilities Menu
    @FXML
    private JFXButton tagsButton;

    public void show() {
        primaryStage.show();
    }

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML_FILE_NAME, primaryStage);
        this.primaryStage = primaryStage;
        this.logic = logic;

        inputHistory = new InputHistory();

    }

    public void fillInnerPart() {
        expensePane = new ExpensePane(logic.getExternalExpenseList());
        logger.info("The filled externalList length " + logic.getExternalExpenseList().size());
        trendingPane = new TrendingPane();
        logger.info("trendingPane is constructed.");
        planPane = new PlanPane(logic.getDialogObservableList());
        logger.info("planPane is constructed." + logic.getDialogObservableList().size());
        // todo: add more data parts to be added.

    }

    @FXML
    private void handleUserInput() {
        String inputString = userInput.getText();
        try {
            CommandResult commandResult;

            if (displayedPane == CommandResult.DisplayedPane.PLAN && !inputString.contains("goto") && !inputString.contains("bye")){
                commandResult = logic.execute("plan " + inputString);
            } else {
                commandResult = logic.execute(inputString);
            }
            console.setText(commandResult.getConsoleInfo());
            fillInnerPart();
            showPane(commandResult);

            if (commandResult.isExit()) Platform.exit();

        } catch (DukeException e) {
            console.setText(e.getMessage());
        }

        inputHistory.add(inputString);
        // logger.info("New Input has been stored.");
        userInput.clear();
    }

    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) {
        logger.info("Key Press detected!");
        switch (keyEvent.getCode()) {
            case UP:
                if(inputHistory.isAbleToLast()) {
                    userInput.setText(inputHistory.getLastInput());
                }
                break;

            case DOWN:
                if(inputHistory.isAbleToNext()) {
                    userInput.setText(inputHistory.getNextInput());
                }
                break;
        }
    }


    private void showPane(CommandResult commandResult) {
        switch (commandResult.getDisplayedPane()) {
        case EXPENSE:
            showExpensePane();
            displayedPane = CommandResult.DisplayedPane.EXPENSE;
            break;

        case TRENDING:
            showTrendingPane();
            displayedPane = CommandResult.DisplayedPane.TRENDING;
            break;

        case PLAN:
            showPlanPane();
            displayedPane = CommandResult.DisplayedPane.PLAN;
            break;
        default:
            break;
        }
    }

    private void showExpensePane() {
        commonBoard.getChildren().clear();
        commonBoard.getChildren().add(expensePane.getRoot());
        //commonBoard.getChildren().add(pieChartSample.getRoot());

        expenseButton.setButtonType(JFXButton.ButtonType.RAISED);
        incomeButton.setButtonType(JFXButton.ButtonType.FLAT);
        loanButton.setButtonType(JFXButton.ButtonType.FLAT);
        trendingButton.setButtonType(JFXButton.ButtonType.FLAT);
        tagsButton.setButtonType(JFXButton.ButtonType.FLAT);
        planButton.setButtonType(JFXButton.ButtonType.FLAT);

        boardTitle.setText("Expenses");
    }

    private void showTrendingPane() {
        commonBoard.getChildren().clear();
        commonBoard.getChildren().add(trendingPane.getRoot());

        expenseButton.setButtonType(JFXButton.ButtonType.FLAT);
        incomeButton.setButtonType(JFXButton.ButtonType.FLAT);
        loanButton.setButtonType(JFXButton.ButtonType.FLAT);
        trendingButton.setButtonType(JFXButton.ButtonType.RAISED);
        tagsButton.setButtonType(JFXButton.ButtonType.FLAT);
        planButton.setButtonType(JFXButton.ButtonType.FLAT);


        boardTitle.setText("Trending");
    }


    private void showPlanPane() {
        commonBoard.getChildren().clear();
        commonBoard.getChildren().add(planPane.getRoot());

        expenseButton.setButtonType(JFXButton.ButtonType.FLAT);
        incomeButton.setButtonType(JFXButton.ButtonType.FLAT);
        loanButton.setButtonType(JFXButton.ButtonType.FLAT);
        trendingButton.setButtonType(JFXButton.ButtonType.FLAT);
        tagsButton.setButtonType(JFXButton.ButtonType.FLAT);
        planButton.setButtonType(JFXButton.ButtonType.RAISED);

        boardTitle.setText("Plan");
    }


}

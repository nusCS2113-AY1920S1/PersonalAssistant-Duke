package duke.ui;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.CommandResult;
import duke.logic.Logic;
import duke.logic.util.AutoCompleter;
import duke.logic.util.InputHistory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class MainWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(MainWindow.class);

    private static final String FXML_FILE_NAME = "MainWindow.fxml";

    private Stage primaryStage;
    private Logic logic;

    private AutoCompleter autoCompleter;

    private ExpensePane expensePane;
    private TrendingPane trendingPane;
    private BudgetPane budgetPane;

    // todo: create controller for trendingPage;

    private InputHistory inputHistory;


    private PlanPane planPane;

    private CommandResult.DisplayedPane displayedPane;
    /* todo: create controller for trendingPage;
    private TrendingPage trendingPage;
     */

    // The area that can be switched.
    @FXML
    private StackPane paneStack;

    // TextInput and TextOutput
    @FXML
    private Label console;

    @FXML
    private TextField userInput;

    public void show() {
        primaryStage.show();
    }

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML_FILE_NAME, primaryStage);
        this.primaryStage = primaryStage;
        this.logic = logic;

        displayedPane = CommandResult.DisplayedPane.EXPENSE;
        fillInnerPart();

        inputHistory = new InputHistory();
        autoCompleter = new AutoCompleter();

        this.userInput.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                autoCompleter.receiveText(userInput.getText());
                String fullSuggestion = autoCompleter.getFullComplement();
                userInput.setText(fullSuggestion);
                userInput.positionCaret(userInput.getText().length());
                event.consume();
                logger.info("Autocomplete finish");
            }
        });
    }

    private void fillInnerPart() {
        expensePane = new ExpensePane(logic.getExternalExpenseList(),logic);
        logger.info("The filled externalList length " + logic.getExternalExpenseList().size());
        trendingPane = new TrendingPane();
        logger.info("trendingPane is constructed.");
        planPane = new PlanPane(logic.getDialogObservableList());
        logger.info("planPane is constructed." + logic.getDialogObservableList().size());
        budgetPane = new BudgetPane(logic.getExternalIncomeList());
        logger.info("Budget plane is constructed.");

        expensePane.getRoot().setVisible(false);
        planPane.getRoot().setVisible(false);
        trendingPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(false);

        paneStack.getChildren().clear();
        paneStack.getChildren().add(expensePane.getRoot());
        paneStack.getChildren().add(planPane.getRoot());
        paneStack.getChildren().add(trendingPane.getRoot());
        paneStack.getChildren().add(budgetPane.getRoot());

        // this part should be unnecessary
        switch (displayedPane) {
            case EXPENSE:
                expensePane.getRoot().setVisible(true);
                break;

            case TRENDING:
                trendingPane.getRoot().setVisible(true);
                break;

            case PLAN:
                planPane.getRoot().setVisible(true);
                break;

            case BUDGET:
                budgetPane.getRoot().setVisible(true);
                break;
        }

        // todo: add more data parts to be added.
    }

    @FXML
    private void handleUserInput() {
        String inputString = userInput.getText();
        try {
            CommandResult commandResult;

            if (displayedPane == CommandResult.DisplayedPane.PLAN
                    && !inputString.contains("goto")
                    && !inputString.contains("bye")) {
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
        displayedPane = commandResult.getDisplayedPane();
        switch (displayedPane) {
        case EXPENSE:
            showExpensePane();
            break;

        case TRENDING:
            showTrendingPane();
            break;

        case PLAN:
            showPlanPane();
            break;

        case BUDGET:
            showBudgetPane();
            break;

        default:
            break;
        }
    }

    private void showExpensePane() {
        expensePane.getRoot().setVisible(true);
        planPane.getRoot().setVisible(false);
        trendingPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(false);
    }

    private void showTrendingPane() {
        expensePane.getRoot().setVisible(false);
        planPane.getRoot().setVisible(false);
        trendingPane.getRoot().setVisible(true);
        budgetPane.getRoot().setVisible(false);
    }


    private void showPlanPane() {
        expensePane.getRoot().setVisible(false);
        planPane.getRoot().setVisible(true);
        trendingPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(false);
    }

    private void showBudgetPane() {
        expensePane.getRoot().setVisible(false);
        planPane.getRoot().setVisible(false);
        trendingPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(true);
    }

}

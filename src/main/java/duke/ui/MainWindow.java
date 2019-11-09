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
    private InputHistory inputHistory;

    private ExpensePane expensePane;
    private TrendingPane trendingPane;
    private PaymentPane paymentPane;
    private BudgetPane budgetPane;
    private PlanPane planPane;

    private CommandResult.DisplayedPane displayedPane;

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

    /**
     * Constructor for controller of the mainWindow.
     * @param primaryStage Stage of Duke
     * @param logic Logic object of duke
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML_FILE_NAME, primaryStage);
        this.primaryStage = primaryStage;
        this.logic = logic;

        displayedPane = CommandResult.DisplayedPane.EXPENSE;
        if (logic.getExternalExpenseList().isEmpty()) {
            //initial boot
            displayedPane = CommandResult.DisplayedPane.PLAN;
        }
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
        expensePane = new ExpensePane(logic.getExternalExpenseList(),
                logic ,
                logic.getExpenseListTotalString(),
                logic.getFilterCriteriaString(),
                logic.getSortCriteriaString(),
                logic.getViewCriteriaString());
        logger.info("The filled externalList length " + logic.getExternalExpenseList().size());
        trendingPane = new TrendingPane();
        logger.info("trendingPane is constructed.");
        planPane = new PlanPane(logic.getDialogObservableList());
        logger.info("planPane is constructed." + logic.getDialogObservableList().size());
        budgetPane = new BudgetPane(logic.getExternalIncomeList(),logic);
        paymentPane = new PaymentPane(logic.getFilteredPaymentList(),
                logic.getPaymentSortingCriteria(),
                logic.getPaymentPredicate());
        logger.info("Budget plane is constructed.");


        expensePane.getRoot().setVisible(false);
        planPane.getRoot().setVisible(false);
        trendingPane.getRoot().setVisible(false);
        paymentPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(false);


        paneStack.getChildren().clear();
        paneStack.getChildren().add(expensePane.getRoot());
        paneStack.getChildren().add(planPane.getRoot());
        paneStack.getChildren().add(trendingPane.getRoot());
        paneStack.getChildren().add(paymentPane.getRoot());
        paneStack.getChildren().add(budgetPane.getRoot());


        // this part should be unnecessary
        switch (displayedPane) {
        case TRENDING:
            trendingPane.getRoot().setVisible(true);
            break;

        case PLAN:
            planPane.getRoot().setVisible(true);
            break;

        case BUDGET:
            budgetPane.getRoot().setVisible(true);
            break;

        case PAYMENT:
            paymentPane.getRoot().setVisible(true);

        default: //Expense pane
            expensePane.getRoot().setVisible(true);

        }
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

            if (commandResult.isExit()) {
                Platform.exit();
            }
        } catch (DukeException e) {
            console.setText(e.getMessage());
        }

        inputHistory.add(inputString);
        userInput.clear();
    }

    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            userInput.setText(inputHistory.getLastInput());
            break;

        case DOWN:
            userInput.setText(inputHistory.getNextInput());
            break;

        default:
            // other key events will be ignored.
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

        case PAYMENT:
            showPaymentPane();
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
        paymentPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(false);
    }

    private void showTrendingPane() {
        expensePane.getRoot().setVisible(false);
        planPane.getRoot().setVisible(false);
        trendingPane.getRoot().setVisible(true);
        paymentPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(false);
    }


    private void showPlanPane() {
        expensePane.getRoot().setVisible(false);
        planPane.getRoot().setVisible(true);
        trendingPane.getRoot().setVisible(false);
        paymentPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(false);
    }

    private void showBudgetPane() {
        expensePane.getRoot().setVisible(false);
        planPane.getRoot().setVisible(false);
        trendingPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(true);
        paymentPane.getRoot().setVisible(false);
    }

    private void showPaymentPane() {
        expensePane.getRoot().setVisible(false);
        planPane.getRoot().setVisible(false);
        trendingPane.getRoot().setVisible(false);
        budgetPane.getRoot().setVisible(false);
        paymentPane.getRoot().setVisible(true);
    }


}

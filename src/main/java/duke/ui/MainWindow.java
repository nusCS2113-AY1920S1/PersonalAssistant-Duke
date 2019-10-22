package duke.ui;

import com.jfoenix.controls.JFXButton;
import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.CommandResult;
import duke.logic.Logic;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class MainWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(MainWindow.class);

    private static final String FXML_FILE_NAME = "MainWindow.fxml";
    private Stage primaryStage;
    private Logic logic;

    private ExpensePane expensePane;
    private PieChartSample pieChartSample;
    private TrendingPane trendingPane;
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
    }

    public void fillInnerPart() {
        pieChartSample = new PieChartSample(logic);
        expensePane = new ExpensePane(logic.getExternalExpenseList());
        logger.info("The filled externalList length " + logic.getExternalExpenseList().size());
        trendingPane = new TrendingPane();
        logger.info("trendingPane is constructed.");
        // todo: add more data parts to be added.
    }


    @FXML
    private void handleUserInput() {
        String inputString = userInput.getText();

        try {
            CommandResult commandResult = logic.execute(inputString);
            console.setText(commandResult.getConsoleInfo());
            showPane(commandResult);

            if(commandResult.isExit()) Platform.exit();
        } catch(DukeException e) {
            console.setText(e.getMessage());
        }

        userInput.clear();
    }

    private void showPane(CommandResult commandResult) {
        switch (commandResult.getDisplayedPane()) {
            case EXPENSE:
                showExpensePane();
                break;

            case TRENDING:
                showTrendingPane();
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

        boardTitle.setText("Trending");
    }
}

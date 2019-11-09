package duke.ui;

import duke.commons.LogsCenter;
import duke.logic.Logic;
import duke.model.Income;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.util.logging.Logger;

public class BudgetPane extends UiPart<AnchorPane>  {

    private static final Logger logger = LogsCenter.getLogger(BudgetPane.class);

    private static final String FXML_FILE_NAME = "BudgetPane.fxml";

    @FXML
    private
    ListView<Income> incomeListView;

    @FXML
    private
    Label incomeLabel;

    @FXML
    Label totalIncomeLabel;

    @FXML
    private
    Pane paneView;

    @FXML
    private
    Pane paneBudgetView;

    @FXML
    private
    ListView<String> budgetListView;

    public Logic logic;

    BudgetPane(ObservableList<Income> incomeList, Logic logic, StringProperty totalIncome) {
        super(FXML_FILE_NAME, null);
        logger.info("incomeList has length " + incomeList.size());
        Label emptyIncomeListPlaceholder = new Label();
        emptyIncomeListPlaceholder.setText("No income entered yet! " +
                "Type \"addIncome #amount /d #source\" to add.");
        emptyIncomeListPlaceholder.setWrapText(true);
        emptyIncomeListPlaceholder.setTextAlignment(TextAlignment.CENTER);
        incomeListView.setPlaceholder(emptyIncomeListPlaceholder);
        incomeListView.setItems(incomeList);
        logger.info("Items are set.");
        incomeListView.setCellFactory(incomeListView -> new IncomeListViewCell());
        logger.info("cell factory is set.");
        incomeLabel.setText("Income");
        incomeLabel.setStyle("-fx-text-fill:gold; -fx-font-size: 20px;");

        this.logic = logic;

        Text text = new Text();
        ProgressBar overallBudget = new ProgressBar();
        double percent = logic.getTotalAmount().doubleValue()/logic.getMonthlyBudget().doubleValue();
        String remaining = logic.getRemaining(logic.getTotalAmount()).toString();
        overallBudget.setProgress(percent);
        text.setText("Remaining: $" + remaining);
        text.setStyle("-fx-font-size: 20px;");
        if(percent > 0.9) {
            overallBudget.setStyle("-fx-accent: red;");
        } else if (percent > 0.65) {
            overallBudget.setStyle("-fx-accent: orange;");
        } else if (percent > 0.40) {
            overallBudget.setStyle("-fx-accent: yellow");
        } else {
            overallBudget.setStyle("-fx-accent: green");
        }
        overallBudget.setLayoutX(150);
        overallBudget.setPrefWidth(500);
        overallBudget.setPrefHeight(30);
        text.setLayoutX(300);
        text.setLayoutY(60);
        paneView.getChildren().clear();
        paneView.getChildren().add(overallBudget);
        paneView.getChildren().add(text);

        paneBudgetView.getChildren().clear();
        paneBudgetView.getChildren().add(new BudgetBar(logic).getRoot());

        budgetListView.setItems(logic.getBudgetObservableList());
        totalIncomeLabel.textProperty().bindBidirectional(totalIncome);
    }

    class IncomeListViewCell extends ListCell<Income> {
        @Override
        protected void updateItem(Income income, boolean empty) {
            super.updateItem(income, empty);
            if (empty || income == null) {
                setGraphic(null);
                setText(null);
            } else {
                int index = incomeListView.getItems().indexOf(income) + 1;
                setGraphic(new IncomeCard(income, index).getRoot());
            }
        }
    }
}

package duke.ui;

import duke.commons.LogsCenter;
import duke.logic.Logic;
import duke.model.BudgetView;
import duke.model.Income;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import javax.swing.*;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class BudgetPane extends UiPart<AnchorPane>  {

    private static final Logger logger = LogsCenter.getLogger(BudgetPane.class);

    private static final String FXML_FILE_NAME = "BudgetPane.fxml";

    @FXML
    private
    ListView<Income> incomeListView;

    @FXML
    Pane paneView;

    @FXML
    Pane paneBudgetView;

    @FXML
    //ListView<String> budgetListView;

    public Logic logic;

    public BudgetPane(ObservableList<Income> incomeList, Logic logic) {
        super(FXML_FILE_NAME, null);
        logger.info("incomeList has length " + incomeList.size());
        Label emptyIncomeListPlaceholder = new Label();
        emptyIncomeListPlaceholder.setText("No Income yet. " +
                "Type \"addIncome #amount\" to add one!");
        incomeListView.setPlaceholder(emptyIncomeListPlaceholder);
        incomeListView.setItems(incomeList);
        logger.info("Items are set.");
        incomeListView.setCellFactory(incomeListView -> new IncomeListViewCell());
        logger.info("cell factory is set.");

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
        text.setLayoutY(50);
        paneView.getChildren().clear();
        paneView.getChildren().add(overallBudget);
        paneView.getChildren().add(text);

        paneBudgetView.getChildren().clear();
        paneBudgetView.getChildren().add(new BudgetBar(logic).getRoot());

        //budgetListView.setItems(logic.getBudgetObservableList());
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

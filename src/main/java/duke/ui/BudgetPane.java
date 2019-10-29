package duke.ui;

import duke.commons.LogsCenter;
import duke.logic.Logic;
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
    TableView incomeTableView;

    @FXML
    Pane paneView;

    @FXML
    GridPane gridPane;

    public Logic logic;
    public Set<String> tags;

    public BudgetPane(ObservableList<Income> incomeList, Logic logic) {
        super(FXML_FILE_NAME, null);
        logger.info("incomeList has length " + incomeList.size());
        incomeTableView.getItems().clear();
        incomeTableView.setPlaceholder(new Label("No incomes to display!"));
        TableColumn<Income, Void> indexColumn = new TableColumn<>("No.");
        indexColumn.setCellFactory(col -> {
            TableCell<Income, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });
        indexColumn.setSortable(false);
        indexColumn.setResizable(false);
        indexColumn.setReorderable(false);
        indexColumn.prefWidthProperty().bind(incomeTableView.widthProperty().multiply(0.15));

        TableColumn<String, Income> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setSortable(false);
        amountColumn.setResizable(false);
        amountColumn.setReorderable(false);
        amountColumn.prefWidthProperty().bind(incomeTableView.widthProperty().multiply(0.25));

        TableColumn<Income, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setSortable(false);
        descriptionColumn.setResizable(false);
        descriptionColumn.setReorderable(false);
        descriptionColumn.prefWidthProperty().bind(incomeTableView.widthProperty().multiply(0.6));

        incomeTableView.setRowFactory(new Callback<TableView<Income>, TableRow<Income>>() {
            @Override
            public TableRow<Income> call(TableView<Income> tableView) {
                final TableRow<Income> row = new TableRow<Income>() {
                    @Override
                    protected void updateItem(Income income, boolean empty) {
                        super.updateItem(income, empty);
                        if(empty) {
                            setGraphic(null);
                            setStyle("-fx-background-color: white");
                        } else {
                            setStyle("-fx-text-background-color: black;");
                        }
                    }
                };
                return row;
            }
        });
        incomeTableView.getColumns().setAll(
                indexColumn,
                descriptionColumn,
                amountColumn
        );
        logger.info("Items are set.");
        for (Income income : incomeList) {
            incomeTableView.getItems().add(income);
        }
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


        ProgressBar view1 = new ProgressBar();
        String category = logic.getBudgetViewCategory().get(1);
        logger.info(category);
        //error is here, keeps throwing null pointer exception
        double percent1 = logic.getBudgetTag(category).doubleValue();
        //double percent1 = (logic.getTagAmount(category).doubleValue()) / (logic.getBudgetTag(category).doubleValue());
        view1.setProgress(percent1);
        gridPane.add(view1,1,1);


    }
}

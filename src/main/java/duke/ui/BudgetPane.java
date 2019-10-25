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
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.util.Set;
import java.util.logging.Logger;

public class BudgetPane extends UiPart<AnchorPane>  {

    private static final Logger logger = LogsCenter.getLogger(BudgetPane.class);

    private static final String FXML_FILE_NAME = "BudgetPane.fxml";

    @FXML
    private Pane paneView;
    private PieChart pieChartSample;

    @FXML
    TableView incomeTableView;

    public Logic logic;
    public Set<String> tags;

    public BudgetPane(ObservableList<Income> incomeList) {
        super(FXML_FILE_NAME, null);
        logger.info("incomeList has length " + incomeList.size());
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
        TableColumn<String, Income> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        timeColumn.setSortable(false);
        TableColumn<String, Income> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setSortable(false);
        TableColumn<Income, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setSortable(false);
        incomeTableView.setRowFactory(new Callback<TableView<Income>, TableRow<Income>>() {
            @Override
            public TableRow<Income> call(TableView<Income> tableView) {
                final TableRow<Income> row = new TableRow<Income>() {
                    @Override
                    protected void updateItem(Income income, boolean empty) {
                        super.updateItem(income, empty);
                        setStyle("-fx-text-background-color: black;");
                    }
                };
                return row;
            }
        });
        incomeTableView.getColumns().setAll(
                indexColumn,
                timeColumn,
                amountColumn,
                descriptionColumn
        );
        logger.info("Items are set.");
        for (Income income : incomeList) {
            incomeTableView.getItems().add(income);
        }
        logger.info("cell factory is set.");

        this.logic = logic;
    }
}

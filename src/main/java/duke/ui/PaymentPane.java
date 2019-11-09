package duke.ui;

import duke.commons.LogsCenter;
import duke.model.payment.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.function.Predicate;
import java.util.logging.Logger;

public class PaymentPane extends UiPart<AnchorPane> {

    private static final Logger logger = LogsCenter.getLogger(PaymentPane.class);

    private static final double FULL_OPACITY = 1;
    private static final double FADED_OPACITY = 0.2;

    private static final String FXML_FILE_NAME = "PaymentPane.fxml";

    private StringProperty sortingCriteria;

    private ObjectProperty<Predicate> predicate;

    // private ObservableList<String> searchKeywordIndicator;

    @FXML
    private Label overdueLabel;

    @FXML
    private Label weekLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label allLabel;

    @FXML
    private Label searchLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private ListView<Payment> paymentListView;

    public PaymentPane(ObservableList<Payment> paymentList,
                       StringProperty sortingCriteria,
                       ObjectProperty<Predicate> predicate) {
        super(FXML_FILE_NAME, null);
        paymentListView.setItems(paymentList);
        paymentListView.setCellFactory(listView -> new PaymentListViewCell());

        this.sortingCriteria = sortingCriteria;
        this.sortingCriteria.addListener((observable, oldValue, newValue) -> {
            highlightSortLabel();
        });

        this.predicate = predicate;
        this.predicate.addListener((observable, oldValue, newValue) -> {
            highlightPredicateLabel();
        });

        highlightSortLabel();
        highlightPredicateLabel();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Payment} using a {@code PaymentBox}.
     */
    class PaymentListViewCell extends ListCell<Payment> {
        @Override
        protected void updateItem(Payment payment, boolean empty) {
            super.updateItem(payment, empty);

            if (empty || payment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PaymentBox(payment, getIndex() + 1).getRoot());
            }
        }
    }

    private void highlightSortLabel() {
        switch (sortingCriteria.getValue()) {
        case "time":
            timeLabel.setOpacity(FULL_OPACITY);
            amountLabel.setOpacity(FADED_OPACITY);
            priorityLabel.setOpacity(FADED_OPACITY);
            break;

        case "amount":
            timeLabel.setOpacity(FADED_OPACITY);
            amountLabel.setOpacity(FULL_OPACITY);
            priorityLabel.setOpacity(FADED_OPACITY);
            break;

        case "priority":
            timeLabel.setOpacity(FADED_OPACITY);
            amountLabel.setOpacity(FADED_OPACITY);
            priorityLabel.setOpacity(FULL_OPACITY);
            break;
        }
    }

    private void highlightPredicateLabel() {
        if (predicate.getValue() instanceof PaymentOverduePredicate) {
            overdueLabel.setOpacity(FULL_OPACITY);
            weekLabel.setOpacity(FADED_OPACITY);
            monthLabel.setOpacity(FADED_OPACITY);
            allLabel.setOpacity(FADED_OPACITY);
            searchLabel.setOpacity(FADED_OPACITY);

        } else if (predicate.getValue() instanceof PaymentInWeekPredicate) {
            overdueLabel.setOpacity(FADED_OPACITY);
            weekLabel.setOpacity(FULL_OPACITY);
            monthLabel.setOpacity(FADED_OPACITY);
            allLabel.setOpacity(FADED_OPACITY);
            searchLabel.setOpacity(FADED_OPACITY);

        } else if (predicate.getValue() instanceof PaymentInMonthPredicate) {
            overdueLabel.setOpacity(FADED_OPACITY);
            weekLabel.setOpacity(FADED_OPACITY);
            monthLabel.setOpacity(FULL_OPACITY);
            allLabel.setOpacity(FADED_OPACITY);
            searchLabel.setOpacity(FADED_OPACITY);

        } else if (predicate.getValue() instanceof SearchKeywordPredicate) {
            overdueLabel.setOpacity(FADED_OPACITY);
            weekLabel.setOpacity(FADED_OPACITY);
            monthLabel.setOpacity(FADED_OPACITY);
            allLabel.setOpacity(FADED_OPACITY);
            searchLabel.setOpacity(FULL_OPACITY);

        } else {
            overdueLabel.setOpacity(FADED_OPACITY);
            weekLabel.setOpacity(FADED_OPACITY);
            monthLabel.setOpacity(FADED_OPACITY);
            allLabel.setOpacity(FULL_OPACITY);
            searchLabel.setOpacity(FADED_OPACITY);
        }
    }

}

package duke.ui;

import duke.commons.LogsCenter;
import duke.model.payment.*;
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

    private static final String FXML_FILE_NAME = "PaymentPane.fxml";

    private ObservableList<String> sortIndicator;

    private ObservableList<Predicate<Payment>> predicateIndicator;

    // private ObservableList<String> searchKeywordIndicator;

    @FXML
    private Label outdatedLabel;

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
                       ObservableList<String> sortIndicator,
                       ObservableList<Predicate<Payment>> predicateIndicator) {
        super(FXML_FILE_NAME, null);
        paymentListView.setItems(paymentList);
        paymentListView.setCellFactory(listView -> new PaymentListViewCell());
        this.sortIndicator = sortIndicator;
        this.predicateIndicator = predicateIndicator;
        // this.searchKeywordIndicator = searchKeywordIndicator;

        highlightSortLabel(this.sortIndicator.get(0));
        highlightPredicateLabel(this.predicateIndicator.get(0));

        this.sortIndicator.addListener((ListChangeListener<String>) change -> {
            highlightSortLabel(this.sortIndicator.get(0));
        });
        this.predicateIndicator.addListener((ListChangeListener<Predicate<Payment>>) change -> {
            highlightPredicateLabel(this.predicateIndicator.get(0));
        });
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

    private void highlightSortLabel(String sortCriteria) {
        switch (sortCriteria) {
        case "time":
            timeLabel.setOpacity(1);
            amountLabel.setOpacity(0.2);
            priorityLabel.setOpacity(0.2);
            break;

        case "amount":
            timeLabel.setOpacity(0.2);
            amountLabel.setOpacity(1);
            priorityLabel.setOpacity(0.2);
            break;

        case "priority":
            timeLabel.setOpacity(0.2);
            amountLabel.setOpacity(0.2);
            priorityLabel.setOpacity(1);
            break;
        }
    }

    private void highlightPredicateLabel(Predicate<Payment> predicate) {
        if (predicate instanceof PaymentOutOfDatePredicate) {
            outdatedLabel.setOpacity(1);
            weekLabel.setOpacity(0.2);
            monthLabel.setOpacity(0.2);
            allLabel.setOpacity(0.2);
            searchLabel.setOpacity(0.2);
        } else if (predicate instanceof PaymentInWeekPredicate) {
            outdatedLabel.setOpacity(0.2);
            weekLabel.setOpacity(1);
            monthLabel.setOpacity(0.2);
            allLabel.setOpacity(0.2);
            searchLabel.setOpacity(0.2);
        } else if (predicate instanceof PaymentInMonthPredicate) {
            outdatedLabel.setOpacity(0.2);
            weekLabel.setOpacity(0.2);
            monthLabel.setOpacity(1);
            allLabel.setOpacity(0.2);
            searchLabel.setOpacity(0.2);
        } else if (predicate instanceof SearchKeywordPredicate) {
            outdatedLabel.setOpacity(0.2);
            weekLabel.setOpacity(0.2);
            monthLabel.setOpacity(0.2);
            allLabel.setOpacity(0.2);
            searchLabel.setOpacity(1);
        } else {
            outdatedLabel.setOpacity(0.2);
            weekLabel.setOpacity(0.2);
            monthLabel.setOpacity(0.2);
            allLabel.setOpacity(1);
            searchLabel.setOpacity(0.2);
        }
    }

}

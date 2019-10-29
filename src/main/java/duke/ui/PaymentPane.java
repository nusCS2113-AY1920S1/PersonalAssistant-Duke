package duke.ui;

import duke.commons.LogsCenter;
import duke.model.payment.Payment;
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

    ObservableList<Predicate<Payment>> timeScopeIndicator;

    ObservableList<String> searchKeywordIndicator;

    @FXML
    private Label outdatedLabel;

    @FXML
    private Label weekLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label allLabel;

    @FXML
    private ListView<Payment> paymentListView;

    public PaymentPane(ObservableList<Payment> paymentList,
                       ObservableList<String> sortIndicator,
                       ObservableList<Predicate<Payment>> timeScopeIndicator,
                       ObservableList<String> searchKeywordIndicator) {
        super(FXML_FILE_NAME, null);
        paymentListView.setItems(paymentList);
        paymentListView.setCellFactory(listView -> new PaymentListViewCell());
        this.sortIndicator = sortIndicator;
        this.timeScopeIndicator = timeScopeIndicator;
        // this.searchKeywordIndicator = searchKeywordIndicator;

        this.sortIndicator.addListener((ListChangeListener<String>) change -> {
            highlightSortLabel(this.sortIndicator.get(0));
        });
        this.timeScopeIndicator.addListener((ListChangeListener<Predicate<Payment>>) change -> {
            highlightTimeScopeLabel(this.timeScopeIndicator.get(0));
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

    }

    private void highlightTimeScopeLabel(Predicate<Payment> timeScopePredicate) {

    }


}

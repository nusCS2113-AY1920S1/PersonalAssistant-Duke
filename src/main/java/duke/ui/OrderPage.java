package duke.ui;

import duke.commons.core.LogsCenter;
import duke.model.order.Order;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.logging.Logger;

/**
 * Controller class for order page.
 * An order page contains order cards and a statistics bar.
 */
public class OrderPage extends UiPart<AnchorPane> {
    private static final String FXML = "OrderPage.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderPage.class);

    @FXML
    private ListView<Order> orderListView;

    @FXML
    private Label active;

    @FXML
    private Label finished;

    @FXML
    private Label canceled;

    /**
     * Creates an order page displaying orders from {@code orderList}.
     */
    public OrderPage(ObservableList<Order> orderList) {
        super(FXML);

        logger.info("Initializing Order Page");

        initializeListView(orderList);

        initializeListener(orderList);

        //Since clicking on ListView results in unwanted changed of colors
        //of list cell elements (for example, label colors),
        //clicking should be disabled.
        disableMouseClick();
    }

    private void initializeListView(ObservableList<Order> orderList) {
        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
    }

    private void initializeListener(ObservableList<Order> orderList) {
        updateStatistics(orderList);
        orderList.addListener((ListChangeListener<Order>) change ->
            updateStatistics(orderList)
        );
    }

    private void updateStatistics(ObservableList<Order> orders) {
        int activeCount = 0;
        int finishedCount = 0;
        int canceledCount = 0;

        for (Order order : orders) {
            if (Order.Status.ACTIVE.equals(order.getStatus())) {
                activeCount++;
            } else if (Order.Status.COMPLETED.equals(order.getStatus())) {
                finishedCount++;
            } else {
                canceledCount++;
            }
        }

        active.setText(activeCount + " active");
        finished.setText(finishedCount + " completed");
        canceled.setText(canceledCount + " canceled");
    }

    private void disableMouseClick() {
        orderListView.addEventFilter(MouseEvent.MOUSE_PRESSED, Event::consume);
    }

    static class OrderListViewCell extends ListCell<Order> {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);
            updateSelected(false);
            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }
}

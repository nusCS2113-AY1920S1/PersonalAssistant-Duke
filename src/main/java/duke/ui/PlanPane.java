package duke.ui;

import duke.commons.LogsCenter;
import duke.model.PlanBot;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.util.logging.Logger;

public class PlanPane extends UiPart<BorderPane> {

    private static final Logger logger = LogsCenter.getLogger(PlanPane.class);

    private static final String FXML_FILE_NAME = "PlanPane.fxml";

    @FXML
    private ListView<PlanBot.PlanDialog> dialogListView;

    /**
     * Constructor for the controller.
     *
     * @param dialogObservableList a ObservableList of PlanDialog from PlanBot
     */
    public PlanPane(ObservableList<PlanBot.PlanDialog> dialogObservableList) {
        super(FXML_FILE_NAME, null);
        dialogListView.setItems(dialogObservableList);
        logger.info("DialogList set");
        dialogListView.setCellFactory(planDialogListView -> new PlanDialogListViewCell());
        Platform.runLater(() -> dialogListView.scrollTo(dialogObservableList.size() - 1));
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PlanBot.PlanDialog}
     * using a {@code PlanBot.PlanDialog}.
     */
    static class PlanDialogListViewCell extends ListCell<PlanBot.PlanDialog> {
        @Override
        protected void updateItem(PlanBot.PlanDialog dialog, boolean empty) {
            super.updateItem(dialog, empty);
            if (empty || dialog == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DialogBox(dialog).getRoot());
            }
        }
    }


}

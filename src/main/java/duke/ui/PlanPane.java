package duke.ui;

import duke.commons.LogsCenter;
import duke.model.PlanBot;
import javafx.collections.ListChangeListener;
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
     * @param dialogObservableList a ObservableList of PlanDialog from PlanBot
     */
    public PlanPane(ObservableList<PlanBot.PlanDialog> dialogObservableList) {
        super(FXML_FILE_NAME, null);
        dialogListView.setItems(dialogObservableList);
        logger.info("DialogList set");
        dialogListView.setCellFactory(planDialogListView -> new PlanDialogListViewCell());
        addAutoScroll(dialogListView);

    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PlanBot.PlanDialog}
     * using a {@code PlanBot.PlanDialog}.
     */
    class PlanDialogListViewCell extends ListCell<PlanBot.PlanDialog> {
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


    /**
     * Code from https://stackoverflow.com/questions/14779135/javafx-tableview-auto-scroll-to-the-last.
     *
     * @param view the listView we want to auto scroll
     * @param <S>  the object in the list
     */
    public static <S> void addAutoScroll(final ListView<S> view) {
        if (view == null) {
            throw new NullPointerException();
        }

        view.getItems().addListener((ListChangeListener<S>) (c -> {
            c.next();
            final int size = view.getItems().size();
            if (size > 0) {
                view.scrollTo(size - 1);
            }
        }));
    }


}

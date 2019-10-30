package duke.ui.window;

import duke.data.Help;
import duke.ui.UiElement;
import duke.ui.card.HelpCard;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class HelpWindow extends UiElement<Region> {
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private ListView<Help> helpListView;

    public HelpWindow() {
        super(FXML, null);

        helpListView.setCellFactory(listView -> new HelpListViewCell());
    }


    class HelpListViewCell extends ListCell<Help> {
        @Override
        protected void updateItem(Help help, boolean empty) {
            super.updateItem(help, empty);

            if (empty || help == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HelpCard(help));
            }
        }
    }
}

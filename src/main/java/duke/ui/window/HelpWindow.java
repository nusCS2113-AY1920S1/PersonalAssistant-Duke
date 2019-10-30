package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.data.GsonStorage;
import duke.data.Help;
import duke.exception.DukeException;
import duke.ui.UiElement;
import duke.ui.card.HelpCard;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;

import java.util.List;

public class HelpWindow extends UiElement<Region> {
    private static final String FXML = "HelpWindow.fxml";
    private static final String HELP_FILE = "data/helpDetails.json";

    @FXML
    private JFXListView<HelpCard> helpListView;

    public HelpWindow(GsonStorage storage) throws DukeException {
        super(FXML, null);

        List<Help> helpList = storage.loadHelpList(HELP_FILE);

        helpList.forEach(help -> {
            helpListView.getItems().add(new HelpCard(help));
        });
    }
}

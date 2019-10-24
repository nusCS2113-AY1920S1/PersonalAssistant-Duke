package cube.ui;

import cube.model.FoodList;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;

public class ListPanel extends UiManager<StackPane> {
    private static final String FXML = "ListPanel.fxml";

    @FXML
    private ListView<FoodList> foodListView;

    public ListPanel() {
        super(FXML);
    }
}

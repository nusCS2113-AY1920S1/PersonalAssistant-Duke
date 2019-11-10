package duke.ui;

import duke.model.PlanBot;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DialogBox extends UiPart<Region> {
    private static final String FXML_FILE_NAME = "DialogBox.fxml";
    public final PlanBot.PlanDialog dialog;


    @FXML
    private Label text;
    @FXML
    VBox textContainer;

    /**
     * Controller for dialog box in PlanPane.
     * @param dialog the dialog object given by PlanBot
     */
    public DialogBox(PlanBot.PlanDialog dialog) {
        super(FXML_FILE_NAME, null);
        this.dialog = dialog;
        text.setText(dialog.text);
        text.setBackground(new Background(new BackgroundFill(Color.rgb(233,170,27)
                , new CornerRadii(5), Insets.EMPTY)));
        if (dialog.agent == PlanBot.Agent.USER) {
            textContainer.setAlignment(Pos.BOTTOM_RIGHT);
        } else {
            text.setAlignment(Pos.BOTTOM_LEFT);
            text.setTextFill(Color.rgb(253,214,108));
            text.setBackground(new Background(new BackgroundFill(Color.rgb(49,66,86)
                    , new CornerRadii(5), Insets.EMPTY)));
        }
    }


}

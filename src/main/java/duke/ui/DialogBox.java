package duke.ui;

import duke.model.PlanBot;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class DialogBox extends UiPart<Region> {
    private static final String FXML_FILE_NAME = "DialogBox.fxml";
    public final PlanBot.PlanDialog dialog;



    @FXML
    private Label text;

    public DialogBox(PlanBot.PlanDialog dialog) {
        super(FXML_FILE_NAME, null);
        this.dialog = dialog;
        text.setText(dialog.text);
        if(dialog.agent == PlanBot.Agent.BOT) {
            text.setAlignment(Pos.BOTTOM_RIGHT);
        }
    }



}

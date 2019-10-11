package JavaFx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;

/**
 * Controller for progress indicator. This control represents the progress indicator for a module.
 */
public class ProgressController extends HBox {
    @FXML
    private Label moduleCodeLabel;
    @FXML
    private Label completedValueLabel;
    @FXML
    private Label overdueValueLabel;
    @FXML
    private ProgressIndicator progressIndicator;

    String moduleCode;
    String completedValue;
    String overdueValue;
    Double progressValue;

    /**
     * This function gets data from the user.
     * @param mc The module code input by user
     * @param cv The number of completed tasks for the module
     * @param ov The number of overdue tasks for the module
     */
    public void getData(String mc, String cv, String ov) { //initialize the string in the controller
        moduleCode = mc;
        completedValue = cv;
        overdueValue = ov;
        progressValue = Double.parseDouble(completedValue)/Double.parseDouble(overdueValue);

        moduleCodeLabel.setText(moduleCode);
        completedValueLabel.setText(completedValue);
        overdueValueLabel.setText(overdueValue);
        progressIndicator.setProgress(progressValue);
    }
}

package duke.ui;

import duke.model.Income;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class IncomeCard extends UiPart<Region> {
    private static final String FXML_FILE_NAME = "IncomeCard.fxml";
    public final Income income;

    @FXML
    private Label description;
    @FXML
    private Label amount;
    @FXML
    private VBox incomeContainer;

    /**
     * Constructor for incomeCard.
     *
     * @param income income from incomeList
     * @param index the specific number of income in the list
     */
    public IncomeCard(Income income, int index) {
        super(FXML_FILE_NAME, null);
        this.income = income;
        description.setText(index + ". " + income.getDescription());
        amount.setText("$" + income.getAmount().toString());
    }
}

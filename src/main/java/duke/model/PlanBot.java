package duke.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

public class PlanBot {

    public PlanBot() {
        this.dialogList = new ArrayList<>();
        dialogList.add(new PlanDialog("test", Agent.USER));
        dialogObservableList = FXCollections.observableList(dialogList);
    }

    public enum Agent{
        USER,
        BOT
    }

    private List<PlanDialog> dialogList;
    private ObservableList<PlanDialog> dialogObservableList;


    public ObservableList<PlanDialog> getDialogObservableList() {
        return dialogObservableList;
    }

    public void processInput(String input) {
        dialogObservableList.add(new PlanDialog(input, Agent.USER));
        dialogObservableList.add(new PlanDialog(input, Agent.BOT));
    }

    public class PlanDialog {
        public String text;
        public Agent agent;

        public PlanDialog(String text, Agent agent) {
            this.agent = agent;
            this.text = text;
        }
    }
}

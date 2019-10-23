package duke.model;

import duke.storage.PlanAttributesStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlanBot {


    private List<PlanDialog> dialogList;
    private ObservableList<PlanDialog> dialogObservableList;
    private PlanQuestionBank planQuestionBank;
    private Map<String,String> planAttributes;

    public PlanBot(Map<String,String> planAttributes) {
        this.dialogList = new ArrayList<>();
        dialogObservableList = FXCollections.observableList(dialogList);
        this.planAttributes = planAttributes;
        if(!planAttributes.isEmpty()){
            StringBuilder knownAttributes = new StringBuilder("Here's what I know about you: \n");
            for(String key : planAttributes.keySet()) {
                knownAttributes.append(key + " : " + planAttributes.get(key) + "\n");
            }
            PlanDialog knownDialog = new PlanDialog(knownAttributes.toString(), Agent.BOT);
            dialogObservableList.add(knownDialog);
        }
        planQuestionBank = new PlanQuestionBank();
        PlanDialog initial = new PlanDialog(planQuestionBank.getCurrentQuestion(), Agent.BOT);
        dialogObservableList.add(initial);
    }

    public enum Agent{
        USER,
        BOT
    }


    public ObservableList<PlanDialog> getDialogObservableList() {
        return dialogObservableList;
    }

    public void processInput(String input) {
        dialogObservableList.add(new PlanDialog(input, Agent.USER));
        PlanQuestionBank.Reply reply = planQuestionBank.getReply(input, planAttributes);
        String replyString = reply.getText();
        planAttributes = reply.getAttributes();
        dialogObservableList.add(new PlanDialog(replyString, Agent.BOT));
    }

    public class PlanDialog {
        public String text;
        public Agent agent;

        public PlanDialog(String text, Agent agent) {
            this.agent = agent;
            this.text = text;
        }
    }

    public Map<String, String> getPlanAttributes() {
        return planAttributes;
    }


}

package duke.model;

import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class PlanBot {

    public enum Agent {
        USER,
        BOT
    }

    private List<PlanDialog> dialogList;
    private ObservableList<PlanDialog> dialogObservableList;
    private PlanQuestionBank planQuestionBank;
    private Map<String, String> planAttributes;
    private Queue<PlanQuestion> questionQueue;
    private PlanQuestion currentQuestion;

    public PlanBot(Map<String, String> planAttributes) throws DukeException {
        this.dialogList = new ArrayList<>();
        dialogObservableList = FXCollections.observableList(dialogList);
        this.planAttributes = planAttributes;
        this.questionQueue = new LinkedList<>();
        if (!planAttributes.isEmpty()) {
            StringBuilder knownAttributes = new StringBuilder("Here's what I know about you: \n");
            for (String key : planAttributes.keySet()) {
                knownAttributes.append(key + " : " + planAttributes.get(key) + "\n");
            }
            PlanDialog knownDialog = new PlanDialog(knownAttributes.toString(), Agent.BOT);
            dialogObservableList.add(knownDialog);
        }
        planQuestionBank = new PlanQuestionBank();
        questionQueue.addAll(planQuestionBank.getInitialQuestions(planAttributes));
        if (questionQueue.isEmpty()) {
            currentQuestion = null;
        } else {
            PlanQuestion firstQuestion = questionQueue.peek();
            currentQuestion = firstQuestion;
            questionQueue.remove();
            PlanDialog initial = new PlanDialog(firstQuestion.getQuestion(), Agent.BOT);
            dialogObservableList.add(initial);
        }

    }


    public ObservableList<PlanDialog> getDialogObservableList() {
        return dialogObservableList;
    }

    public void processInput(String input) {
        dialogObservableList.add(new PlanDialog(input, Agent.USER));
        if (currentQuestion == null) {
            PlanDialog emptyQueueDialog = new PlanDialog("I know everything about you already!", Agent.BOT);
            dialogObservableList.add(emptyQueueDialog);
        } else {
            try {
                PlanQuestion.Reply reply = currentQuestion.getReply(input, planAttributes);
                dialogObservableList.add(new PlanDialog(reply.getText(), Agent.BOT));
                planAttributes = reply.getAttributes();
                currentQuestion = null;
                if (!questionQueue.isEmpty()) {
                    currentQuestion = questionQueue.peek();
                    questionQueue.remove();
                }
                dialogObservableList.add(new PlanDialog(currentQuestion.getQuestion(), Agent.BOT));
            } catch (DukeException e) {
                dialogObservableList.add(new PlanDialog(e.getMessage(), Agent.BOT));
            }
        }


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

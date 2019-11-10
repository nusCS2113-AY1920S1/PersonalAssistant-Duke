package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * PlanBot is the overall budget recommendation class.
 * It's responsible for displaying the dialog to the user in a list,
 * printing error messages as a dialog from the bot
 * and asking users answered questions form question bank.
 * It ensures that if its asking a question, only one question is being asked at a time.
 */
public class PlanBot {

    private static PlanBot planBot;

    private static final Logger logger = LogsCenter.getLogger(PlanQuestion.class);

    /**
     * Denotes if the dialog is from the user or from the Bot.
     */
    public enum Agent {
        USER,
        BOT
    }

    /**
     * Contains a <code>List</code> of <code>PlanDialog</code> which is the chatBot's history.
     */
    private List<PlanDialog> dialogList;

    /**
     * The a <code>ObservableList</code> of <code>PlanDialog</code> history
     * so that the GUI can automatically be updated without having to repopulate the entire list.
     */
    private ObservableList<PlanDialog> dialogObservableList;

    /**
     * Contains all the <code>PlanQuestion</code> that we are going to ask the user.
     */
    private PlanQuestionBank planQuestionBank;

    /**
     * A Map of the user's attributes that we have already found out.
     */
    private Map<String, String> planAttributes;

    /**
     * The buffer of questions we are asking the user.
     */
    private Queue<PlanQuestion> questionQueue;

    /**
     * The current question being asked, contains null when no more questions are being asked.
     */
    private PlanQuestion currentQuestion;

    private PlanQuestionBank.PlanRecommendation planBudgetRecommendation;

    /**
     * Constructor for PlanBot.
     *
     * @param planAttributes the loaded attributes from Storage
     * @throws DukeException when there is an error loading questions based on the loaded planAttributes
     */
    private PlanBot(Map<String, String> planAttributes) {
        this.dialogList = new ArrayList<>();
        dialogObservableList = FXCollections.observableList(dialogList);
        this.planAttributes = planAttributes;
        this.questionQueue = new LinkedList<>();
        if (planAttributes.isEmpty()) {
            dialogObservableList.add(new PlanDialog("Hi, seems like this is the first time using Duke++. "
                    + "Let me plan your budget for you!"
                    + " Alternatively, type \"goto expense\" to start using Duke++!",
                    Agent.BOT));
        }
        try {
            planQuestionBank = PlanQuestionBank.getInstance();
        } catch (DukeException e) {
            dialogObservableList.add(new PlanDialog(e.getMessage(), Agent.BOT));
        }
        try {
            questionQueue.addAll(planQuestionBank.getQuestions(planAttributes));
        } catch (DukeException e) {
            dialogObservableList.add(new PlanDialog(e.getMessage(), Agent.BOT));
        }
        if (questionQueue.isEmpty()) {
            currentQuestion = null;
            sendCompletedMessage();
        } else {
            dialogList.add(new PlanDialog("Tell me more about yourself to give you recommendations", Agent.BOT));
            PlanQuestion firstQuestion = questionQueue.peek();
            currentQuestion = firstQuestion;
            questionQueue.remove();
            assert firstQuestion != null;
            PlanDialog initial = new PlanDialog(firstQuestion.getQuestion(), Agent.BOT);
            dialogObservableList.add(initial);
        }
    }

    public static PlanBot getInstance(Map<String, String> planAttributes) {
        if (planBot == null) {
            planBot = new PlanBot(planAttributes);
        }
        return planBot;
    }

    public ObservableList<PlanDialog> getDialogObservableList() {
        return dialogObservableList;
    }

    /**
     * Processes the input String of the user.
     *
     * @param input the input String from the user
     */
    public void processInput(String input) {
        dialogObservableList.add(new PlanDialog(input, Agent.USER));
        if (currentQuestion == null) {
            sendCompletedMessage();
        } else {
            try {
                PlanQuestion.Reply reply = currentQuestion.getReply(input, planAttributes);
                questionQueue.clear();
                questionQueue.addAll(planQuestionBank.getQuestions(planAttributes));
                logger.info("\n\n\nQueue size: " + questionQueue.size());
                dialogObservableList.add(new PlanDialog(reply.getText(), Agent.BOT));
                planAttributes = reply.getAttributes();
                if (!questionQueue.isEmpty()) {
                    currentQuestion = questionQueue.peek();
                    questionQueue.remove();
                    dialogObservableList.add(new PlanDialog(currentQuestion.getQuestion(), Agent.BOT));
                } else {
                    sendCompletedMessage();
                }
            } catch (DukeException e) {
                dialogObservableList.add(new PlanDialog(e.getMessage(), Agent.BOT));
            }
        }
    }

    public PlanQuestionBank.PlanRecommendation getPlanBudgetRecommendation() {
        return planBudgetRecommendation;
    }

    public Map<String, String> getPlanAttributes() {
        return planAttributes;
    }


    /**
     * Puts the recommendation into the dialog list.
     */
    private void sendCompletedMessage() {
        logger.info("Completed Plan Bot");
        try {
            dialogList.add(new PlanDialog(planQuestionBank
                    .makeRecommendation(planAttributes)
                    .getRecommendation(), Agent.BOT));
            planBudgetRecommendation = planQuestionBank
                    .makeRecommendation(planAttributes);
            StringBuilder recommendedBudgetStringBuilder = new StringBuilder();
            for (String category : planBudgetRecommendation.getPlanBudget().keySet()) {
                recommendedBudgetStringBuilder.append(category)
                        .append(" : ")
                        .append(planBudgetRecommendation.getPlanBudget().get(category))
                        .append("\n");
            }
            dialogList.add(new PlanDialog("Here's a recommended budget for you: \n"
                    + recommendedBudgetStringBuilder.toString()
                    + "type \"export\" to export the budget",
                    Agent.BOT));
        } catch (DukeException e) {
            dialogList.add(new PlanDialog(e.getMessage(), Agent.BOT));
        }

    }


    /**
     * A container for an individual chat history.
     */
    public static class PlanDialog {
        public String text;
        public Agent agent;

        public PlanDialog(String text, Agent agent) {
            this.agent = agent;
            this.text = text;
        }
    }


}

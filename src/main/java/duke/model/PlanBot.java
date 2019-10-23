package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.Parser.Parser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

public class PlanBot {

    private static final Logger logger = LogsCenter.getLogger(PlanQuestion.class);


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
        questionQueue.addAll(planQuestionBank.getQuestions(planAttributes));
        if (questionQueue.isEmpty()) {
            currentQuestion = null;
            dialogList.add(new PlanDialog(makeRecommendation(), Agent.BOT));

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



    public void processInput(String input) throws DukeException {
        dialogObservableList.add(new PlanDialog(input, Agent.USER));
        if (currentQuestion == null) {
            PlanDialog emptyQueueDialog = new PlanDialog("I know everything about you already!", Agent.BOT);
            dialogObservableList.add(emptyQueueDialog);
            dialogObservableList.add(new PlanDialog(makeRecommendation(), Agent.BOT));
        } else {
            try {
                PlanQuestion.Reply reply = currentQuestion.getReply(input, planAttributes);
                questionQueue = planQuestionBank.getQuestions(planAttributes);
                logger.info("\n\n\nQueue size: " + questionQueue.size() );
                dialogObservableList.add(new PlanDialog(reply.getText(), Agent.BOT));
                planAttributes = reply.getAttributes();
                currentQuestion = null;
                if (!questionQueue.isEmpty()) {
                    currentQuestion = questionQueue.peek();
                    questionQueue.remove();
                }
                if(currentQuestion != null) {
                    dialogObservableList.add(new PlanDialog(currentQuestion.getQuestion(), Agent.BOT));
                }else {
                    dialogObservableList.add(new PlanDialog(makeRecommendation(), Agent.BOT));
                }
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

    private String makeRecommendation() throws DukeException {
        StringBuilder recommendation = new StringBuilder();
        if(planAttributes.get("NUS_STUDENT").equals("FALSE")) {
            return "Since you're not a NUS student, I can't make any recommendations for you :(";
        }

        if(planAttributes.get("CAMPUS_LIFE").equals("FALSE")) {
            String tripCostString = planAttributes.get("TRIP_COST");
            String tripsPerWeekString  = planAttributes.get("TRAVEL_DAYS");
            int tripsPerWeek = Integer.parseInt(tripsPerWeekString);
            BigDecimal tripsPerWeekBD = BigDecimal.valueOf(tripsPerWeek);
            BigDecimal tripCost = Parser.parseMoney(tripCostString);
            BigDecimal monthlyCost = tripCost.multiply(tripsPerWeekBD).multiply(BigDecimal.valueOf(4));
            switch(planAttributes.get("TRANSPORT_METHOD")) {
            case "MRT":
                if(monthlyCost.compareTo(BigDecimal.valueOf(48)) > 0) {
                    recommendation.append("Based on your travelling habits, it is cheaper to buy concession!\n");
                    recommendation.append("MRT concession costs: $48.00 monthly.\n");
                }else {
                    recommendation.append("Your transport costs $" + monthlyCost + " monthly. \n");
                }
                break;

            case "BUS":
                if(monthlyCost.compareTo(BigDecimal.valueOf(52)) > 0) {
                    recommendation.append("Based on your travelling habits, it is cheaper to buy concession!\n");
                    recommendation.append("MRT concession costs: $52.00 monthly.\n");
                }else {
                    recommendation.append("Your transport costs $" + monthlyCost + " monthly. \n");
                }
                break;


            default:
                if(monthlyCost.compareTo(BigDecimal.valueOf(85)) > 0) {
                    recommendation.append("Based on your travelling habits, it is cheaper to buy concession!\n");
                    recommendation.append("Both Bus and MRT concession cost: $85.00 monthly.\n");
                }else {
                    recommendation.append("Your transport costs $" + monthlyCost + " monthly. \n");
                }
                break;

            }
        }

        return recommendation.toString();


    }


}

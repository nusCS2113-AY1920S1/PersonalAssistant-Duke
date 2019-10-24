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

    /**
     * Constructor for PlanBot.
     *
     * @param planAttributes the loaded attributes from Storage
     * @throws DukeException when there is an error loading questions based on the loaded planAttributes
     */
    public PlanBot(Map<String, String> planAttributes) {
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
        try {
            planQuestionBank = new PlanQuestionBank();
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
            try {
                dialogList.add(new PlanDialog(makeRecommendation(), Agent.BOT));
            } catch (DukeException e) {
                dialogList.add(new PlanDialog(e.getMessage(), Agent.BOT));
            }
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

    /**
     * Processes the input String of the user.
     *
     * @param input the input String of the user
     * @throws DukeException when there is invalid input
     */
    public void processInput(String input) throws DukeException {
        dialogObservableList.add(new PlanDialog(input, Agent.USER));
        if (currentQuestion == null) {
            PlanDialog emptyQueueDialog = new PlanDialog("Based on what you've told me, here's a recommended budget plan!", Agent.BOT);
            dialogObservableList.add(emptyQueueDialog);
            dialogObservableList.add(new PlanDialog(makeRecommendation(), Agent.BOT));
        } else {
            try {
                PlanQuestion.Reply reply = currentQuestion.getReply(input, planAttributes);
                questionQueue.clear();
                questionQueue.addAll(planQuestionBank.getQuestions(planAttributes));
                logger.info("\n\n\nQueue size: " + questionQueue.size());
                dialogObservableList.add(new PlanDialog(reply.getText(), Agent.BOT));
                planAttributes = reply.getAttributes();
                currentQuestion = null;
                if (!questionQueue.isEmpty()) {
                    currentQuestion = questionQueue.peek();
                    questionQueue.remove();
                }
                if (currentQuestion != null) {
                    dialogObservableList.add(new PlanDialog(currentQuestion.getQuestion(), Agent.BOT));
                } else {
                    dialogObservableList.add(new PlanDialog(makeRecommendation(), Agent.BOT));
                }
            } catch (DukeException e) {
                dialogObservableList.add(new PlanDialog(e.getMessage(), Agent.BOT));
            }
        }
    }




    public Map<String, String> getPlanAttributes() {
        return planAttributes;
    }

    /**
     * Makes recommendations for the user. This should only be called when we know every attribute of the user.
     *
     * @return String budget recommendations.
     * @throws DukeException when there is an error in constructing the recommendation based on the knownAttributes
     */
    private String makeRecommendation() throws DukeException {
        StringBuilder recommendation = new StringBuilder();
        if (planAttributes.get("NUS_STUDENT").equals("FALSE")) {
            return "Since you're not a NUS student, I can't make any recommendations for you :(";
        }
        if (planAttributes.get("CAMPUS_LIFE").equals("FALSE")) {
            String tripCostString = planAttributes.get("TRIP_COST");
            String tripsPerWeekString = planAttributes.get("TRAVEL_DAYS");
            int tripsPerWeek = Integer.parseInt(tripsPerWeekString);
            BigDecimal tripsPerWeekBD = BigDecimal.valueOf(tripsPerWeek);
            BigDecimal tripCost = Parser.parseMoney(tripCostString);
            BigDecimal monthlyCost = tripCost.multiply(tripsPerWeekBD).multiply(BigDecimal.valueOf(8));
            switch (planAttributes.get("TRANSPORT_METHOD")) {
            case "MRT":
                if (monthlyCost.compareTo(BigDecimal.valueOf(48)) > 0) {
                    recommendation.append("Based on your travelling habits, it is cheaper to buy concession!\n" +
                            "MRT concession costs: $48.00 monthly.\n" +
                            "You should set your transport budget at $48.00 monthly\n");
                } else {
                    recommendation.append("You should set transport budget at $" + monthlyCost + " monthly. \n");
                }
                break;
            case "BUS":
                if (monthlyCost.compareTo(BigDecimal.valueOf(52)) > 0) {
                    recommendation.append("Based on your travelling habits, it is cheaper to buy concession!\n" +
                            "MRT concession costs: $52.00 monthly.\n" +
                            "You should set your transport budget at $52.00 monthly\n");
                } else {
                    recommendation.append("You should set transport budget at $" + monthlyCost + " monthly. \n");
                }
                break;
            default:
                if (monthlyCost.compareTo(BigDecimal.valueOf(85)) > 0) {
                    recommendation.append("Based on your travelling habits, it is cheaper to buy concession!\n" +
                            "Combined concession costs: $85.00 monthly.\n" +
                            "You should set your transport budget at $85.00 monthly\n");
                } else {
                    recommendation.append("You should set transport budget at $" + monthlyCost + " monthly. \n");
                }
                break;
            }

            int mealsPerDay =  Integer.parseInt(planAttributes.get("MEALS_PER_DAY"));
            BigDecimal costPerMeal = Parser.parseMoney(planAttributes.get("AVERAGE_MEAL_COST"));
            BigDecimal monthlyFoodBudget = costPerMeal.multiply(BigDecimal.valueOf(mealsPerDay)).multiply(BigDecimal.valueOf(30));
            recommendation.append("You should set food budget at $" + monthlyFoodBudget +  " monthly. \n");
        } else {
            if(planAttributes.get("DINE_IN_HALL").equals("TRUE")) {
                BigDecimal costPerMeal = Parser.parseMoney(planAttributes.get("AVERAGE_MEAL_COST"));
                BigDecimal monthlyFoodBudget = costPerMeal.multiply(BigDecimal.valueOf(1)).multiply(BigDecimal.valueOf(11)); //11 since 3 meals during each weekend * 1 meal per day
                recommendation.append("You should set food budget at $" + monthlyFoodBudget + " monthly. \n");
            }else {
                int mealsPerDay =  Integer.parseInt(planAttributes.get("MEALS_PER_DAY"));
                BigDecimal costPerMeal = Parser.parseMoney(planAttributes.get("AVERAGE_MEAL_COST"));
                BigDecimal monthlyFoodBudget = costPerMeal.multiply(BigDecimal.valueOf(mealsPerDay)).multiply(BigDecimal.valueOf(30));
                recommendation.append("You should set food budget at $" + monthlyFoodBudget + " monthly. \n");
            }
        }



        if(recommendation.toString().isEmpty()){
            return "I can't make any recommendations for you :(. Something probably went wrong";
        }
        return recommendation.toString();
    }

    /**
     * A container for an individual chat history.
     */
    public class PlanDialog {
        public String text;
        public Agent agent;

        public PlanDialog(String text, Agent agent) {
            this.agent = agent;
            this.text = text;
        }
    }


}

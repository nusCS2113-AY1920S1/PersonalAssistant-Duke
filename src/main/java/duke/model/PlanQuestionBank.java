package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.Parser.Parser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;

public class PlanQuestionBank {
    private Map<Integer, PlanQuestion> questionList;

    private static final Logger logger = LogsCenter.getLogger(PlanQuestionBank.class);


    private static final String[] BOOL_ANSWERS = {"YES", "Y", "NO", "N"};
    private static final String[] BOOL_ATTRIBUTE_VALUES = {"TRUE", "TRUE", "FALSE", "FALSE"};

    /**
     * Constructor of the question bank, developers should add new questions inside here.
     * @throws DukeException on Error constructing the QuestionBank
     */
    public PlanQuestionBank() throws DukeException {
        this.questionList = new HashMap<>();
        PlanQuestion question1 = new PlanQuestion("Are you a student from NUS? <yes/no>",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "NUS_STUDENT");
        question1.addNeighbouring("TRUE", 2);
        questionList.put(1, question1);

        PlanQuestion question2 = new PlanQuestion("Do you live on campus? <yes/no>",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "CAMPUS_LIFE");
        question2.addNeighbouring("FALSE", 3);
        question2.addNeighbouring("FALSE", 4);
        question2.addNeighbouring("FALSE", 5);
        question2.addNeighbouring("FALSE", 7);
        question2.addNeighbouring("TRUE", 6);
        questionList.put(2, question2);
        questionList.put(3, new PlanQuestion(
                "How many days of the week do you travel go to school? <0 - 7>",
                new String[]{"0", "1", "2", "3", "4", "5", "6", "7"},
                new String[]{"0", "1", "2", "3", "4", "5", "6", "7"},
                "TRAVEL_DAYS"));

        questionList.put(4, new PlanQuestion("How do you go to school? <bus, mrt, both>",
                new String[]{"BUS", "MRT", "BOTH"},
                new String[]{"BUS", "MRT", "BOTH"},
                "TRANSPORT_METHOD"));

        questionList.put(5, new PlanQuestion("How much does your trip cost each way?",
                new String[]{"DOUBLE"},
                new String[]{"DOUBLE"},
                "TRIP_COST"));

        PlanQuestion question6 = new PlanQuestion("Do you eat at your Hall/RC often?",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "DINE_IN_HALL");
        question6.addNeighbouring("FALSE", 7);
        question6.addNeighbouring("TRUE" , 7);
        questionList.put(6, question6);


        PlanQuestion question7 =  new PlanQuestion("How many meals per day do pay for daily? <0 - 3>",
                new String[]{"0", "1", "2", "3"},
                new String[]{"0", "1", "2", "3"},
                "MEALS_PER_DAY");
        question7.addNeighbouring("1" , 8);
        question7.addNeighbouring("2" , 8);
        question7.addNeighbouring("3" , 8);
        questionList.put(7,question7);

        questionList.put(8, new PlanQuestion("How much on average is each meal that you pay for?",
                new String[]{"DOUBLE"},
                new String[]{"DOUBLE"},
                "AVERAGE_MEAL_COST"));
    }

    /**
     * Gets a Queue of questions to ask a user in PlanBot.
     * @param knownAttributes Map of String to String of what we already know about the users
     * @return a Queue of questions to ask the user
     */
    public Set<PlanQuestion> getQuestions(Map<String, String> knownAttributes) throws DukeException {
        Map<String, PlanQuestion> attributeQuestion = new HashMap<>();
        Queue<Integer> questionsToAdd = new LinkedList<>();
        questionsToAdd.add(1);
        while (!questionsToAdd.isEmpty()) {
            Integer index = questionsToAdd.peek();
            questionsToAdd.remove();
            try {
                PlanQuestion question = questionList.get(index);
                String questionAttribute = question.getAttribute();
                attributeQuestion.put(question.getAttribute(), question);
                if (knownAttributes.containsKey(questionAttribute)) {
                    String attributeValue = knownAttributes.get(questionAttribute);
                    Set<Integer> children = questionList.get(index).getNeighbouringQuestions(attributeValue);
                    if (!children.isEmpty()) {
                        questionsToAdd.addAll(children);
                    }
                }
            } catch (NullPointerException e) {
                throw new DukeException("Error getting neighbouring questions!");
            }
        }
        for (String knownAttribute : knownAttributes.keySet()) {
            attributeQuestion.remove(knownAttribute);
        }

        return new HashSet<>(attributeQuestion.values());
    }

    /**
     * Makes recommendations for the user. This should only be called when we know every attribute of the user.
     *
     * @return String budget recommendations.
     * @throws DukeException when there is an error in constructing the recommendation based on the knownAttributes
     */
    String makeRecommendation(Map<String, String> planAttributes) throws DukeException {
        StringBuilder recommendation = new StringBuilder();
        try {
            if (planAttributes.get("NUS_STUDENT").equals("FALSE")) {
                return "This program is designed for NUS students. \n " +
                        "Since you're not a NUS student, I can't make any recommendations for you :( \n" +
                        "However, you can still use the program! Type \"goto expense\" to start using.";
            }else
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
                        recommendation.append("Based on your travelling habits, it is cheaper to buy concession!\n")
                                .append("MRT concession costs: $48.00 monthly.\n")
                                .append("You should set your transport budget at $48.00 monthly\n");
                    } else {
                        recommendation.append("You should set transport budget at $").append(monthlyCost).append(" monthly. \n");
                    }
                    break;
                case "BUS":
                    if (monthlyCost.compareTo(BigDecimal.valueOf(52)) > 0) {
                        recommendation.append("Based on your travelling habits, it is cheaper to buy concession!\n")
                                .append("MRT concession costs: $52.00 monthly.\n")
                                .append("You should set your transport budget at $52.00 monthly\n");
                    } else {
                        recommendation.append("You should set transport budget at $").append(monthlyCost).append(" monthly. \n");
                    }
                    break;
                default:
                    if (monthlyCost.compareTo(BigDecimal.valueOf(85)) > 0) {
                        recommendation.append("Based on your travelling habits, it is cheaper to buy concession!\n" +
                                "Combined concession costs: $85.00 monthly.\n" +
                                "You should set your transport budget at $85.00 monthly\n");
                    } else {
                        recommendation.append("You should set transport budget at $")
                                .append(monthlyCost)
                                .append(" monthly. \n");
                    }
                    break;
                }

                int mealsPerDay =  Integer.parseInt(planAttributes.get("MEALS_PER_DAY"));
                BigDecimal costPerMeal = Parser.parseMoney(planAttributes.get("AVERAGE_MEAL_COST"));
                BigDecimal monthlyFoodBudget = costPerMeal
                        .multiply(BigDecimal.valueOf(mealsPerDay))
                        .multiply(BigDecimal.valueOf(30));
                recommendation.append("I'd suggest you set at $").append(monthlyFoodBudget).append(" monthly. \n");
            } else {
                recommendation.append("Since you live in campus, you can just allocate a small budget of $10 to transport! \n");
                if(planAttributes.get("DINE_IN_HALL").equals("TRUE")) {
                    BigDecimal costPerMeal = Parser.parseMoney(planAttributes.get("AVERAGE_MEAL_COST"));
                    BigDecimal monthlyFoodBudget = costPerMeal
                            .multiply(BigDecimal.valueOf(1))
                            .multiply(BigDecimal.valueOf(11)); //11 since 3 meals during each weekend * 1 meal per day
                    recommendation.append("I'd suggest you set food budget at $").append(monthlyFoodBudget).append(" monthly. \n");
                }else {
                    int mealsPerDay =  Integer.parseInt(planAttributes.get("MEALS_PER_DAY"));
                    BigDecimal costPerMeal = Parser.parseMoney(planAttributes.get("AVERAGE_MEAL_COST"));
                    BigDecimal monthlyFoodBudget = costPerMeal.multiply(BigDecimal.valueOf(mealsPerDay)).multiply(BigDecimal.valueOf(30));
                    recommendation.append("I'd suggest you set food budget at $").append(monthlyFoodBudget).append(" monthly. \n");
                }
            }
        }catch (NullPointerException e) {
            throw new DukeException("Missing attributes to make recommendation!");
        }




        if(recommendation.toString().isEmpty()){
            return "I can't make any recommendations for you :(. Something probably went wrong";
        }
        return recommendation.toString();
    }


}

package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.parser.Parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;

public class PlanQuestionBank {
    private Map<Integer, PlanQuestion> questionList;

    private static final Logger logger = LogsCenter.getLogger(PlanQuestionBank.class);


    private static final String[] BOOL_ANSWERS = {"YES", "Y", "NO", "N"};
    private static final String[] BOOL_ATTRIBUTE_VALUES = {"TRUE", "TRUE", "FALSE", "FALSE"};
    private static final String[] DOUBLE = {"DOUBLE"};


    /**
     * Constructor of the question bank, developers should add new questions inside here.
     *
     * @throws DukeException on Error constructing the QuestionBank
     */
    public PlanQuestionBank() throws DukeException {
        this.questionList = new HashMap<>();
        PlanQuestion question1 = new PlanQuestion("Are you a student from NUS? <yes/no>",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "NUS_STUDENT");
        question1.addNeighbouring("TRUE", 2);
        question1.addNeighbouring("TRUE", 9);
        questionList.put(1, question1);

        PlanQuestion question2 = new PlanQuestion("Do you live on campus? <yes/no>",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "CAMPUS_LIFE");
        question2.addNeighbouring("FALSE", 3);
        question2.addNeighbouring("TRUE", 6);
        questionList.put(2, question2);

        PlanQuestion question3 = new PlanQuestion(
                "How many days of the week do you travel go to school? <0 - 7>",
                generateIntRange(0, 7),
                generateIntRange(0, 7),
                "TRAVEL_DAYS");
        question3.addNeighbouring(4);
        questionList.put(3, question3);

        PlanQuestion question4 = new PlanQuestion("How do you go to school? <bus, mrt, both>",
                new String[]{"BUS", "MRT", "BOTH"},
                new String[]{"BUS", "MRT", "BOTH"},
                "TRANSPORT_METHOD");
        question4.addNeighbouring(5);
        questionList.put(4, question4);

        PlanQuestion question5 = new PlanQuestion("How much does your trip cost each way?",
                DOUBLE,
                DOUBLE,
                "TRIP_COST");
        question5.addNeighbouring(7);
        questionList.put(5, question5);


        PlanQuestion question6 = new PlanQuestion("Do you eat at your Hall/RC often?",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "DINE_IN_HALL");
        question6.addNeighbouring("FALSE", 7);
        question6.addNeighbouring("TRUE", 8);
        questionList.put(6, question6);

        PlanQuestion question7 = new PlanQuestion("How many meals per day do pay for daily? <0 - 3>",
                generateIntRange(0, 3),
                generateIntRange(0, 3),
                "MEALS_PER_DAY");
        question7.addNeighbouring("1", 8);
        question7.addNeighbouring("2", 8);
        question7.addNeighbouring("3", 8);
        questionList.put(7, question7);

        PlanQuestion question8 = new PlanQuestion("How much does each meal that you pay for "
                + "cost on average? <money amount>",
                DOUBLE,
                DOUBLE,
                "AVERAGE_MEAL_COST");
        question8.addNeighbouring(9);
        questionList.put(8, question8);

        PlanQuestion question9 = new PlanQuestion("How much do you pay for your phone bill? <money amount>",
                DOUBLE,
                DOUBLE,
                "PHONE_BILL");
        question9.addNeighbouring(10);
        questionList.put(9, question9);

        PlanQuestion question10 = new PlanQuestion("Do you subscribe to netflix? <yes/no>",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "NETFLIX");
        question10.addNeighbouring(11);
        questionList.put(10, question10);

        PlanQuestion question11 = new PlanQuestion("Do you subscribe to a music subscription service? <yes/no>",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "MUSIC_SUBSCRIPTION");
        question11.addNeighbouring(12);
        questionList.put(11, question11);

        questionList.put(12, new PlanQuestion("How much do you want to "
                + "spend on online shopping monthly? <money amount>",
                DOUBLE,
                DOUBLE,
                "ONLINE_SHOPPING"));
        logger.info("QuestionBank generated successfully!");
    }

    /**
     * Gets a Queue of questions to ask a user in PlanBot.
     *
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
                    questionsToAdd.addAll(children);
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
     * Makes recommendations for the user.
     * This should only be called when we know every attribute of the user.
     *
     * @return String budget recommendations.
     * @throws DukeException when there is an error in constructing the recommendation based on the knownAttributes
     */
    PlanRecommendation makeRecommendation(Map<String, String> planAttributes) throws DukeException {
        Map<String, BigDecimal> budgetRecommendation = new HashMap<>();
        StringBuilder recommendation = new StringBuilder();
        List<Expense> recommendationExpenseList = new ArrayList<>();
        try {
            if (planAttributes.get("NUS_STUDENT").equals("FALSE")) {
                return new PlanRecommendation("This program is designed for NUS students. \n "
                        + "Since you're not a NUS student, I can't make any recommendations for you :( \n"
                        + "However, you can still use the program! Type \"goto expense\" to start using.",
                        budgetRecommendation,
                        recommendationExpenseList);
            } else {
                //NUS STUDENT
                if (planAttributes.get("CAMPUS_LIFE").equals("FALSE")) {
                    String tripCostString = planAttributes.get("TRIP_COST");
                    String tripsPerWeekString = planAttributes.get("TRAVEL_DAYS");
                    int tripsPerWeek = Integer.parseInt(tripsPerWeekString);
                    BigDecimal tripsPerWeekBD = BigDecimal.valueOf(tripsPerWeek);
                    BigDecimal tripCost = Parser.parseMoney(tripCostString);
                    BigDecimal monthlyCost = tripCost
                            .multiply(tripsPerWeekBD)
                            .multiply(BigDecimal.valueOf(8));
                    switch (planAttributes.get("TRANSPORT_METHOD")) {
                    case "MRT":
                        if (monthlyCost.compareTo(BigDecimal.valueOf(48)) > 0) {
                            recommendation.append("Based on your travelling habits, "
                                    + "it is cheaper to buy concession!\n")
                                    .append("MRT concession costs: $48.00 monthly.\n")
                                    .append("You should set your transport budget at $48.00 monthly\n\n");
                            budgetRecommendation.put("transport", Parser.parseMoney("48.00"));
                        } else if (monthlyCost.compareTo(BigDecimal.ZERO) == 1) {
                            recommendation.append("You should set transport budget at $")
                                    .append(monthlyCost)
                                    .append(" monthly. \n\n");
                        }
                        break;
                    case "BUS":
                        if (monthlyCost.compareTo(BigDecimal.valueOf(52)) > 0) {
                            recommendation.append("Based on your travelling habits, "
                                    + "it is cheaper to buy concession!\n")
                                    .append("MRT concession costs: $52.00 monthly.\n")
                                    .append("You should set your transport budget at $52.00 monthly\n");
                            budgetRecommendation.put("transport", Parser.parseMoney("52.00"));
                        } else {
                            recommendation.append("You should set transport budget at $")
                                    .append(monthlyCost)
                                    .append(" monthly. \n");
                            budgetRecommendation.put("transport", monthlyCost);
                        }
                        break;
                    default:
                        if (monthlyCost.compareTo(BigDecimal.valueOf(85)) > 0) {
                            recommendation.append("Based on your travelling habits, "
                                    + "it is cheaper to buy concession!\n"
                                    + "Combined concession costs: $85.00 monthly.\n"
                                    + "You should set your transport budget at $85.00 monthly\n\n");
                            budgetRecommendation.put("transport", Parser.parseMoney("85.00"));
                        } else {
                            recommendation.append("You should set transport budget at $")
                                    .append(monthlyCost)
                                    .append(" monthly. \n\n");
                            budgetRecommendation.put("transport", monthlyCost);
                        }
                        break;
                    }
                    int mealsPerDay = Integer.parseInt(planAttributes.get("MEALS_PER_DAY"));
                    if (mealsPerDay > 0) {
                        BigDecimal costPerMeal = Parser.parseMoney(planAttributes.get("AVERAGE_MEAL_COST"));
                        BigDecimal monthlyFoodBudget = costPerMeal
                                .multiply(BigDecimal.valueOf(mealsPerDay))
                                .multiply(BigDecimal.valueOf(30));
                        if (monthlyFoodBudget.compareTo(BigDecimal.ZERO) == 1) {
                            recommendation.append("I'd suggest you set your food budget at $")
                                    .append(monthlyFoodBudget)
                                    .append(" monthly. \n\n");
                            budgetRecommendation.put("food ", monthlyFoodBudget);
                        }
                    }
                } else {
                    //Stays on campus
                    recommendation.append("Since you live in campus, "
                            + "you can just allocate a small budget of $10 to transport! \n\n");
                    budgetRecommendation.put("transport", Parser.parseMoney("10"));
                    if (planAttributes.get("DINE_IN_HALL").equals("TRUE")) {
                        BigDecimal costPerMeal = Parser.parseMoney(planAttributes.get("AVERAGE_MEAL_COST"));
                        BigDecimal monthlyFoodBudget = costPerMeal
                                .multiply(BigDecimal.valueOf(4))
                                .multiply(BigDecimal.valueOf(11));
                        //11 since 3 meals during each weekend * 1 meal per day
                        if (monthlyFoodBudget.compareTo(BigDecimal.ZERO) == 1) {
                            recommendation.append("I'd suggest you set your food budget at $")
                                    .append(monthlyFoodBudget).append(" monthly. \n\n");
                            budgetRecommendation.put("food", monthlyFoodBudget);
                        }
                    } else {
                        //Eats all meals outside of hall
                        int mealsPerDay = Integer.parseInt(planAttributes.get("MEALS_PER_DAY"));
                        BigDecimal costPerMeal = Parser.parseMoney(planAttributes.get("AVERAGE_MEAL_COST"));
                        BigDecimal monthlyFoodBudget = costPerMeal.multiply(BigDecimal
                                .valueOf(mealsPerDay))
                                .multiply(BigDecimal.valueOf(30));
                        if (monthlyFoodBudget.compareTo(BigDecimal.ZERO) == 1) {
                            recommendation.append("I'd suggest you set your food budget at $")
                                    .append(monthlyFoodBudget).append(" monthly. \n\n");
                            budgetRecommendation.put("food", monthlyFoodBudget);
                        }
                    }
                }
                BigDecimal phoneBill = Parser.parseMoney(planAttributes.get("PHONE_BILL"));
                if (!phoneBill.equals(Parser.parseMoney("0"))) {
                    recommendation.append("You set set a budget of $")
                            .append(phoneBill)
                            .append(" for your phone bill.\n\n");
                    budgetRecommendation.put("phone bill", phoneBill);
                    Expense.Builder phoneBillExpenseBuilder = new Expense.Builder();
                    phoneBillExpenseBuilder.setAmount(phoneBill);
                    phoneBillExpenseBuilder.setDescription("Phone bill");
                    phoneBillExpenseBuilder.setRecurring(true);
                    phoneBillExpenseBuilder.setTag("phone bill");
                    recommendationExpenseList.add(phoneBillExpenseBuilder.build());
                }
                if (planAttributes.get("NETFLIX").equals("TRUE")) {
                    recommendation.append("Netflix has a family plan that is $17.00 per month,"
                            + " so its cheaper if you can find friends to share!\n"
                            + "You should allocate $4.25 to netflix\n\n");
                    budgetRecommendation.put("netflix", Parser.parseMoney("4.25"));
                    budgetRecommendation.put("phone bill", phoneBill);
                    Expense.Builder netflixExpenseBuilder = new Expense.Builder();
                    netflixExpenseBuilder.setAmount("4.25");
                    netflixExpenseBuilder.setDescription("Netflix");
                    netflixExpenseBuilder.setRecurring(true);
                    netflixExpenseBuilder.setTag("netflix");
                    recommendationExpenseList.add(netflixExpenseBuilder.build());
                }
                if (planAttributes.get("MUSIC_SUBSCRIPTION").equals("TRUE")) {
                    recommendation.append("Spotify has a student plan that is only $5 a month! \n"
                            + "You should allocate $5 to Spotify\n\n");
                    budgetRecommendation.put("spotify", Parser.parseMoney("5"));
                    Expense.Builder spotifyExpenseBuilder = new Expense.Builder();
                    spotifyExpenseBuilder.setAmount("5.00");
                    spotifyExpenseBuilder.setDescription("Spotify");
                    spotifyExpenseBuilder.setRecurring(true);
                    spotifyExpenseBuilder.setTag("spotify");
                    recommendationExpenseList.add(spotifyExpenseBuilder.build());
                }
                if (Parser.parseMoney(planAttributes.get("ONLINE_SHOPPING")).compareTo(BigDecimal.ZERO) == 1) {
                    budgetRecommendation.put("online shopping", Parser.parseMoney(planAttributes.get("ONLINE_SHOPPING")));
                    recommendation.append("You should allocate $"
                            + planAttributes.get("ONLINE_SHOPPING")
                            + " to online shopping.");
                }
            }
        } catch (NullPointerException | NumberFormatException e) {
            throw new DukeException("Missing attributes to make recommendation!" + e.getMessage());
        }
        if (recommendation.toString().isEmpty()) {
            return new PlanRecommendation("I can't make any recommendations for you"
                    + " :(. Something probably went wrong",
                    budgetRecommendation,
                    recommendationExpenseList);
        }
        logger.info("Recommendation made successfully!");
        return new PlanRecommendation(recommendation.toString(),
                budgetRecommendation,
                recommendationExpenseList);
    }

    private String[] generateIntRange(int start, int end) {
        List<String> strings = new ArrayList<String>();
        for (int i = start; i <= end; ++i) {
            strings.add(Integer.toString(i));
        }
        return strings.toArray(new String[0]);
    }

    /**
     * Simple container for recommendation.
     */
    public class PlanRecommendation {
        String recommendation;
        Map<String, BigDecimal> budget;
        List<Expense> recommendationExpenseList;

        /**
         * Constructor for PlanRecommendation.
         *
         * @param recommendation            String of the recommendation, text that will appear in the dialog
         * @param budget                    Map&lt;String, BigDecimal> map of category as key and amount as the value
         * @param recommendationExpenseList List of expenses to add into expense list.
         */
        public PlanRecommendation(String recommendation,
                                  Map<String, BigDecimal> budget,
                                  List<Expense> recommendationExpenseList) {
            this.recommendation = recommendation;
            this.budget = budget;
            this.recommendationExpenseList = recommendationExpenseList;
        }

        public String getRecommendation() {
            return recommendation;
        }

        public Map<String, BigDecimal> getPlanBudget() {
            return budget;
        }

        public List<Expense> getRecommendationExpenseList() {
            return recommendationExpenseList;
        }
    }


}

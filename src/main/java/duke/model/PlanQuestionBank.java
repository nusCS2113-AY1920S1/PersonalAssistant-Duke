package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;

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


}

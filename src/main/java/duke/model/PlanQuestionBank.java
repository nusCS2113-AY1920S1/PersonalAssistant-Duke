package duke.model;

import duke.exception.DukeException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class PlanQuestionBank {
    private Map<Integer, PlanQuestion> questionList;

    private static final String[] BOOL_ANSWERS = {"yes", "y", "no", "n"};
    private static final String[] BOOL_ATTRIBUTE_VALUES = {"TRUE", "TRUE", "FALSE", "FALSE"};


    public PlanQuestionBank() throws DukeException {
        this.questionList = new HashMap<>();
        questionList.put(1, new PlanQuestion(1,
                "Are you a student from NUS?",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "NUS_STUDENT", new HashSet<>(Arrays.asList(2))));
        questionList.put(2, new PlanQuestion(2, "Do you live on campus?", BOOL_ANSWERS, BOOL_ATTRIBUTE_VALUES, "CAMPUS_LIFE"));

    }


    public Collection<PlanQuestion> getInitialQuestions(Map<String, String> knownAttributes) {
        Map<String, PlanQuestion> attributeQuestion = new HashMap<>();
        Queue<Integer> questionsToAdd = new LinkedList<>();
        questionsToAdd.add(1);
        while (!questionsToAdd.isEmpty()) {
            Integer index = questionsToAdd.peek();
            questionsToAdd.remove();
            PlanQuestion question = questionList.get(index);
            attributeQuestion.put(question.getAttribute(), question);
            Set<Integer> children = questionList.get(index).getNeighbouringQuestions();
            for (Integer child : children) {
                questionsToAdd.add(child);
            }
        }
        for (String knownAttribute : knownAttributes.keySet()) {
            attributeQuestion.remove(knownAttribute);
        }
        return attributeQuestion.values();
    }


}

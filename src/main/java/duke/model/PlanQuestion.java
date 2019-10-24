package duke.model;

import duke.exception.DukeException;
import duke.logic.Parser.Parser;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class PlanQuestion {
    private String question;
    private Map<String, String> answersAttributesValue;
    private Map<String, Set<Integer>> neighbouringQuestions;
    private String attribute;

    /**
     * Constructor for PlanQuestion.
     * @param question String the question we are asking the user
     * @param answers an Array of strings of the possible answers
     * @param attributeValue an Array of Attributes the attribute could take,
     *                       its size should be the same as the answer array
     * @param attribute the attribute of the user we want to determine from the question
     * @throws DukeException when there are errors in the construction of the question
     */
    public PlanQuestion(String question,
                        String[] answers,
                        String[] attributeValue,
                        String attribute) throws DukeException {
        this.question = question;
        this.answersAttributesValue = new HashMap<>();
        int answersSize = answers.length;
        if (attributeValue.length < answersSize) {
            answersSize = attributeValue.length;
            throw new DukeException("Some question was set up incorrectly!!! This shouldn't have happened!");
        }
        for (int i = 0; i < answersSize; ++i) {
            answersAttributesValue.put(answers[i], attributeValue[i]);
        }
        this.attribute = attribute;
        this.neighbouringQuestions = new HashMap<>();
    }


    public String getQuestion() {
        return question;
    }

    public String getAttribute() {
        return attribute;
    }

    public Set<Integer> getNeighbouringQuestions(String attribute) {
        if (neighbouringQuestions.containsKey(attribute)) {
            return neighbouringQuestions.get(attribute);
        }
        return new HashSet<>();
    }

    Reply getReply(String input, Map<String, String> attributes) throws DukeException {
        try {

            if (answersAttributesValue.size() == 1) {
                if (answersAttributesValue.containsKey("DOUBLE")) {
                    BigDecimal scaledAmount = Parser.parseMoney(input);
                    String attributeVal = scaledAmount.toString();
                    attributes.put(attribute, attributeVal);
                    return new Reply("Ok noted!", attributes);
                }

            } else {
                if (!answersAttributesValue.containsKey(input.toUpperCase())) {
                    throw new NoSuchElementException();
                }
                String attributeVal = answersAttributesValue.get(input.toUpperCase());
                attributes.put(attribute, attributeVal);
                return new Reply("Ok noted!", attributes);
            }

        } catch (NoSuchElementException | NumberFormatException e) {
            throw new DukeException("Please enter a valid reply!");
        }
        return new Reply("Something strange happened", attributes);
    }

    public void addNeighbouring(String attribute, Integer neighbouring) {
        if (neighbouringQuestions.containsKey(attribute)) {
            neighbouringQuestions.get(attribute).add(neighbouring);
        } else {
            neighbouringQuestions.put(attribute, new HashSet<>(Collections.singletonList(neighbouring)));
        }
    }

    static class Reply {
        private String text;
        private Map<String, String> attributes;

        Reply(String text, Map<String, String> attributes) {
            this.text = text;
            this.attributes = attributes;
        }

        String getText() {
            return text;
        }

        Map<String, String> getAttributes() {
            return attributes;
        }
    }


}

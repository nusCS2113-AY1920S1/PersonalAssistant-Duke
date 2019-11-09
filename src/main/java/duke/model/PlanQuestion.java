package duke.model;

import duke.exception.DukeException;
import duke.logic.parser.Parser;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A single question that is being asked to the user.
 */
public class PlanQuestion {
    private String question;
    private Map<String, String> answersAttributesValue;
    private Map<String, Set<Integer>> neighbouringQuestions;
    private String attribute;

    private final static String SUCCESS_MESSAGE = "Ok noted!";
    private final static String DOUBLE = "DOUBLE";

    /**
     * Constructor for PlanQuestion.
     *
     * @param question       String the question we are asking the user
     * @param answers        an Array of strings of the possible answers
     * @param attributeValue an Array of Attributes the attribute could take,
     *                       its size should be the same as the answer array
     * @param attribute      the attribute of the user we want to determine from the question
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

    /**
     * Returns a set of Integers of neighbouring questions given an attribute.
     *
     * @param attribute the attribute we want to get the neighbours of.
     * @return a set of indexes of neighbouring questions, an empty set if no enighboring quesion
     */
    public Set<Integer> getNeighbouringQuestions(String attribute) {
        if (answersAttributesValue.containsKey(DOUBLE) && (neighbouringQuestions.get(DOUBLE) != null)) {
            return neighbouringQuestions.get(DOUBLE);
        }
        if (neighbouringQuestions.containsKey(attribute)) {
            return neighbouringQuestions.get(attribute);
        }
        return new HashSet<>();
    }

    /**
     * Returns a success message if the input provided is a valid one and
     * the question is successfully processed.
     *
     * @param input      the input string for the question
     * @param attributes the currently known attributes about the user
     * @return Reply containing the updated attributes and success message
     * @throws DukeException
     */
    Reply getReply(String input, Map<String, String> attributes) throws DukeException {
        try {
            if (answersAttributesValue.size() == 1) {
                if (answersAttributesValue.containsKey(DOUBLE)) {
                    BigDecimal scaledAmount = Parser.parseMoney(input);
                    String attributeVal = scaledAmount.toString();
                    attributes.put(attribute, attributeVal);
                    return new Reply(SUCCESS_MESSAGE, attributes);
                }
            } else {
                if (!answersAttributesValue.containsKey(input.toUpperCase())) {
                    throw new NoSuchElementException();
                }
                String attributeVal = answersAttributesValue.get(input.toUpperCase());
                attributes.put(attribute, attributeVal);
                return new Reply(SUCCESS_MESSAGE, attributes);
            }
        } catch (NoSuchElementException | NumberFormatException | NullPointerException e) {
            throw new DukeException("Please enter a valid reply!");
        }
        return new Reply("Something strange happened", attributes);
    }

    /**
     * Adds a neighbouring question's index to every attribute value.
     *
     * @param neighbouring Integer index of neighbouring question
     */
    public void addNeighbouring(Integer neighbouring) {
        if (answersAttributesValue.containsKey(DOUBLE)) {
            if (neighbouringQuestions.containsKey(DOUBLE)) {
                neighbouringQuestions.get(DOUBLE).add(neighbouring);
            } else {
                neighbouringQuestions.put(DOUBLE, new HashSet<>(Collections.singletonList(neighbouring)));
            }
        }
        for (String attributeValue : answersAttributesValue.values()) {
            if (neighbouringQuestions.containsKey(attributeValue)) {
                neighbouringQuestions.get(attributeValue).add(neighbouring);
            } else {
                neighbouringQuestions.put(attributeValue, new HashSet<>(Collections.singletonList(neighbouring)));
            }
        }
    }

    /**
     * Adds a neighbouring question's index to a specific attribute value.
     *
     * @param neighbouring   Integer index of neighbouring question
     * @param attributeValue String of the attributeValue we want our questions to be mapped to
     */
    public void addNeighbouring(String attributeValue, Integer neighbouring) throws DukeException {
        if (!answersAttributesValue.containsValue(attributeValue)) {
            throw new DukeException(attributeValue + " is not a valid attribute value for " + attribute);
        } else if (neighbouringQuestions.containsKey(attributeValue)) {
            neighbouringQuestions.get(attributeValue).add(neighbouring);
        } else {
            neighbouringQuestions.put(attributeValue, new HashSet<>(Collections.singletonList(neighbouring)));
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

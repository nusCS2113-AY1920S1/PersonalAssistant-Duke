package duke.model;

import duke.exception.DukeException;
import duke.logic.Parser.Parser;

import java.math.BigDecimal;
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

    /**
     * Returns a set of Integers of neighbouring questions given an attribute.
     *
     * @param attributeValue the attribute value we want to get the neighbours of.
     * @return
     */
    public Set<Integer> getNeighbouringQuestions(String attributeValue) throws DukeException {
        if (answersAttributesValue.containsKey("DOUBLE") && (neighbouringQuestions.get("DOUBLE") != null)) {
            return neighbouringQuestions.get("DOUBLE");
        }
        if (!neighbouringQuestions.containsKey(attributeValue)) {
            throw new DukeException("No such attributeValue: " + attributeValue);
        }
        return neighbouringQuestions.get(attributeValue);
    }

    Reply getReply(String input, Map<String, String> attributes) throws DukeException {
        try {
            if (answersAttributesValue.size() == 1 && answersAttributesValue.containsKey("DOUBLE")) {
                BigDecimal scaledAmount = Parser.parseMoney(input);
                String attributeVal = scaledAmount.toString();
                attributes.put(attribute, attributeVal);
                return new Reply("Ok noted!", attributes);
            } else {
                if (!answersAttributesValue.containsKey(input.toUpperCase())) {
                    throw new NoSuchElementException();
                }
                String attributeVal = answersAttributesValue.get(input.toUpperCase());
                attributes.put(attribute, attributeVal);
                return new Reply("Ok noted!", attributes);
            }
        } catch (NoSuchElementException | NumberFormatException | NullPointerException e) {
            throw new DukeException("Please enter a valid reply!");
        }
    }

    /**
     * Adds a neighbouring question's index to every attribute value.
     *
     * @param neighbouring Integer index of neighbouring question
     */
    public void addNeighbouring(Integer neighbouring) {
        if (answersAttributesValue.containsKey("DOUBLE")) {
            if (neighbouringQuestions.containsKey("DOUBLE")) {
                neighbouringQuestions.get("DOUBLE").add(neighbouring);
            } else {
                neighbouringQuestions.put("DOUBLE", new HashSet<>(Collections.singletonList(neighbouring)));
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
        if (!this.answersAttributesValue.containsValue(attributeValue)) {
            throw new DukeException("No such value " + attributeValue + "in the question!");
        } else {
            if (neighbouringQuestions.containsKey(attributeValue)) {
                neighbouringQuestions.get(attributeValue).add(neighbouring);
            } else {
                neighbouringQuestions.put(attributeValue, new HashSet<>(Collections.singletonList(neighbouring)));
            }
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

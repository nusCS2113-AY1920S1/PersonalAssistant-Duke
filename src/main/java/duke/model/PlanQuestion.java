package duke.model;

import duke.exception.DukeException;
import duke.logic.Parser.Parser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class PlanQuestion {
    private Integer questionNumber;
    private String question;
    private Map<String, String> answersAttributesValue;
    private Map<String,Set<Integer>> neighbouringQuestions;
    private String attribute;


    public PlanQuestion(int index, String question, String[] answers, String[] attributeValue, String attribute) throws DukeException {
        this.questionNumber = index;
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
        if(neighbouringQuestions.containsKey(attribute)){
            return neighbouringQuestions.get(attribute);
        }
        return new HashSet<>();
    }

    public Reply getReply(String input, Map<String, String> attributes) throws DukeException {
        try {

            if(answersAttributesValue.size() == 1) {
                if(answersAttributesValue.containsKey("DOUBLE")) {
                    BigDecimal scaledAmount = Parser.parseMoney(input) ;
                    String attributeVal = scaledAmount.toString();
                    attributes.put(attribute, attributeVal);
                    return new Reply("Ok noted!", attributes);
                }

            }else {
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
        if(neighbouringQuestions.keySet().contains(attribute)) {
            neighbouringQuestions.get(attribute).add(neighbouring);
        } else {
            neighbouringQuestions.put(attribute, new HashSet<>(Arrays.asList(neighbouring)));
        }
    }

    public class Reply {
        private String text;
        private Map<String, String> attributes;

        public Reply(String text, Map<String, String> attributes) {
            this.text = text;
            this.attributes = attributes;
        }

        public String getText() {
            return text;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }
    }


}

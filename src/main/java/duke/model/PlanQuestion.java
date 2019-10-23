package duke.model;

import duke.exception.DukeException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class PlanQuestion {
    private Integer questionNumber;
    private String question;
    private Map<String, String> answersAttributesValue;
    private Set<Integer> neighbouringQuestions;
    private String attribute;

    public PlanQuestion(int index, String question, String[] answers, String[] attributeValue, String attribute, Set<Integer> neighbouringQuestions) throws DukeException {
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
        this.neighbouringQuestions = neighbouringQuestions;
    }

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
        this.neighbouringQuestions = new HashSet<>();
    }

    public String getQuestion() {
        return question;
    }

    public String getAttribute() {
        return attribute;
    }

    public Set<Integer> getNeighbouringQuestions() {
        return neighbouringQuestions;
    }

    public Reply getReply(String input, Map<String, String> attributes) throws DukeException {
        try {
            if (!answersAttributesValue.containsKey(input.toLowerCase())) {
                throw new NoSuchElementException();
            }
            String attributeVal = answersAttributesValue.get(input.toLowerCase());
            attributes.put(attribute, attributeVal);
            return new Reply("Ok noted!", attributes);
        } catch (NoSuchElementException e) {
            throw new DukeException("Please enter a valid reply!");
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

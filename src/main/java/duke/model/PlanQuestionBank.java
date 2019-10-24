package duke.model;

import duke.exception.DukeException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlanQuestionBank {
    private int curr;
    private List<String> questionList;
    private List<String> expectedAnswerType;

    public PlanQuestionBank(){
        questionList = new ArrayList<>();
        expectedAnswerType = new ArrayList<>();
        questionList.add("Are you a student from NUS?");
        expectedAnswerType.add("bool");
        curr = 0;
    }

    public Reply getReply(String input, Map<String,String> attributes){
        if(expectedAnswerType.get(curr) == "bool"){
            try {
                parseBool(input);
                attributes.put("NUS_STUDENT", "TRUE");
                return new Reply("Noted", attributes);
            } catch (DukeException e) {
                return new Reply(e.getMessage(), attributes);
            }
        }
        return new Reply("Something strange happened, please try again", attributes);
    }

    public String getCurrentQuestion() {
        return questionList.get(curr);
    }

    private boolean parseBool(String input) throws DukeException {
        input = input.toLowerCase();
        switch (input) {
        case "yes":
        case "y":
            return true;
        case "no":
        case "n":
            return false;
        default:
            throw new DukeException("Your answer should be a yes/no!");


        }
    }


    private BigDecimal parseMoney(String input) {
            double moneyDouble = Double.parseDouble(input);
            BigDecimal money = BigDecimal.valueOf(moneyDouble);
            return money.setScale(2, RoundingMode.HALF_EVEN);
    }

    public class Reply {
        private String text;
        private Map<String,String> attributes;

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

package com.algosenpai.app.logic.chapters.chapter3;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

public class BitwiseOperatorQuestion extends Question {

    private static int firstNumber;
    private static int secondNumber;
    private static String bitwiseOperator;
    private static int ans;

    BitwiseOperatorQuestion() {
        //Bounds the two numbers to be operated on between 40 and 129.
        firstNumber = getRandomNumber(40,90);
        secondNumber = getRandomNumber(40,90);
        //Generates a random number to decide which operator to use.
        int randomNumber = getRandomNumber(0,3);
        switch (randomNumber) {
        case 0 :
            bitwiseOperator = " AND ";
            ans = firstNumber & secondNumber;
            break;
        case 1 :
            bitwiseOperator = " OR ";
            ans = firstNumber | secondNumber;
            break;
        case 2 :
            bitwiseOperator = " XOR ";
            ans = firstNumber ^ secondNumber;
            break;
        default :
            bitwiseOperator = null;
            ans = -1;
            break;
        }
        answer = String.valueOf(ans);
        questionFormatter();
        reviewCalculation();
    }

    private void reviewCalculation() {
        rtlm = new ReviewTracingListModel();
        rtlm.addReviewStep("The first number when represented in bits : ");
        StringBuilder firstString = new StringBuilder();
        firstString.append(Integer.toBinaryString(firstNumber));
        rtlm.addReviewStep(firstString.toString());
        rtlm.addReviewStep("The second number when represented in bits : ");
        StringBuilder secondString = new StringBuilder();
        secondString.append(Integer.toBinaryString(secondNumber));
        rtlm.addReviewStep(secondString.toString());
        rtlm.addReviewStep("Reverse both strings, and apply the operator" + bitwiseOperator
                + "to each bit : ");
        firstString.reverse();
        secondString.reverse();
        rtlm.addReviewStep("These are the new strings.");
        rtlm.addReviewStep("\t" + firstString.toString() + "\n" + bitwiseOperator + "\n\t"
                + secondString.toString());
        rtlm.addReviewStep("-------------------------------------------");
        StringBuilder output = new StringBuilder();
        output.append(Integer.toBinaryString(ans));
        output.reverse();
        rtlm.addReviewStep("\t" + output);
        rtlm.addReviewStep("Reverse this output to give you " + ans);
    }

    @Override
    public void questionFormatter() {
        question = "What is the integer result of " + firstNumber + bitwiseOperator + secondNumber + "?\n";
    }

}

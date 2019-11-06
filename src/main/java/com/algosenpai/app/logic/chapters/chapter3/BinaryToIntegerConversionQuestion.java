package com.algosenpai.app.logic.chapters.chapter3;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

public class BinaryToIntegerConversionQuestion extends Question {

    private static String binaryRepresentation;

    BinaryToIntegerConversionQuestion() {
        int ans = getRandomNumber(200,300);
        answer = String.valueOf(ans);
        binaryRepresentation = Integer.toBinaryString(ans);
        reviewCalculation();
        questionFormatter();
    }

    @Override
    public void questionFormatter() {
        question = "What is the integer representation of binary value "
                + binaryRepresentation + "?";
    }

    /**
     * Generates the review for the question.
     */
    private void reviewCalculation() {
        rtlm = new ReviewTracingListModel();
        rtlm.addReviewTracingModel("This is the current binary value.");
        rtlm.addReviewTracingModel(binaryRepresentation);
        rtlm.addReviewTracingModel("First we reverse the value.");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(binaryRepresentation);
        stringBuilder.reverse();
        rtlm.addReviewTracingModel("This is the new binary value.");
        rtlm.addReviewTracingModel(stringBuilder.toString());
        rtlm.addReviewTracingModel("From the index 0, we begin doing our calculations.");
        rtlm.addReviewTracingModel("int counter = 0");
        int counter = 0;
        for (int i = 0; i < stringBuilder.length(); i++) {
            rtlm.addReviewTracingModel("counter += " + stringBuilder.charAt(i) + "pow(2 , " + i + ")");
        }
        rtlm.addReviewTracingModel("This is the final counter : " + counter);
    }
}

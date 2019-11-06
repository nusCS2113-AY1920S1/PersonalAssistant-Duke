package com.algosenpai.app.logic.chapters.chapter3;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

public class LeastSignificantBitQuestion extends Question {

    private static int number;

    LeastSignificantBitQuestion() {
        number = getRandomNumber(2000,2100);
        rtlm = new ReviewTracingListModel();
        rtlm.addReviewStep("This is the decimal number we are working with : " + number);
        rtlm.addReviewStep("We start by converting the number to its binary form.");
        questionFormatter();
        answer = String.valueOf(calculateLeastSigBit(number));
    }

    @Override
    public void questionFormatter() {
        question = "What is the index of the Least Significant Bit"
                + " in the binary representation of " + number + "?\n"
                + "(first bit that is 1 counted from the right, 0-based indexing)\n";
    }

    /**
     * Calculates the position of the least significant bit of an integer.
     * @param number The number to be calculated.
     * @return The position of the least significant bit.
     */
    private static int calculateLeastSigBit(int number) {
        int counter = 0;
        rtlm.addReviewStep("Set index to 0.");
        rtlm.addReviewStep("while number is even, we divide it by 2 and increment counter.");
        while (number % 2 == 0) {
            counter++;
            rtlm.addReviewStep("Increment index to " + counter);
            number /= 2;
            rtlm.addReviewStep("Divide number by 2 to get " + number);
        }
        rtlm.addReviewStep("Now the number is odd.");
        rtlm.addReviewStep("We know the index is : " + counter);
        return counter;
    }

}

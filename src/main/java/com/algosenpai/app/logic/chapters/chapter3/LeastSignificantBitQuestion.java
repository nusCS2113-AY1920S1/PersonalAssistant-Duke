package com.algosenpai.app.logic.chapters.chapter3;

import com.algosenpai.app.logic.chapters.Question;

public class LeastSignificantBitQuestion extends Question {

    private static int number;

    LeastSignificantBitQuestion() {
        number = getRandomNumber(2000,2100);
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
        while (number % 2 == 0) {
            counter++;
            number /= 2;
        }
        return counter;
    }

}

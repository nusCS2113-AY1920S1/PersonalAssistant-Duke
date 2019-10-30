package com.algosenpai.app.logic.chapters.chapter3;

import com.algosenpai.app.logic.chapters.Question;

public class CountingBitsQuestion extends Question {

    private static int number;
    private static char bitToFind;

    CountingBitsQuestion() {
        //This bounds the number between 40 and 129.
        number = getRandomNumber(40,90);
        String binaryRepresentation = Integer.toBinaryString(number);
        //This bounds the value to be either 0 or 1.
        int val = getRandomNumber(0,2);
        //Decide if bit '1' or '0' should be found.
        bitToFind = getBitToFind(val);
        //Format the question
        questionFormatter();
        int ans = calculateBits(binaryRepresentation, bitToFind);
        answer = String.valueOf(ans);
    }

    @Override
    public void questionFormatter() {
        question = "How many bit(s) in the binary representation of " + number + " is/are " + bitToFind + "?\n";
    }

    /**
     * Calculates the number of bits that match the given character to be found.
     * @param binaryRepresentation The binary representation of the number to be searched.
     * @param bitToFind The particular bit to be searched.
     * @return The number of bits that match the bit to be found.
     */
    private static int calculateBits(String binaryRepresentation, char bitToFind) {
        int counter = 0;
        for (int i = 0; i < binaryRepresentation.length(); i++) {
            if (binaryRepresentation.charAt(i) == bitToFind) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Determines the bit to be searched according to the value.
     * @param val A value between 0 and 1 which determines the bit to be found.
     * @return the bit to be found.
     */
    private static char getBitToFind(int val) {
        char bitToFind;
        //This selects the bit to be searched.
        if (val == 0) {
            bitToFind = '0';
        } else {
            bitToFind = '1';
        }
        return bitToFind;
    }

}

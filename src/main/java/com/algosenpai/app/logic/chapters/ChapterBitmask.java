package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.Random;

class ChapterBitmask {

    private static Random random = new Random();

    /**
     * Generates a random question related to bitmasking.
     * @return A question model containing to the random number being generated.
     */
    static QuestionModel generateQuestions() {
        int questionType = getRandomNumber(0,5);
        switch (questionType) {
        case 0 :
            return leastSignificantBitQuestion();
        case 1 :
            return binaryToIntegerConversionQuestion();
        case 3 :
            return countingBitsQuestion();
        case 4 :
            return bitwiseOperatorQuestion();
        default :
            return null;
        }
    }

    /**
     * Generates a question related to the bitwise operations AND, XOR, OR. 
     * User will be required to find the result of the expression when the bitwise operator is used on 
     * two random numbers.
     * @return The QuestionModel containing the question and the answer.
     */
    private static QuestionModel bitwiseOperatorQuestion() {
        String bitwiseOperator;
        //Bounds the two numbers to be operated on between 40 and 129.
        int firstNumber = getRandomNumber(40,90);
        int secondNumber = getRandomNumber(40,90);
        int answer;
        //Generates a random number to decide which operator to use.
        int randomNumber = getRandomNumber(0,3);
        switch (randomNumber) {
        case 0 :
            bitwiseOperator = " AND ";
            answer = firstNumber & secondNumber;
            break;
        case 1 :
            bitwiseOperator = " OR ";
            answer = firstNumber | secondNumber;
            break;
        case 2 :
            bitwiseOperator = " XOR ";
            answer = firstNumber ^ secondNumber;
            break;
        default :
            bitwiseOperator = null;
            answer = -1;
            break;
        }
        String question = bitwiseOperationQuestionGenerator(bitwiseOperator, firstNumber, secondNumber);
        return new QuestionModel(question, String.valueOf(answer), new ReviewTracingListModel());
    }

    /**
     * Generates a question related to bit matching.
     * 
     * @return The QuestionModel containing the question and the answer.
     */
    private static QuestionModel countingBitsQuestion() {
        //This bounds the number between 40 and 129. 
        int number = getRandomNumber(40,90);
        String binaryRepresentation = Integer.toBinaryString(number);
        //This bounds the value to be either 0 or 1.
        int val = getRandomNumber(0,2);
        //Decide if bit '1' or '0' should be found.
        char bitToFind = getBitToFind(val);
        //Format the question
        String question = countingBitsQuestionGenerator(number, bitToFind);
        int answer = calculateBits(binaryRepresentation, bitToFind);
        return new QuestionModel(question, String.valueOf(answer), new ReviewTracingListModel());
    }

    /**
     * Generates a question related to finding the integer value of a given binary
     * string.
     * 
     * @return The QuestionModel containing the question and the answer.
     */
    private static QuestionModel binaryToIntegerConversionQuestion() {
        //This bounds the number between 40 and 129.
        int answer = getRandomNumber(40,90);
        String binaryRepresentation = Integer.toBinaryString(answer);
        String question = binaryToIntegerConversionQuestionGenerator(binaryRepresentation);
        return new QuestionModel(question, String.valueOf(answer), new ReviewTracingListModel());
    }

    /**
     * Generates a question related to finding the position of the least significant
     * bit in an integer.
     * 
     * @return The QuestionModel containing the question and the answer.
     */
    private static QuestionModel leastSignificantBitQuestion() {
        //This bounds the number between 40 and 99.
        int number = getRandomNumber(40,90);
        String question = leastSigBitQuestionGenerator(number);
        String answer = String.valueOf(calculateLeastSigBit(number));
        return new QuestionModel(question, answer, new ReviewTracingListModel());
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

    /**
     * Generates an integer.
     * @param minimum The minimum value of the integer generated.
     * @param bound Any value below this could be added to minimum.
     * @return The integer which is randomly generated.
     */
    private static int getRandomNumber(int minimum, int bound) {
        return random.nextInt(bound) + minimum;
    }

    /**
     * Formats the question for the bitwiseOperationQuestion.
     * @param bitwiseOperator The bitwise operator to be used.
     * @param firstNumber The first number to be operated with.
     * @param secondNumber  The second number to be operated with.
     * @return The formatted question.
     */
    private static String bitwiseOperationQuestionGenerator(String bitwiseOperator, int firstNumber, int secondNumber) {
        return "What is the integer result of " + firstNumber + bitwiseOperator + secondNumber + "?";
    }

    /**
     * Formats the question for the countingBitsQuestion.
     * @param number The number in decimal form.
     * @param bitToFind The particular bit to find in the binary form of the number.
     * @return The formatted String question.
     */
    private static String countingBitsQuestionGenerator(int number, char bitToFind) {
        return "How many bit(s) in the binary representation of " + number + " is/are " + bitToFind + "?";
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

    /**
     * Formats the question for the binarytoIntegerConversionQuestion.
     * @param binaryRepresentation The String containing the binary representation of an integer.
     * @return The formatted String question.
     */
    private static String binaryToIntegerConversionQuestionGenerator(String binaryRepresentation) {
        return "What is the integer representation of binary value "
                + binaryRepresentation + "?";
    }

    /**
     * Formats the question for the leastSignificantBitQuestion.
     * @param number The number in decimal form.
     * @return The formatted String.
     */
    private static String leastSigBitQuestionGenerator(int number) {
        return "What is the index of the Least Significant Bit (first bit that is 1"
                + " counted from the right, 0-based indexing) in the binary representation of " + number + "?";
    }



}


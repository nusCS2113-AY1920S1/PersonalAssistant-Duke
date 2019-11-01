package com.algosenpai.app.logic.chapters.chapter1;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.ArrayList;
import java.util.HashSet;

public class BubbleSortPassesQuestion extends Question {

    // Size of the array.
    private static int arraySize;
    // The array in the question.
    private static ArrayList<Integer> initialArray;
    private static int passes;

    BubbleSortPassesQuestion() {
        // Determines a random size for the array between 5 and 9.
        arraySize = getRandomNumber(5, 5);
        // Populates the array.
        initialArray = new ArrayList<>(generateArray(arraySize));
        // Determines the number of passes.
        passes = getRandomNumber(1, arraySize - 2);
        run();
    }

    /**
     * This is used for testing.
     * @param arraySize The given array size.
     * @param initialArray The actual array.
     * @param passes The number of passes to be carried out.
     */
    public BubbleSortPassesQuestion(int arraySize, ArrayList<Integer> initialArray,int passes) {
        BubbleSortPassesQuestion.arraySize = arraySize;
        BubbleSortPassesQuestion.initialArray = initialArray;
        BubbleSortPassesQuestion.passes = passes;
        run();
    }

    /**
     * Runs the remaining code.
     */
    private void run() {
        questionFormatter();
        bubbleSort(initialArray, passes);
        answer = initialArray.toString();
        answer = answer.substring(1, answer.length() - 1);
    }

    @Override
    public void questionFormatter() {
        question = "An array of " + arraySize + " elements underwent the following Bubble Sort Algorithm : "
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + passes + " passes?\n"
                + "Please provide your answer in comma-separated format. e.g. x, y, z, ...\n\n";
        question += "for (int i = 0; i < passes; i++) {\n" + "   for (int j = 0; j < arr.size - 1 - i; j ++) {\n"
                + "       if (arr[j] > arr[j + 1]) {\n" + "            swap (arr[j], arr[j+1]);\n" + "       }\n"
                + "   }\n" + "}\n\n";
    }

    /**
     * Generates the set of values to be used for the arrays.
     * 
     * @param arraySize The size of the set to be used.
     * @return The hashset to be used for the array.
     */
    private HashSet<Integer> generateArray(int arraySize) {
        HashSet<Integer> tempStorage = new HashSet<>();
        while (tempStorage.size() != arraySize) {
            int value = getRandomNumber(1, 50);
            tempStorage.add(value);
        }
        return tempStorage;
    }

    /**
     * Bubble Sorts the array.
     * 
     * @param arr    The arraylist to be sorted.
     * @param passes The number of passes before the program gets terminated.
     */
    private static void bubbleSort(ArrayList<Integer> arr, int passes) {
        rtlm = new ReviewTracingListModel();
        rtlm.addReviewTracingModel("This is the array at the start.");
        rtlm.addReviewTracingModel(arr.toString());
        for (int i = 0; i < passes; i++) {
            rtlm.addReviewTracingModel("Pass " + (i + 1) + " : ");
            for (int j = 0; j < arr.size() - 1 - i; j++) {
                int first = arr.get(j);
                int second = arr.get(j + 1);
                if (first > second) {
                    rtlm.addReviewTracingModel("Since " + first + " is smaller than " + second + ", swap.");
                    arr.set(j, second);
                    arr.set(j + 1, first);
                } else {
                    rtlm.addReviewTracingModel("Since " + first + " is larger than " + second + ", ignore.");
                }
                rtlm.addReviewTracingModel(arr.toString());
            }
            rtlm.addReviewTracingModel("This is the array after " + (i + 1) + " pass");
            rtlm.addReviewTracingModel(arr.toString());
        }
    }
}
package com.algosenpai.app.logic.chapters.chapter1;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.ArrayList;
import java.util.HashSet;

public class SelectionSortSwapQuestion extends Question {

    // Size of the array.
    private static int arraySize;
    // The array in the question.
    private static ArrayList<Integer> initialArray;
    private static int swaps;

    SelectionSortSwapQuestion() {
        // Generates a size to be used for the array between 4 and 8.
        arraySize = getRandomNumber(7, 4);
        // Populates the array with integers
        initialArray = new ArrayList<>(generateArray(arraySize));
        // Determine the number of swaps to be performed.
        swaps = getRandomNumber(1, arraySize - 2);
        // Generates the question from the variables provided.
        questionFormatter();
        selectionSort(initialArray, swaps);
        answer = initialArray.toString();
        answer = answer.substring(1, answer.length() - 1);
    }

    @Override
    public void questionFormatter() {
        question = "An array of " + arraySize + " elements underwent the following Selection Sort Algorithm : \n"
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + swaps + " swaps?\n"
                + "Please provide your answer in space-separated format. e.g. x y z ...\n";
        question += "int i, j, min_idx;\n" + "for (i = 0; i < n-1; i++) {\n" + "\tmin_idx = i;\n"
                + "\tfor (j = i+1; j < n; j++) {\n" + "\t\tif (arr[j] < arr[min_idx]) {\n" + "\t\t\tmin_idx = j;\n"
                + "\t\t}\n" + "\t}\n" + "\tif (min_idx != i) {\n" + "\t\tswap(arr[min_idx], arr[i]);\n" + "\t}\n"
                + "}\n";
    }

    /**
     * Generates the set of values to be used for the arrays.
     * 
     * @param arraySize The size of the set to be used.
     * @return The hashset to be used for the array.
     */
    private static HashSet<Integer> generateArray(int arraySize) {
        HashSet<Integer> tempStorage = new HashSet<>();
        while (tempStorage.size() != arraySize) {
            int value = getRandomNumber(0, 100);
            tempStorage.add(value);
        }
        return tempStorage;
    }

    /**
     * Conducts Selection Sort on the given ArrayList.
     * 
     * @param arr   The ArrayList to be sorted.
     * @param swaps The number of swaps before the program terminates.
     */
    private static void selectionSort(ArrayList<Integer> arr, int swaps) {
        rtlm = new ReviewTracingListModel();
        rtlm.addReviewStep("This is the array at the start.");
        rtlm.addReviewStep(arr.toString());
        int i;
        int j;
        int minIdx;
        int counter = 0;
        rtlm.addReviewStep("Counter = " + counter + ".");
        for (i = 0; i < arr.size() - 1; i++) {
            minIdx = i;
            rtlm.addReviewStep("Start with minIndex " + minIdx + ".");
            for (j = i + 1; j < arr.size(); j++) {
                rtlm.addReviewStep("Compare to " + arr.get(j) + ".");
                if (arr.get(j) < arr.get(minIdx)) {
                    rtlm.addReviewStep("Since " + arr.get(j) + " is smaller, change minIndex to "
                            + j + ".");
                    minIdx = j;
                } else {
                    rtlm.addReviewStep("Ignore " + arr.get(j) + " since it is bigger.");
                }
            }
            rtlm.addReviewStep("Swap " + arr.get(minIdx) + " with " + arr.get(i) + ".");
            int temp = arr.get(minIdx);
            arr.set(minIdx, arr.get(i));
            arr.set(i, temp);
            rtlm.addReviewStep("This is the new array.");
            rtlm.addReviewStep(arr.toString());
            if (minIdx != i) {
                rtlm.addReviewStep("Counter = " + counter + ".");
                counter++;
            }
            if (counter == swaps) {
                rtlm.addReviewStep("Since counter == swaps, stop.");
                rtlm.addReviewStep("This is the final array.");
                rtlm.addReviewStep(arr.toString());
                return;
            }
        }
    }
}
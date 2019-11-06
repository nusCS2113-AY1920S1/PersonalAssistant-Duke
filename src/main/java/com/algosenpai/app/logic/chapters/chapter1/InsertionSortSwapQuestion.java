package com.algosenpai.app.logic.chapters.chapter1;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.ArrayList;
import java.util.HashSet;

public class InsertionSortSwapQuestion extends Question {

    private static int arraySize;
    private static ArrayList<Integer> initialArray;
    private static int swaps;

    InsertionSortSwapQuestion() {
        // Generates a size to be used for the array between 4 and 8.
        arraySize = getRandomNumber(5, 4);
        // Populates the array with integers
        initialArray = new ArrayList<>(generateArray(arraySize));
        // Determine the number of swaps to be performed.
        swaps = getRandomNumber(1, arraySize - 2);
        // Generates the question from the variables provided.
        questionFormatter();
        insertionSort(initialArray, swaps);
        answer = initialArray.toString();
        answer = answer.substring(1, answer.length() - 1);
    }

    @Override
    public void questionFormatter() {
        question = "An array of " + arraySize + " elements underwent the following Insertion Sort Algorithm : \n"
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + swaps + " swaps?\n"
                + "Please provide your answer in space-separated format. e.g. x y z ...\n\n";
        question += "int i, key, j;\n" + "for (i = 1; i < n; i++) {\n" + "    key = arr[i];\n"
                + "    j = i - 1;\n" + "    while (j >= 0 && arr[j] > key) {\n" + "        arr[j + 1] = arr[j];\n"
                + "        j = j - 1;\n" + "    }\n" + "    arr[j + 1] = key;\n" + "}\n";
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
            int value = getRandomNumber(1, 50);
            tempStorage.add(value);
        }
        return tempStorage;
    }

    /**
     * Conducts Insertion Sort on the given ArrayList.
     * 
     * @param arr   The array to conduct the Insertion Sort algorithm on.
     * @param swaps The number of swaps to be performed before the algorithm
     */
    private static void insertionSort(ArrayList<Integer> arr, int swaps) {
        rtlm = new ReviewTracingListModel();
        rtlm.addReviewStep("This is the array at the start.");
        rtlm.addReviewStep(arr.toString());
        int i;
        int key;
        int j;
        int counter = 0;
        rtlm.addReviewStep("Counter = " + counter + ".");
        for (i = 1; i < arr.size(); i++) {
            key = arr.get(i);
            rtlm.addReviewStep("Pick " + key + ".");
            j = i - 1;
            rtlm.addReviewStep("Traverse backwards");
            while (j >= 0 && arr.get(j) > key) {
                rtlm.addReviewStep("Since " + arr.get(j) + " is more than " + key + ", swap.");
                arr.set(j + 1, arr.get(j));
                j = j - 1;
                counter++;
                rtlm.addReviewStep("Counter = " + counter + ".");
                if (counter == swaps) {
                    rtlm.addReviewStep("Since counter == swaps, stop.");
                    break;
                }
            }
            arr.set(j + 1, key);
            if (counter == swaps) {
                rtlm.addReviewStep("This is the final array.");
                rtlm.addReviewStep(arr.toString());
                return;
            }
            if (j == -1) {
                rtlm.addReviewStep("Since we have reached the front, check next.");
            } else {
                rtlm.addReviewStep("Since " + arr.get(j) + " is smaller than " + arr.get(i) + ", check next.");
            }
        }

    }

}
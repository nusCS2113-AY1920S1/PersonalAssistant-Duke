package com.algosenpai.app.logic.chapters.chapter1;

import com.algosenpai.app.logic.chapters.Question;

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
    }

    @Override
    public void questionFormatter() {
        question = "An array of " + arraySize + " elements underwent the following Insertion Sort Algorithm : \n"
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + swaps + " swaps?\n\n";
        question += "int i, key, j;\n" + "for (i = 1; i < n; i++) {\n" + "    key = arr[i];\n"
                + "    j = i - 1;\n" + "    while (j >= 0 && arr[j] > key) {\n" + "        arr[j + 1] = arr[j];\n"
                + "        j = j - 1;\n" + "    }\n" + "    arr[j + 1] = key;\n" + "} \n\n";
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
        int i;
        int key;
        int j;
        int counter = 0;
        for (i = 1; i < arr.size(); i++) {
            key = arr.get(i);
            j = i - 1;
            while (j >= 0 && arr.get(j) > key) {
                arr.set(j + 1, arr.get(j));
                j = j - 1;
                counter++;
                if (counter == swaps) {
                    break;
                }
            }
            arr.set(j + 1, key);
            if (counter == swaps) {
                return;
            }
        }
    }

}
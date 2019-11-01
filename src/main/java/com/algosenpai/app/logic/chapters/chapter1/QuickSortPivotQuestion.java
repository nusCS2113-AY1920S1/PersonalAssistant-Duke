package com.algosenpai.app.logic.chapters.chapter1;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class QuickSortPivotQuestion extends Question {

    private static int arraySize;
    private static ArrayList<Integer> initialArray;
    // A static array container to store elements from initialArray
    private static Integer[] arr;

    QuickSortPivotQuestion() {
        // Generates a size to be used for the array between 6 and 11.
        arraySize = getRandomNumber(6, 6);
        // Populates the array with integers.
        initialArray = new ArrayList<>(generateArray(arraySize));
        // Converts to a static array.
        arr = initialArray.toArray(new Integer[arraySize]);
        // Data structure containing the elements after all the steps of quickSort
        ArrayList<Integer[]> allSteps = new ArrayList<>();
        quickSort(arr, 0, arraySize - 1, allSteps);
        arr = allSteps.get(getRandomNumber(0, allSteps.size() - 1));
        initialArray = new ArrayList<>(Arrays.asList(arr));
        questionFormatter();
        answer = quickSortPivotAnswerGenerator();
    }

    /**
     * This is used for testing.
     * @param arraySize The given array size.
     * @param initialArray The given array.
     */
    QuickSortPivotQuestion(int arraySize, ArrayList<Integer> initialArray) {
        QuickSortPivotQuestion.arraySize = arraySize;
        QuickSortPivotQuestion.initialArray = initialArray;
        arr = initialArray.toArray(new Integer[arraySize]);
        // Data structure containing the elements after all the steps of quickSort
        ArrayList<Integer[]> allSteps = new ArrayList<>();
        quickSort(arr, 0, arraySize - 1, allSteps);
        arr = allSteps.get(getRandomNumber(0, allSteps.size() - 1));
        QuickSortPivotQuestion.initialArray = new ArrayList<>(Arrays.asList(arr));
        questionFormatter();
        answer = quickSortPivotAnswerGenerator();
    }


    @Override
    public void questionFormatter() {
        question = "An array of " + arraySize
                + " elements underwent some passes of the Quick Sort Algorithm to become : \n" + initialArray + "\n";
        question += "How many elements could possibly have been the pivot?\n\n";
    }

    /**
     * Calculates the answer for the quickSortPivotQuestion.
     * 
     * @return The answer for the question in a String format.
     */
    private static String quickSortPivotAnswerGenerator() {
        rtlm = new ReviewTracingListModel();
        rtlm.addReviewTracingModel("This is the array after some number of passes of Quick Sort.");
        rtlm.addReviewTracingModel(initialArray.toString());
        rtlm.addReviewTracingModel("The trick is to traverse from left to right.");
        rtlm.addReviewTracingModel("Rule #1 Number is bigger than all on left.");
        rtlm.addReviewTracingModel("Rule #2 Number is smaller than all on right.");
        int answer = 0;
        rtlm.addReviewTracingModel("Counter = " + answer + ".");
        for (int i = 0; i < arraySize; i++) {
            boolean canBePivot = true;
            rtlm.addReviewTracingModel("Referenced number is " + arr[i]);
            for (int j = 0; j < arraySize; j++) {
                if (i == j) {
                    continue;
                }
                if ((j < i && arr[j] > arr[i]) || (j > i && arr[j] < arr[i])) {
                    rtlm.addReviewTracingModel("One of the rules are broken when second number is "
                            + arr[j] + ".");
                    canBePivot = false;
                    break;
                }
            }
            if (canBePivot) {
                rtlm.addReviewTracingModel("Number " + arr[i] + " is a valid pivot.");
                answer++;
                rtlm.addReviewTracingModel("Counter = " + answer + ".");
            }
        }
        rtlm.addReviewTracingModel("Final count : " + answer + ".");
        return String.valueOf(answer);
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
     * Quick Sorts the array.
     * 
     * @param arr      The array to be sorted.
     * @param low      The start of the partitioning.
     * @param high     The end of the partitioning.
     * @param allSteps The arraylist which takes a snapshot after each step.
     */
    private static void quickSort(Integer[] arr, int low, int high, ArrayList<Integer[]> allSteps) {
        if (low < high) {
            int partitionIndex = partition(arr, low, high, allSteps);
            quickSort(arr, low, partitionIndex - 1, allSteps);
            quickSort(arr, partitionIndex + 1, high, allSteps);
        }
    }

    /**
     * Partitions the array according to the Quick Sort Algorithm.
     * 
     * @param arr      The array to be sorted.
     * @param low      The start of the partitioning.
     * @param high     The end of the partitioning.
     * @param allSteps The arraylist which takes a snapshot after each step.
     * @return An integer which is the partitioning index.
     */
    private static int partition(Integer[] arr, int low, int high, ArrayList<Integer[]> allSteps) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        Integer[] tempArray = Arrays.copyOf(arr, arr.length);
        allSteps.add(tempArray);
        return (i + 1);
    }

}
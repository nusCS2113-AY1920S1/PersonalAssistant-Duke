package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.logic.Question;
import com.algosenpai.app.model.ReviewTracingListModel;
import com.algosenpai.app.model.ReviewTracingModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;

public class ChapterSorting {

    private static Random random = new Random();
    private static Scanner s = new Scanner(System.in);
    /**
     * Generates the question by using a random number to determine which of the
     * sub-questions to ask.
     * 
     * @return A question class that contains the question and expected answer.
     */

    public static Question generateQuestions() {
        int questionType = random.nextInt(4);
        switch (questionType) {
        case 0:
            return bubbleSortPassesQuestion();
        case 1:
            return quickSortPivotsQuestion();
        case 2:
            return insertionSortSwapsQuestion();
        case 3:
            return selectionSortSwapsQuestion();
        default:
            return null;
        }
    }

    /**
     * Generates the Selection Sort Algorithm question related to the sequence of
     * integers after a certain number of swaps. It determines a random array size,
     * then runs the algorithm on it for certain number of swaps, after which it
     * will terminate.
     * 
     * @return the question class with the question.
     */
    private static Question selectionSortSwapsQuestion() {
        int arraySize = random.nextInt(4) + 5;
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        int swaps = random.nextInt(arraySize - 5) + 1;
        String question = "An array of " + arraySize + " elements underwent the following Selection Sort Algorithm : "
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + swaps + " swaps?\n";
        question += "int i, j, min_idx;\n" + "   for (i = 0; i < n-1; i++) {\n" + "       min_idx = i;\n"
                + "       for (j = i+1; j < n; j++) {\n" + "           if (arr[j] < arr[min_idx]) {\n"
                + "               min_idx = j;\n" + "       }\n" + "       }\n" + "   if (min_idx != i) {"
                + "       swap(arr[min_idx], arr[i]);\n" + "   }" + "}\n";
        ReviewTracingListModel rtlm = new ReviewTracingListModel();
        selectionSort(initialArray, swaps, rtlm);
        Question newQuestion = new Question(question, initialArray.toString(), rtlm);
        return newQuestion;
    }

    /**
     * Generates the Insertion Sort Algorithm question regarding the sequence of
     * integers after a certain number of swaps. It determines a random array size,
     * then conducts Insertion Sort on it. A random number of swaps is generated,
     * after which Insertion Sort stops.
     * 
     * @return the question class with the question.
     */
    private static Question insertionSortSwapsQuestion() {
        int arraySize = random.nextInt(4) + 5;
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        int swaps = random.nextInt(arraySize - 2) + 1;
        String question = "An array of " + arraySize + " elements underwent the following Insertion Sort Algorithm : "
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + swaps + " swaps?\n\n";
        question += "int i, key, j;\n" + "for (i = 1; i < n; i++) {\n" + "    key = arr[i];\n"
                + "    j = i - 1;\n" + "    while (j >= 0 && arr[j] > key) {\n" + "        arr[j + 1] = arr[j];\n"
                + "        j = j - 1;\n" + "    }\n" + "    arr[j + 1] = key;\n" + "} \n\n";
        ReviewTracingListModel rtlm = new ReviewTracingListModel();
        insertionSort(initialArray, swaps, rtlm);
        Question newQuestion = new Question(question, initialArray.toString(), rtlm);
        return newQuestion;
    }

    /**
     * Generates the QuickSort Algorithm question regarding number of possible
     * pivots after certain passes of the algorithm. It determines a random array
     * size, then conducts QuickSort on it. A random snapshot of the array is taken
     * for the question and the answer is pre-determined.
     * 
     * @return the question class with the question.
     */
    private static Question quickSortPivotsQuestion() {
        int arraySize = random.nextInt(6) + 6;
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        Integer[] arr = initialArray.toArray(new Integer[arraySize]);
        ArrayList<Integer[]> allSteps = new ArrayList<>();
        quickSort(arr, 0, arraySize - 1, allSteps);
        int numberOfChoices = allSteps.size() - 1;
        arr = allSteps.get(random.nextInt(numberOfChoices));
        initialArray = new ArrayList<>(Arrays.asList(arr));
        String question = "An array of " + arraySize
                + " elements underwent some passes of the Quick Sort Algorithm to become : " + initialArray + "\n";
        question += "How many elements could possibly have been the pivot?\n\n";
        int answer = 0;
        for (int i = 0; i < arraySize; i++) {
            Boolean canBePivot = true;
            for (int j = 0; j < arraySize; j++) {
                if (i == j) {
                    continue;
                }
                if ((j < i && arr[j] > arr[i]) || (j > i && arr[j] < arr[i])) {
                    canBePivot = false;
                    break;
                }
            }
            if (canBePivot) {
                answer++;
            }
        }
        Question newQuestion = new Question(question, Integer.toString(answer), new ReviewTracingListModel());
        return newQuestion;
    }

    /**
     * Generates the BubbleSort Algorithm question regarding the sequence of
     * integers after a certain number of passes. It determines a random array size,
     * then fills the array with unique numbers. A random number of passes is
     * determined and the BubbleSort Algorithm stops midway.
     * 
     * @return the question class with the question.
     */
    private static Question bubbleSortPassesQuestion() {
        int arraySize = random.nextInt(7) + 3;
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        int passes = random.nextInt(arraySize - 2) + 1;
        String question = "An array of " + arraySize + " elements underwent the following Bubble Sort Algorithm : "
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + passes + " passes?\n\n";
        question += "for (int i = 0; i < passes; i++) {\n"
                + "   for (int j = 0; j < arr.size - 1 - i; j ++) {\n" + "       if (arr[j] > arr[j + 1]) {\n"
                + "            swap (arr[j], arr[j+1]);\n" + "       }\n" + "   }\n" + "}\n\n";
        ReviewTracingListModel rtlm = new ReviewTracingListModel();
        bubbleSort(initialArray, passes, rtlm);
        Question newQuestion = new Question(question, initialArray.toString(), rtlm);
        return newQuestion;
    }

    /**
     * Conducts Selection Sort on the given ArrayList.
     * @param arr   The ArrayList to be sorted.
     * @param swaps The number of swaps before the program terminates.
     * @param rtlm  The steps taken in the solution
     */
    private static void selectionSort(ArrayList<Integer> arr, int swaps, ReviewTracingListModel rtlm) {
        int i;
        int j;
        int minIdx;
        int counter = 0;
        for (i = 0; i < arr.size() - 1; i++) {
            minIdx = i;
            for (j = i + 1; j < arr.size(); j++) {
                if (arr.get(j) < arr.get(minIdx)) {
                    minIdx = j;
                }
            }
            int temp = arr.get(minIdx);
            arr.set(minIdx, arr.get(i));
            arr.set(i, temp);
            if (minIdx != i) {
                counter++;
            }
            if (counter == swaps) {
                return;
            }
        }
    }

    /**
     * Conducts Insertion Sort on the given ArrayList.
     *  @param arr   The array to conduct the Insertion Sort algorithm on.
     * @param swaps The number of swaps to be performed before the algorithm
     * @param rtlm  The steps taken in the solution
     */
    private static void insertionSort(ArrayList<Integer> arr, int swaps, ReviewTracingListModel rtlm) {
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

    /**
     * Bubble Sorts the array.
     * @param arr    The arraylist to be sorted.
     * @param passes The number of passes before the program gets terminated.
     * @param rtlm   The steps taken in the solution
     */
    private static void bubbleSort(ArrayList<Integer> arr, int passes, ReviewTracingListModel rtlm) {
        for (int i = 0; i < passes; i++) {
            for (int j = 0; j < arr.size() - 1 - i; j++) {
                int first = arr.get(j);
                int second = arr.get(j + 1);
                rtlm.addReviewTracingModel(new ReviewTracingModel(j, j + 1, "c"));
                if (first > second) {
                    rtlm.addReviewTracingModel(new ReviewTracingModel(j, j + 1, "s"));
                    int temp = first;
                    arr.set(j, second);
                    arr.set(j + 1, temp);
                }
            }
        }
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

    /**
     * Generates the set of values to be used for the arrays.
     * 
     * @param arraySize The size of the set to be used.
     * @return The hashset to be used for the array.
     */
    private static HashSet<Integer> generateArray(int arraySize) {
        HashSet<Integer> tempStorage = new HashSet<>();
        while (tempStorage.size() != arraySize) {
            int value = random.nextInt(100);
            tempStorage.add(value);
        }
        return tempStorage;
    }

}
package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.models.ReviewTracingListModel;
import com.algosenpai.app.logic.models.ReviewTracingModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.HashSet;

class ChapterSorting {

    private static Random random = new Random();

    /**
     * Generates the question by using a random number to determine which of the
     * sub-questions to ask.
     * 
     * @return A question class that contains the question and expected answer.
     */

    static QuestionModel generateQuestions() {
        int questionType = getRandomNumber(0, 3);
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
    private static QuestionModel selectionSortSwapsQuestion() {
        //Generates a size to be used for the array between 4 and 8.
        int arraySize = getRandomNumber(5,4);
        //Populates the array with integers
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        //Determine the number of swaps to be performed.
        int swaps = getRandomNumber(1,arraySize - 5);
        //Generates the question from the variables provided.
        String question = selectionSortSwapsQuestionGenerator(arraySize, initialArray, swaps);
        ReviewTracingListModel rtlm = new ReviewTracingListModel();
        selectionSort(initialArray, swaps, rtlm);
        return new QuestionModel(question, initialArray.toString(), rtlm);
    }

    /**
     * Generates the Insertion Sort Algorithm question regarding the sequence of
     * integers after a certain number of swaps. It determines a random array size,
     * then conducts Insertion Sort on it. A random number of swaps is generated,
     * after which Insertion Sort stops.
     * 
     * @return the question class with the question.
     */
    private static QuestionModel insertionSortSwapsQuestion() {
        //Generates a size to be used for the array between 4 and 8.
        int arraySize = getRandomNumber(5,4);
        //Populates the array with integers
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        //Determine the number of swaps to be performed.
        int swaps = getRandomNumber(1, arraySize - 2);
        // Generates the question from the variables provided.
        String question = insertionSortSwapQuestionGenerator(arraySize, initialArray, swaps);
        ReviewTracingListModel rtlm = new ReviewTracingListModel();
        insertionSort(initialArray, swaps, rtlm);
        return new QuestionModel(question, initialArray.toString(), rtlm);
    }

    /**
     * Generates the QuickSort Algorithm question regarding number of possible
     * pivots after certain passes of the algorithm. It determines a random array
     * size, then conducts QuickSort on it. A random snapshot of the array is taken
     * for the question and the answer is pre-determined.
     * 
     * @return the question class with the question.
     */
    private static QuestionModel quickSortPivotsQuestion() {
        //Generates a size to be used for the array between 6 and 11.
        int arraySize = getRandomNumber(6,6);
        //Populates the array with integers.
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        //Converts to a static array.
        Integer[] arr = initialArray.toArray(new Integer[arraySize]);
        ArrayList<Integer[]> allSteps = new ArrayList<>();
        quickSort(arr, 0, arraySize - 1, allSteps);
        arr = allSteps.get(getRandomNumber(0, allSteps.size() - 1));
        initialArray = new ArrayList<>(Arrays.asList(arr));
        String question = quickSortPivotQuestionGenerator(arraySize, initialArray);
        String answer = quickSortPivotAnswerGenerator(arraySize, arr);
        return new QuestionModel(question, answer, new ReviewTracingListModel());
    }

    /**
     * Generates the BubbleSort Algorithm question regarding the sequence of
     * integers after a certain number of passes. It determines a random array size,
     * then fills the array with unique numbers. A random number of passes is
     * determined and the BubbleSort Algorithm stops midway.
     * 
     * @return the question class with the question.
     */
    private static QuestionModel bubbleSortPassesQuestion() {
        //Generates a size to be used for the array between 3 and 9.
        int arraySize = getRandomNumber(3,7);
        //Populates the array with integers.
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        //Determines the number of passes.
        int passes = getRandomNumber(1,arraySize - 2);
        String question = bubbleSortPassesQuestionGenerator(arraySize, initialArray, passes);
        ReviewTracingListModel rtlm = new ReviewTracingListModel();
        bubbleSort(initialArray, passes, rtlm);
        return new QuestionModel(question, initialArray.toString(), rtlm);
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
                    arr.set(j, second);
                    arr.set(j + 1, first);
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
            int value = getRandomNumber(0,100);
            tempStorage.add(value);
        }
        return tempStorage;
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
     * Formats the question for the selectionSortSwapsQuestion.
     * @param arraySize The size of the array.
     * @param initialArray The arraylist to be shown to the user.
     * @param swaps The number of swaps to be conducted.
     * @return The string containing the formatted question.
     */
    private static String selectionSortSwapsQuestionGenerator(int arraySize, ArrayList<Integer> initialArray,
                                                              int swaps) {
        String question = "An array of " + arraySize + " elements underwent the following Selection Sort Algorithm : "
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + swaps + " swaps?\n";
        question += "int i, j, min_idx;\n" + "   for (i = 0; i < n-1; i++) {\n" + "       min_idx = i;\n"
                + "       for (j = i+1; j < n; j++) {\n" + "           if (arr[j] < arr[min_idx]) {\n"
                + "               min_idx = j;\n" + "       }\n" + "       }\n" + "   if (min_idx != i) {"
                + "       swap(arr[min_idx], arr[i]);\n" + "   }" + "}\n";
        return question;
    }

    /**
     * Formats the question for the insertionSortSwapsQuestion.
     * @param arraySize The size of the array.
     * @param initialArray The arraylist to be shown to the user.
     * @param swaps The number of swaps to be conducted.
     * @return The string containing the formatted question.
     */
    private static String insertionSortSwapQuestionGenerator(int arraySize, ArrayList<Integer> initialArray,
                                                             int swaps) {
        String question = "An array of " + arraySize + " elements underwent the following Insertion Sort Algorithm : "
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + swaps + " swaps?\n\n";
        question += "int i, key, j;\n" + "for (i = 1; i < n; i++) {\n" + "    key = arr[i];\n"
                + "    j = i - 1;\n" + "    while (j >= 0 && arr[j] > key) {\n" + "        arr[j + 1] = arr[j];\n"
                + "        j = j - 1;\n" + "    }\n" + "    arr[j + 1] = key;\n" + "} \n\n";
        return question;
    }

    /**
     * Formats the question for the quickSortPivotQuestion.
     * @param arraySize The size of the array.
     * @param initialArray The arraylist to be shown to the user.
     * @return The string containing the formatted question.
     */
    private static String quickSortPivotQuestionGenerator(int arraySize, ArrayList<Integer> initialArray) {
        String question = "An array of " + arraySize
                + " elements underwent some passes of the Quick Sort Algorithm to become : " + initialArray + "\n";
        question += "How many elements could possibly have been the pivot?\n\n";
        return question;
    }

    /**
     * Calculates the answer for the quickSortPivotQuestion.
     * @param arraySize The size of the array.
     * @param arr The array in the question.
     * @return The answer for the question in a String format.
     */
    private static String quickSortPivotAnswerGenerator(int arraySize, Integer[] arr) {
        int answer = 0;
        for (int i = 0; i < arraySize; i++) {
            boolean canBePivot = true;
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
        return String.valueOf(answer);
    }

    /**
     * Formats the question for the bubbleSortPassesQuestion.
     * @param arraySize The size of the array.
     * @param initialArray The arraylist to be shown to the user.
     * @param passes The number of passes to be conducted.
     * @return The string containing the formatted question.
     */
    private static String bubbleSortPassesQuestionGenerator(int arraySize, ArrayList<Integer> initialArray,
                                                            int passes) {
        String question = "An array of " + arraySize + " elements underwent the following Bubble Sort Algorithm : "
                + initialArray + "\n";
        question += "What would be the new configuration of the elements after " + passes + " passes?\n\n";
        question += "for (int i = 0; i < passes; i++) {\n"
                + "   for (int j = 0; j < arr.size - 1 - i; j ++) {\n" + "       if (arr[j] > arr[j + 1]) {\n"
                + "            swap (arr[j], arr[j+1]);\n" + "       }\n" + "   }\n" + "}\n\n";
        return question;
    }

}
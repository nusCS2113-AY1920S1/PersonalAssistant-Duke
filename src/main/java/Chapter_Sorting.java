import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;

class Chapter_Sorting {

    private static Random random = new Random();
    private static Scanner s = new Scanner(System.in);

    /**
     * Generates the question by using a random number to determine which of the
     * subquestions to ask.
     * 
     * @return True if a correct answer is given and false if the answer is wrong.
     */
    public Boolean generateQuestions() {
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
            return true;
        }
    }

    /**
     * Generates the Selection Sort Algorithm question related to the sequence of
     * integers after a certain number of swaps. It determines a random array size,
     * then runs the algorithm on it for certain number of swaps, after which it
     * will terminate. An input is taken in from the user as an answer.
     * 
     * @return True if the input matches the answer, and false if the answer is
     *         wrong.
     */
    private Boolean selectionSortSwapsQuestion() {
        int arraySize = random.nextInt(4) + 5;
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        int swaps = random.nextInt(arraySize - 5) + 1;
        System.out.println("An array of " + arraySize + " elements underwent the following Selection Sort Algorithm : "
                + initialArray);
        System.out.println("What would be the new configuration of the elements after " + swaps + " swaps?\n");
        String code = new String("int i, j, min_idx;\n" + "   for (i = 0; i < n-1; i++) {\n" + "       min_idx = i;\n"
                + "       for (j = i+1; j < n; j++) {\n" + "           if (arr[j] < arr[min_idx]) {\n"
                + "               min_idx = j;\n" + "       }\n" + "       }\n" + "   if (min_idx != i) {"
                + "       swap(arr[min_idx], arr[i]);\n" + "   }" + "}\n");
        System.out.println(code);
        String userInput = s.nextLine();
        String[] arr = userInput.split(", ");
        ArrayList<Integer> userAnswer = new ArrayList<>();
        for (String i : arr) {
            userAnswer.add(Integer.parseInt(i));
        }
        selectionSort(initialArray, swaps);
        if (userAnswer.equals(initialArray)) {
            System.out.println("Well Done! You got a correct answer!\n");
            return true;
        } else {
            System.out.println("This is the correct answer : " + initialArray + "\nTry harder!\n");
            return false;
        }
    }

    /**
     * Generates the Insertion Sort Algorithm question regarding the sequence of
     * integers after a certain number of swaps. It determines a random array size,
     * then conducts Insertion Sort on it. A random number of swaps is generated,
     * after which Insertion Sort stops. An input is taken in from the user as an
     * answer.
     * 
     * @return True if a correct answer is given, and false if the answer does not
     *         match.
     */
    private static Boolean insertionSortSwapsQuestion() {
        int arraySize = random.nextInt(4) + 5;
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        int swaps = random.nextInt(arraySize - 2) + 1;
        System.out.println("An array of " + arraySize + " elements underwent the following Insertion Sort Algorithm : "
                + initialArray);
        System.out.println("What would be the new configuration of the elements after " + swaps + " swaps?\n");

        String code = new String("int i, key, j;\n" + "for (i = 1; i < n; i++) {\n" + "    key = arr[i];\n"
                + "    j = i - 1;\n" + "    while (j >= 0 && arr[j] > key) {\n" + "        arr[j + 1] = arr[j];\n"
                + "        j = j - 1;\n" + "    }\n" + "    arr[j + 1] = key;\n" + "} \n");
        System.out.println(code);
        String userInput = s.nextLine();
        String[] arr = userInput.split(", ");
        ArrayList<Integer> userAnswer = new ArrayList<>();
        for (String i : arr) {
            userAnswer.add(Integer.parseInt(i));
        }
        insertionSort(initialArray, swaps);
        if (userAnswer.equals(initialArray)) {
            System.out.println("Well Done! You got a correct answer!\n");
            return true;
        } else {
            System.out.println("This is the correct answer : " + initialArray + "\nTry harder!\n");
            return false;
        }
    }

    /**
     * Generates the QuickSort Algorithm question regarding number of possible
     * pivots after certain passes of the algorithm. It determines a random array
     * size, then conducts QuickSort on it. A random snapshot of the array is taken
     * for the question and the answer is pre-determined. An input is taken in from
     * the user as an answer.
     * 
     * @return True if the answer is correct, false if the answer is wrong.
     */
    private static Boolean quickSortPivotsQuestion() {
        int arraySize = random.nextInt(6) + 6;
        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        Integer[] arr = initialArray.toArray(new Integer[arraySize]);
        ArrayList<Integer[]> allSteps = new ArrayList<>();
        quickSort(arr, 0, arraySize - 1, allSteps);
        int numberOfChoices = allSteps.size() - 1;
        arr = allSteps.get(random.nextInt(numberOfChoices));
        initialArray = new ArrayList<>(Arrays.asList(arr));
        System.out.println("An array of " + arraySize
                + " elements underwent some passes of the Quick Sort Algorithm to become : " + initialArray);
        System.out.println("How many elements could possibly have been the pivot?\n");
        int userInput = s.nextInt();
        s.nextLine();
        int answer = 0;
        for (int i = 0; i < arraySize; i++) {
            Boolean canBePivot = true;
            for (int j = 0; j < arraySize; j++) {
                if (i == j)
                    continue;
                if ((j < i && arr[j] > arr[i]) || (j > i && arr[j] < arr[i])) {
                    canBePivot = false;
                    break;
                }
            }
            if (canBePivot) {
                answer++;
            }
        }

        if (userInput == answer) {
            System.out.println("Well Done! You got a correct answer!\n");
            return true;
        } else {
            System.out.println("This is the correct answer : " + answer + "\nTry harder!\n");
            return false;
        }

    }

    /**
     * Generates the BubbleSort Algorithm question regarding the sequence of
     * integers after a certain number of passes. It determines a random array size,
     * then fills the array with unique numbers. A random number of passes is
     * determined and the BubbleSort Algorithm stops midway. The user then has to
     * decide what the correct answer is.
     * 
     * @return true if the answer matches, and false if the answer is wrong.
     */
    private static Boolean bubbleSortPassesQuestion() {
        int arraySize = random.nextInt(7) + 3;

        ArrayList<Integer> initialArray = new ArrayList<>(generateArray(arraySize));
        int passes = random.nextInt(arraySize - 2) + 1;
        System.out.println("An array of " + arraySize + " elements underwent the following Bubble Sort Algorithm : "
                + initialArray);
        System.out.println("What would be the new configuration of the elements after " + passes + " passes?\n");
        String code = new String("for (int i = 0; i < passes; i++) {\n"
                + "   for (int j = 0; j < arr.size - 1 - i; j ++) {\n" + "       if (arr[j] > arr[j + 1]) {\n"
                + "            swap (arr[j], arr[j+1]);\n" + "       }\n" + "   }\n" + "}\n");
        System.out.println(code);
        String userInput = s.nextLine();
        String[] arr = userInput.split(", ");
        ArrayList<Integer> userAnswer = new ArrayList<>();
        for (String i : arr) {
            userAnswer.add(Integer.parseInt(i));
        }
        bubbleSort(initialArray, passes);
        if (userAnswer.equals(initialArray)) {
            System.out.println("Well Done! You got a correct answer!\n");
            return true;
        } else {
            System.out.println("This is the correct answer : " + initialArray + "\nTry harder!\n");
            return false;
        }
    }

    /**
     * Conducts Selection Sort on the given ArrayList.
     * 
     * @param arr   The ArrayList to be sorted.
     * @param swaps The number of swaps before the program terminates.
     */
    private void selectionSort(ArrayList<Integer> arr, int swaps) {
        int i, j, min_idx;
        int counter = 0;
        for (i = 0; i < arr.size() - 1; i++) {
            min_idx = i;
            for (j = i + 1; j < arr.size(); j++) {
                if (arr.get(j) < arr.get(min_idx)) {
                    min_idx = j;
                }
            }
            int temp = arr.get(min_idx);
            arr.set(min_idx, arr.get(i));
            arr.set(i, temp);
            if (min_idx != i) {
                counter++;
            }
            if (counter == swaps) {
                return;
            }
        }
    }

    /**
     * Conducts Insertion Sort on the given ArrayList.
     * 
     * @param arr   The array to conduct the Insertion Sort algorithm on.
     * @param swaps The number of swaps to be performed before the algorithm
     *              terminates.
     */
    private static void insertionSort(ArrayList<Integer> arr, int swaps) {
        int i, key, j;
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
     * 
     * @param arr    The arraylist to be sorted.
     * @param passes The number of passes before the program gets terminated.
     */
    private static void bubbleSort(ArrayList<Integer> arr, int passes) {
        for (int i = 0; i < passes; i++) {
            for (int j = 0; j < arr.size() - 1 - i; j++) {
                int first = arr.get(j);
                int second = arr.get(j + 1);
                if (first > second) {
                    int temp = first;
                    arr.set(j, second);
                    arr.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Quick Sorts the array
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
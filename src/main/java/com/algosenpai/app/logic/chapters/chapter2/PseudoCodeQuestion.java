package com.algosenpai.app.logic.chapters.chapter2;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class PseudoCodeQuestion extends Question {

    private static ArrayList<Integer> array;

    PseudoCodeQuestion() {
        //Generates a size for the array between 6 and 9.
        int arraySize = getRandomNumber(6, 4);
        //Populates the array with elements no greater than 10.
        array = new ArrayList<>(createList(arraySize));
        questionFormatter();
        ArrayList<String> pseudoCode = new ArrayList<>();
        generatePseudoCode(pseudoCode);
        question += printPseudoCode(pseudoCode);
        answer = calculateSum(array, pseudoCode);
    }

    @Override
    public void questionFormatter() {
        question = "In the pseudocode program below, list is an initially empty Singly Linked List.\n"
                + "The function populateList() adds the integers " + array + " to the tail of the list sequentially.\n"
                + "What is the output of the program?";
    }

    /**
     * Calculates the sum of the values based on the pseudocode generated.
     *
     * @param array      The ArrayList which makes up the Linked List.
     * @param pseudoCode The list of instructions in the pseudo-code.
     * @return The value of the sum given in the pseudo-code in a String format.
     */
    private static String calculateSum(ArrayList<Integer> array, ArrayList<String> pseudoCode) {
        rtlm = new ReviewTracingListModel();
        rtlm.addReviewStep("This is the linked list.");
        rtlm.addReviewStep(array.toString());
        int sum = 0;
        int index = 0;
        rtlm.addReviewStep("We start with sum = " + sum + ", and index = " + index + ".");
        for (String i : pseudoCode) {
            switch (i) {
            case "n = n.next;":
                rtlm.addReviewStep("Consider step : " + i);
                index++;
                rtlm.addReviewStep("Increment index to " + index + ".");
                break;
            case "sum += list.tail.value;":
                rtlm.addReviewStep("Consider step : " + i);
                sum += array.get(array.size() - 1);
                rtlm.addReviewStep("New sum = " + sum + ".");
                break;
            case "sum += list.head.value;":
                rtlm.addReviewStep("Consider step : " + i);
                sum += array.get(0);
                rtlm.addReviewStep("New sum = " + sum + ".");
                break;
            case "n = list.head;":
                rtlm.addReviewStep("Consider step : " + i);
                index = 0;
                rtlm.addReviewStep("Set index to " + index + ".");
                break;
            case "sum += n.value;":
                rtlm.addReviewStep("Consider step : " + i);
                sum += array.get(index);
                rtlm.addReviewStep("New sum = " + sum + "after adding " + array.get(index) + ".");
                break;
            case "sum += n.next.value;":
                rtlm.addReviewStep("Consider step : " + i);
                sum += array.get(index + 1);
                rtlm.addReviewStep("New sum = " + sum + "after adding " + array.get(index + 1) + ".");
                break;
            default:
                break;
            }
        }
        rtlm.addReviewStep("Final value is " + sum + ".");
        return String.valueOf(sum);
    }

    /**
     * Generates a list of pseudo-code, such as "n = n.next", or "n = list.head".
     * This list of instructions will be printed to the user.
     *
     * @param pseudoCode The list which will contain the instructions.
     */
    private static void generatePseudoCode(ArrayList<String> pseudoCode) {
        pseudoCode.add("populateList();");
        pseudoCode.add("int sum = 0;");
        pseudoCode.add("Node n = list.head; //list.head/list.tail points to the first/last integer in list");
        //Generates a number of commands between 4 and 7.
        int noOfCommands = getRandomNumber(4,4);
        for (int i = 0; i < noOfCommands; i++) {
            int commandToAdd = getRandomNumber(0,6);
            switch (commandToAdd) {
            case 0:
                pseudoCode.add("n = n.next;");
                break;
            case 1:
                pseudoCode.add("sum += list.tail.value;");
                break;
            case 2:
                pseudoCode.add("sum += list.head.value;");
                break;
            case 3:
                pseudoCode.add("n = list.head;");
                break;
            case 4:
                pseudoCode.add("sum += n.value;");
                break;
            case 5:
                pseudoCode.add("sum += n.next.value;");
                break;
            default:
                break;
            }
        }
        pseudoCode.add("print sum;");
    }

    /**
     * Creates a Linked List and populate it with unique numbers.
     *
     * @param size The number of elements to be in the Linked List.
     * @return The Linked List data structure to be used for the question.
     */
    private static LinkedList<Integer> createList(int size) {
        HashSet<Integer> set = new HashSet<>();
        while (set.size() != size) {
            int value = getRandomNumber(0, 15);
            set.add(value);
        }
        return new LinkedList<>(set);
    }

    /**
     * Creates a String containing the list of instructions used in the pseudocode.
     *
     * @param pseudoCode The list of instructions in the pseudo-code.
     * @return The String containing the instructions.
     */
    private static String printPseudoCode(ArrayList<String> pseudoCode) {
        StringBuilder string = new StringBuilder();
        for (String cmd : pseudoCode) {
            string.append(cmd).append("\n");
        }
        return string.toString();
    }
}

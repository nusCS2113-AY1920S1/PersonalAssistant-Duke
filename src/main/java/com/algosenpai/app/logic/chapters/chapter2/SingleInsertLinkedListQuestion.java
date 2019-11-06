package com.algosenpai.app.logic.chapters.chapter2;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.HashSet;
import java.util.LinkedList;

public class SingleInsertLinkedListQuestion extends Question {

    private static int listSize;
    private static int valueToAdd;
    private static String positionToAdd;

    SingleInsertLinkedListQuestion() {
        //Generates a size for the linked list between 5 and 8.
        listSize = getRandomNumber(5,4);
        //Decide on a value to be added between 0 and 100.
        valueToAdd = getRandomNumber(0,100);
        //Decide on the position to be added.
        positionToAdd = getPositionToAdd();
        questionFormatter();
        //Populates the linked list with values.
        LinkedList<Integer> ll = createList(listSize);
        question += printList(ll);
        rtlm = new ReviewTracingListModel();
        rtlm.addReviewStep("This is the current list.");
        rtlm.addReviewStep(printList(ll));
        rtlm.addReviewStep("Since the position at which to append is " + positionToAdd + ".");
        if (positionToAdd.equals("head")) {
            ll.addFirst(valueToAdd);
        } else {
            ll.addLast(valueToAdd);
        }
        rtlm.addReviewStep("This is the new linked list.");
        answer = ll.toString();
        answer = answer.substring(1,answer.length() - 1);
        rtlm.addReviewStep(printList(ll));
    }

    @Override
    public void questionFormatter() {
        question = "Consider the Singly Linked List of size " + listSize + " below.\n"
                + "It undergoes an insertion of value " + valueToAdd + " at the " + positionToAdd
                + ".\nWhat would be the new sequence of integers?\n"
                + "Please provide your answer in space-separated format. e.g. x y z ...\n";
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
            int value = getRandomNumber(0, 100);
            set.add(value);
        }
        return new LinkedList<>(set);
    }

    /**
     * Creates a formatted String from the linkedlist provided.
     *
     * @param ll The linked list provided.
     * @return The string representing the linkedlist.
     */
    private static String printList(LinkedList<Integer> ll) {
        StringBuilder linkedListString = new StringBuilder();
        for (int i : ll) {
            linkedListString.append("[").append(i).append("]");
            if (i != ll.getLast()) {
                linkedListString.append(" -> ");
            }
        }
        return linkedListString.toString();
    }

    /**
     * Decides the position to add the value to by generating a random number 1, or 2.
     * @return A String containing the position. Either "head" or "tail"
     */
    private static String getPositionToAdd() {
        String positionToAdd;
        if (getRandomNumber(0,2) == 1) {
            positionToAdd = "head";
        } else {
            positionToAdd = "tail";
        }
        return positionToAdd;
    }

}

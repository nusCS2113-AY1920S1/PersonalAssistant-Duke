package com.algosenpai.app.logic.chapters.chapter2;

import com.algosenpai.app.logic.chapters.Question;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.logic.models.ReviewTracingListModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class StackPopPushQuestion extends Question {

    private static String question;
    private static String answer;
    private static int stackSize;

    StackPopPushQuestion() {
        // Generates a random number for the size of the stack between 4 and 8.
        stackSize = getRandomNumber(5, 5);
        // Populates the stack with numbers.
        LinkedList<Integer> stack = createList(stackSize, 100);
        ArrayList<String> instructions = new ArrayList<>();
        // Determines the number of instructions to be carried out between 4 and 6.
        int numberOfInstructions = getRandomNumber(2, 4);
        addInstructions(instructions, numberOfInstructions);
        questionFormatter();
        question += printStack(stack);
        question += printInstructions(instructions);
        changeStack(instructions, stack);
        answer = String.valueOf(stack.getLast());
    }

    @Override
    public QuestionModel execute() {
        return new QuestionModel(question, answer, new ReviewTracingListModel());
    }

    @Override
    public void questionFormatter() {
        question = "A Stack of size " + stackSize
                + " undergoes a series of operations as shown below.\n"
                + "What would be the new value called upon stack.peek()?\n";
    }

    /**
     * Creates a Linked List and populate it with unique numbers.
     *
     * @param size The number of elements to be in the Linked List.
     * @return The Linked List data structure to be used for the question.
     */
    private static LinkedList<Integer> createList(int size, int bound) {
        HashSet<Integer> set = new HashSet<>();
        while (set.size() != size) {
            int value = getRandomNumber(0, bound);
            set.add(value);
        }
        return new LinkedList<>(set);
    }

    /**
     * Adds new instructions to the list through random generation of either 1 or 0.
     * If values is 0, a push command is added along with a value. Else, a pop
     * command is added.
     *
     * @param instructions         The list where instructions will be added.
     * @param numberOfInstructions The number of instructions to be added into the
     *                             list.
     */
    private static void addInstructions(ArrayList<String> instructions, int numberOfInstructions) {
        for (int i = 0; i < numberOfInstructions; i++) {
            int val = getRandomNumber(0,2);
            int toadd = getRandomNumber(0,100);
            switch (val) {
            case 0:
                String combined = "Push(" + toadd + ");";
                instructions.add(combined);
                break;
            case 1:
                instructions.add("Pop();");
                break;
            default:
                break;
            }
        }
    }

    /**
     * Creates a formatted String which represents the stack given.
     *
     * @param stack The stack containing the elements.
     * @return The string which represents the stack.
     */
    private static String printStack(LinkedList<Integer> stack) {
        StringBuilder s = new StringBuilder();
        for (int i : stack) {
            s.append("[").append(i).append("] <- ");
        }
        s.append("Head\n");
        return s.toString();
    }

    /**
     * Creates a String with the instruction given by the list on separate new lines.
     *
     * @param instructions The list of instructions provided.
     * @return The String containing the instructions given by the list.
     */
    private static String printInstructions(ArrayList<String> instructions) {
        StringBuilder instructs = new StringBuilder();
        int i = 1;
        for (String s : instructions) {
            instructs.append(i).append(". ").append(s).append("\n");
            i++;
        }
        return instructs.toString();
    }

    /**
     * Changes the stack according to the instructions generated. Instructions are
     * either pop or push.
     *
     * @param instructions The list of instructions provided.
     * @param stack        The data structure to be changed.
     */
    private static void changeStack(ArrayList<String> instructions, LinkedList<Integer> stack) {
        for (String cmd : instructions) {
            if (cmd.contains("Pop")) {
                stack.removeLast();
            } else {
                String number = cmd.substring(5, cmd.length() - 2);
                int valuetoadd = Integer.parseInt(number);
                stack.addLast(valuetoadd);
            }
        }
    }
}

package com.algosenpai.app.logic.chapters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

class ChapterLinkedList {
    private static Random random = new Random();
    private static Scanner s = new Scanner(System.in);

    private int totalAnswers;
    private int correctAnswers;

    /**
     * Generates a random question according to a random value.
     * 
     * @return True if the question was answered correctly, or false if the question
     *         was answered wrongly.
     */
    public Boolean generateQuestions() {
        int questionType = random.nextInt(4);
        switch (questionType) {
        case 0:
            return stackPopPushQuestion();
        case 1:
            return queuePopPushQuestion();
        case 2:
            return singleInsertLinkedListQuestion();
        case 3:
            return pseudoCodeQuestion();
        default:
            return true;
        }
    }

    /**
     * Generates a pseudoCode question that tests the user on their understanding of
     * Linked List through pseudocode calculation. An input is taken from the user
     * as an answer.
     * 
     * @return True if the answer matches the input, or false if the answer is
     *         wrong.
     */
    private Boolean pseudoCodeQuestion() {
        int arraySize = random.nextInt(4) + 6;
        ArrayList<Integer> array = new ArrayList<>(createList(arraySize));
        for (int i = 0; i < array.size(); i++) {
            array.set(i, array.get(i) % 10);
        }
        System.out.println("In the pseudocode program below, list is an initially empty Singly Linked List.\n"
                + "The function populateList() adds the integers " + array + " to the tail of the list sequentially.\n"
                + "What is the output of the program?");
        ArrayList<String> pseudoCode = new ArrayList<>();
        generatePseudoCode(pseudoCode);
        printPseudoCode(pseudoCode);
        int answer = calculateSum(array, pseudoCode);
        int userInput = s.nextInt();
        s.nextLine();
        if (answer == userInput) {
            System.out.println("Well Done! You got a correct answer!\n");
            return true;
        } else {
            System.out.print("This is the correct answer : " + answer);
            System.out.println(". Try harder!\n");
            return false;
        }

    }

    /**
     * Calculates the sum of the values based on the pseudocode generated.
     * 
     * @param array      The ArrayList which makes up the Linked List.
     * @param pseudoCode The list of instructions in the pseudo-code.
     * @return The value of the sum given in the pseudo-code.
     */
    private int calculateSum(ArrayList<Integer> array, ArrayList<String> pseudoCode) {
        int sum = 0;
        int index = 0;
        for (String i : pseudoCode) {
            switch (i) {
            case "n = n.next;":
                index++;
                break;
            case "sum += list.tail.value;":
                sum += array.get(array.size() - 1);
                break;
            case "sum += list.head.value;":
                sum += array.get(0);
                break;
            case "n = list.head;":
                index = 0;
                break;
            case "sum += n.value;":
                sum += array.get(index);
                break;
            case "sum += n.next.value;":
                sum += array.get(index + 1);
                break;
            default:
                break;
            }
        }
        return sum;
    }

    /**
     * Generates a list of pseudo-code, such as "n = n.next", or "n = list.head".
     * This list of instructions will be printed to the user.
     * 
     * @param pseudoCode The list which will contain the instructions.
     */
    private void generatePseudoCode(ArrayList<String> pseudoCode) {
        pseudoCode.add("populateList();");
        pseudoCode.add("int sum = 0;");
        pseudoCode.add("Node n = list.head; //list.head/list.tail points to the first/last integer in list");
        int noOfCommands = random.nextInt(4) + 4;
        for (int i = 0; i < noOfCommands; i++) {
            int commandToAdd = random.nextInt(6);
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
     * Generates a question relating to a single insertion in a Singly Linked List.
     * The question can be either appending to the head or the tail of the linked
     * list. A sequence of integers are taken in from the user as an answer.
     * 
     * @return True if the answer matches the input given by the user, else false if
     *         the answer is wrong.
     */
    private Boolean singleInsertLinkedListQuestion() {
        int listSize = random.nextInt(4) + 5;
        LinkedList<Integer> ll = createList(listSize);
        int valueToAdd = random.nextInt(100);
        String positionToAdd;
        if (random.nextInt(2) == 1) {
            positionToAdd = "head";
        } else {
            positionToAdd = "tail";
        }
        System.out.println("Consider the Singly Linked List of size " + listSize + " below."
                + " It undergoes an insertion of value " + valueToAdd + " at the " + positionToAdd
                + ".\nWhat would be the new sequence of integers?");
        printList(ll);
        if (positionToAdd.equals("head")) {
            ll.addFirst(valueToAdd);
        } else {
            ll.addLast(valueToAdd);
        }
        LinkedList<Integer> userInput = new LinkedList<>();
        String input = s.nextLine();
        String[] arr = input.split(" -> ");
        for (String string : arr) {
            userInput.add(Integer.parseInt(string));
        }
        if (userInput.equals(ll)) {
            System.out.println("Well Done! You got a correct answer!\n");
            return true;
        } else {
            System.out.println("This is the correct answer : ");
            printList(ll);
            System.out.println("Try harder!\n");
            return false;
        }
    }

    /**
     * Generates a Queue question related to popping and pushing. A random size is
     * determined, followed by a series of instructions consisting of pop and push.
     * The algorithm will do the popping and pushing accordingly. An input is taken
     * in from the user as an answer.
     * 
     * @return True if the answer matches the input, or false if the question was
     *         answered wrongly.
     */
    private Boolean queuePopPushQuestion() {
        int queueSize = random.nextInt(4) + 4;
        LinkedList<Integer> queue = createList(queueSize);
        ArrayList<String> instructions = new ArrayList<String>();
        int numberOfInstructions = random.nextInt(3) + 4;
        addInstructions(instructions, numberOfInstructions);
        System.out.println("A Queue of size " + queueSize
                + " undergoes a series of operations as shown below.\n"
                + "What would be the new value called upon queue.peek()?");
        printQueue(queue);
        printInstructions(instructions);
        changeQueue(instructions, queue);
        int val = s.nextInt();
        s.nextLine();
        if (val == queue.getLast()) {
            System.out.println("Well Done! You got a correct answer!\n");
            return true;
        } else {
            System.out.println("This is the correct answer : " + queue.getLast() + "\nTry harder!\n");
            return false;
        }
    }

    /**
     * Changes the queue according to the instructions given. If instruction is pop,
     * the front value of the list would be removed, or else a new value will be
     * pushed into the queue.
     * 
     * @param instructions The list of instructions given.
     * @param queue        The list which would be edited according to the
     *                     instructions given.
     */
    private void changeQueue(ArrayList<String> instructions, LinkedList<Integer> queue) {
        for (String cmd : instructions) {
            if (cmd.contains("Pop")) {
                queue.removeLast();
            } else {
                String number = cmd.substring(5, cmd.length() - 2);
                int valuetoadd = Integer.parseInt(number);
                queue.addFirst(valuetoadd);
            }
        }
    }

    /**
     * Generates a Stack operations question related to pushing and popping. A
     * random stack size is determined and the stack is created. A sequence of
     * instructions consisting of push and pops are created, followed by which, the
     * algorithm will do the popping and pushing accordingly. An input is taken in
     * from the user as an answer.
     * 
     * @return True if the answer matches the input, or false if the answer is
     *         wrong.
     */
    private Boolean stackPopPushQuestion() {
        int stackSize = random.nextInt(4) + 4;
        LinkedList<Integer> stack = createList(stackSize);
        ArrayList<String> instructions = new ArrayList<String>();
        int numberOfInstructions = random.nextInt(3) + 4;
        addInstructions(instructions, numberOfInstructions);
        System.out.println("A Stack of size " + stackSize
                + " undergoes a series of operations as shown below.\n"
                + "What would be the new value called upon stack.peek()?");
        printStack(stack);
        printInstructions(instructions);
        changeStack(instructions, stack);
        int val = s.nextInt();
        s.nextLine();
        if (val == stack.getLast()) {
            System.out.println("Well Done! You got a correct answer!\n");
            return true;
        } else {
            System.out.println("This is the correct answer : " + stack.getLast() + "\nTry harder!\n");
            return false;
        }
    }

    /**
     * Changes the stack according to the instructions generated. Instructions are
     * either pop or push.
     * 
     * @param instructions The list of instructions provided.
     * @param stack        The data structure to be changed.
     */
    private void changeStack(ArrayList<String> instructions, LinkedList<Integer> stack) {
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

    /**
     * Adds new instructions to the list through random generation of either 1 or 0.
     * If values is 0, a push command is added along with a value. Else, a pop
     * command is added.
     * 
     * @param instructions         The list where instructions will be added.
     * @param numberOfInstructions The number of instructions to be added into the
     *                             list.
     */
    private void addInstructions(ArrayList<String> instructions, int numberOfInstructions) {
        for (int i = 0; i < numberOfInstructions; i++) {
            int val = random.nextInt(2);
            int toadd = random.nextInt(100);
            switch (val) {
            case 0:
                String combined = "Push(" + Integer.toString(toadd) + ");";
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
     * Creates a Linked List and populate it with unique numbers.
     * 
     * @param size The number of elements to be in the Linked List.
     * @return The Linked List data structure to be used for the question.
     */
    private LinkedList<Integer> createList(int size) {
        HashSet<Integer> set = new HashSet<>();
        while (set.size() != size) {
            int value = random.nextInt(100);
            set.add(value);
        }
        LinkedList<Integer> stackCreated = new LinkedList<>(set);
        return stackCreated;
    }

    /**
     * Prints the list of pseudo-code.
     * 
     * @param pseudoCode The list of instructions in the pseudo-code.
     */
    private void printPseudoCode(ArrayList<String> pseudoCode) {
        for (String cmd : pseudoCode) {
            System.out.println(cmd);
        }
    }

    /**
     * Prints the list given.
     * 
     * @param ll The linked list provided.
     */
    private void printList(LinkedList<Integer> ll) {
        for (int i : ll) {
            System.out.printf("[%d]", i);
            if (i != ll.getLast()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    /**
     * Prints the stack created.
     * 
     * @param stack The stack containing the elements.
     */
    private void printStack(LinkedList<Integer> stack) {
        for (int i : stack) {
            System.out.printf("[%d] <- ", i);
        }
        System.out.println("Head");
    }

    /**
     * Prints the queue created.
     * 
     * @param queue The queue containing the elements.
     */
    private void printQueue(LinkedList<Integer> queue) {
        for (int i : queue) {
            System.out.printf("[%d] -> ", i);
        }
        System.out.println("Front");
    }

    /**
     * Prints the instruction given by the list on separate new lines.
     * 
     * @param instructions The list of instructions provided.
     */
    private void printInstructions(ArrayList<String> instructions) {
        int i = 1;
        for (String s : instructions) {
            System.out.printf("%d. %s\n", i, s);
            i++;
        }
    }

}
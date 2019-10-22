package util;

import models.task.Task;

import java.util.ArrayList;
import java.util.Arrays;

public class ParserHelper {
    private SortHelper sortHelper;

    public ParserHelper() {
        this.sortHelper = new SortHelper();
    }

    /**
     * Parses the string input to extract the name, phone number and email.
     * @param input Contains the name, phone number and email.
     * @return An array consisting of name in index 0, phone number in index 1 and email in index 2.
     */
    public String[] parseMemberDetails(String input) {
        String[] memberDetails = new String[4];
        memberDetails[0] = "--";
        memberDetails[1] = "--";
        memberDetails[2] = "--";
        memberDetails[3] = "0";
        String[] tempInput = input.split(" ");
        for (String s : tempInput) {
            if (s.length() >= 2) {
                switch (s.substring(0, 2)) {
                case "i/":
                    memberDetails[1] = s.substring(2);
                    break;
                case "e/":
                    memberDetails[2] = s.substring(2);
                    break;
                case "x/":
                    memberDetails[3] = s.substring(2);
                    break;
                default:
                    break;
                }
            }
        }
        int indexOfNameFlag = input.indexOf("n/");
        int indexOfPhoneFlag = input.indexOf("i/");
        int indexOfEmailFlag = input.indexOf("e/");
        int indexOfMemberIndexFlag = input.indexOf("x/");
        if (indexOfNameFlag == -1) {
            return memberDetails;
        }
        if (indexOfPhoneFlag != -1) {
            memberDetails[0] = input.substring(indexOfNameFlag + 2, indexOfPhoneFlag - 1);
        } else if (indexOfEmailFlag != -1) {
            memberDetails[0] = input.substring(indexOfNameFlag + 2, indexOfEmailFlag - 1);
        } else if (indexOfMemberIndexFlag != -1) {
            memberDetails[0] = input.substring(indexOfNameFlag + 2, indexOfMemberIndexFlag - 1);
        } else {
            memberDetails[0] = input.substring(2);
        }
        return memberDetails;
    }

    /**
     * Parses the string input to extract the name, priority, due date, credit and task state.
     * @param input Contains the name, priority, due date, credit and task state.
     * @return An ArrayList consisting of name in index 0, priority in index 1, due date in index 2,
     *         credit in index 3, task state in index 4.
     */
    public ArrayList<String> parseTaskDetails(String input) {
        ArrayList<String> newTask = new ArrayList<>();

        String newTaskName = "--";
        String newTaskPriority = "-1";
        String newTaskDate = null;
        String newTaskCredit = "-1";
        String newTaskState = "NONE";

        String [] newTaskDetails = input.split(" ");
        for (String s : newTaskDetails) {
            switch (s.substring(0, 2)) {
            case "t/":
                newTaskName = s.substring(2);
                break;
            case "p/":
                newTaskPriority = s.substring(2);
                break;
            case "d/":
                newTaskDate = s.substring(2);
                break;
            case "c/":
                newTaskCredit = s.substring(2);
                break;
            case "s/":
                newTaskState = s.substring(2);
                break;
            default:
                break;
            }
        }

        int indexOfNameFlag = input.indexOf("t/");
        if (indexOfNameFlag != -1) {
            newTaskName = input.substring(indexOfNameFlag+2);
            int indexOfAnotherFlag = newTaskName.indexOf("/")-1;
            if (indexOfAnotherFlag > 0) {
                newTaskName = newTaskName.substring(0, indexOfAnotherFlag).trim();
            }
        }

        newTask.add(newTaskName);
        newTask.add(newTaskPriority);
        newTask.add(newTaskDate);
        newTask.add(newTaskCredit);
        newTask.add(newTaskState);

        return newTask;
    }

    /**
     * Parses the criteria specified by the user to sort the list of tasks.
     * @param taskList A list of all tasks in the project.
     * @param sortCriteria Criteria to sort the list of tasks
     * @return An ArrayList with String descriptions of task details sorted by the criteria specified by the user.
     */
    public ArrayList<String> parseSortTaskDetails(ArrayList<Task> taskList, String sortCriteria) {
        ArrayList<String> taskDetails = new ArrayList<>();
        if (sortCriteria.length() >= 4) {
            String[] detailedCriteria = sortCriteria.split("-");
            switch (detailedCriteria[0]) {
            case "/NAME":
                taskDetails = this.sortHelper.sortTaskName(taskList);
                break;
            case "/INDEX":
                taskDetails = this.sortHelper.sortTaskIndex(taskList);
                break;
            case "/DATE":
                taskDetails = this.sortHelper.sortTaskDueDate(taskList);
                break;
            case "/PRIORITY":
                taskDetails = this.sortHelper.sortTaskPriority(taskList);
                break;
            case "/CREDIT":
                taskDetails = this.sortHelper.sortTaskCredit(taskList);
                break;
            case "/WHO":
                taskDetails = this.sortHelper.sortTaskMember(taskList, detailedCriteria[1]);
                break;
            case "/KANBAN":
                taskDetails = this.sortHelper.sortTaskState(taskList, detailedCriteria[1]);
                break;
            default:
                break;
            }
        }
        return taskDetails;
    }

    /**
     * Parses input String to get valid task and member index numbers, as well as error messages
     * for invalid index numbers.
     * @param input The input from the user.
     */
    public ArrayList<ArrayList<String>> parseAssignmentInputHelper(String input) {
        ArrayList<String> allIndexesToAssign = new ArrayList<>();
        ArrayList<String> allIndexesToUnassign = new ArrayList<>();
        ArrayList<String> allTasksIndexes = new ArrayList<>();

        String [] inputParts = input.split("-");

        for (String s : inputParts) {
            String [] part = s.split(" ");
            switch (part[0]) {
            case "i":
                allTasksIndexes = new ArrayList<>(Arrays.asList(part));
                allTasksIndexes.remove("i");
                break;
            case "to":
                allIndexesToAssign = new ArrayList<>(Arrays.asList(part));
                allIndexesToAssign.remove("to");
                break;
            case "rm":
                allIndexesToUnassign = new ArrayList<>(Arrays.asList(s.split(" ")));
                allIndexesToUnassign.remove("rm");
                break;
            default:
            }
        }

        ArrayList<ArrayList<String>> assignmentOutput = new ArrayList<>();
        assignmentOutput.add(allIndexesToAssign);
        assignmentOutput.add(allIndexesToUnassign);
        assignmentOutput.add(allTasksIndexes);

        return assignmentOutput;
    }
}

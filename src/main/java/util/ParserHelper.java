package util;

import models.task.Task;

import java.util.ArrayList;

public class ParserHelper {
    private SortHelper sortHelper;

    public ParserHelper() {
        this.sortHelper = new SortHelper();
    }

    /**
     * Parses the string input to extract the name, phone number and email.
     * @param input Contains the name, phone number and email.
     * @return An array consisting on name in index 0, phone number in index 1 and email in index 2.
     */
    public String[] parseMemberDetails(String input) {
        String[] memberDetails = new String[4];
        memberDetails[0] = "No name";
        memberDetails[1] = "No phone number";
        memberDetails[2] = "No email address";
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
}

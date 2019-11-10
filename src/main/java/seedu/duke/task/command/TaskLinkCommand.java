package seedu.duke.task.command;

import seedu.duke.common.command.Command;
import seedu.duke.common.model.Model;
import seedu.duke.email.EmailList;
import seedu.duke.email.entity.Email;
import seedu.duke.task.TaskList;
import seedu.duke.task.entity.Task;
import seedu.duke.ui.UI;

import java.util.ArrayList;

public class TaskLinkCommand extends Command {
    private int taskIndex;
    private ArrayList<Integer> emailIndexList;
    private ArrayList<Integer> deleteIndexList;

    /**
     * Instantiates link command with all the necessary variables.
     *
     * @param taskIndex       the index of task that is to be linked together.
     * @param emailIndexList  the index of emails that is to be linked.
     * @param deleteIndexList the index of link to be deleted for the specified task.
     */
    public TaskLinkCommand(int taskIndex, ArrayList<Integer> emailIndexList, ArrayList<Integer> deleteIndexList) {
        this.taskIndex = taskIndex;
        this.emailIndexList = emailIndexList;
        this.deleteIndexList = deleteIndexList;
    }

    /**
     * Executes the link command.
     *
     * @param model allows access the taskList and emailList
     * @return      a flag whether linking is done successfully. Returns false otherwise.
     */
    @Override
    public boolean execute(Model model) {
        TaskList taskList = model.getTaskList();
        EmailList emailList = model.getEmailList();
        StringBuilder msg = new StringBuilder();
        ArrayList<String> linkedEmails = taskList.get(taskIndex).getLinkedEmails();

        if (!deleteIndexList.isEmpty()) {
            deleteLinks(msg, linkedEmails);
        }

        if (!emailIndexList.isEmpty()) {
            addLinks(taskList, emailList, msg, linkedEmails);
        }

        listLinkedEmails(emailList, msg, linkedEmails);

        if (!silent) {
            responseMsg = msg.toString();
            UI.getInstance().showResponse(msg.toString());
        }
        return true;
    }

    private void addLinks(TaskList taskList, EmailList emailList, StringBuilder msg, ArrayList<String> linkedEmails) {
        msg.append("Linked task " + (taskIndex+1));
        msg.append(" with email(s): ");
        Task task = taskList.get(taskIndex);

        for (int i = emailIndexList.size()-1; i >= 0; i--) {
            int index = emailIndexList.get(i);
            msg.append((index+1) + " ");
            Email email = emailList.get(index);
            if (linkedEmails.contains(email.getShaHash())) {
                continue;
            }
            task.addLinkedEmails(email.getShaHash());
        }
        msg.append(System.lineSeparator());
    }

    private void listLinkedEmails(EmailList emailList, StringBuilder msg, ArrayList<String> linkedEmails) {
        if (linkedEmails.isEmpty()) {
            msg.append("No linked emails currently.");
        } else {
            msg.append("Here are your linked emails:" + System.lineSeparator());
            int listIndex = 1;
            for (String filename : linkedEmails) {
                if (emailList.convertShaToSubject(filename) == null) {
                    linkedEmails.remove(filename);
                } else {
                    String name = emailList.convertShaToSubject(filename);
                    msg.append(listIndex + ". " + name + System.lineSeparator());
                    listIndex++;
                }
            }

            int firstIndex = emailList.convertShaToIndex(linkedEmails.get(0));
            String[] parsedMsg = emailList.show(firstIndex);
            UI.getInstance().setEmailContent(parsedMsg[1]);
            UI.getInstance().updateHtml();
            responseMsg = msg.toString();
        }
    }

    private void deleteLinks(StringBuilder msg, ArrayList<String> linkedEmails) {
        for (int deleteIndex : deleteIndexList) {
            if (deleteIndex < linkedEmails.size() && deleteIndex >= 0) {
                linkedEmails.remove(deleteIndex);
            } else {
                msg.append("Link " + (deleteIndex+1) + " does not exist. ");
            }
        }
        msg.append("Valid links have been deleted. ");
        msg.append(System.lineSeparator() + System.lineSeparator());
    }
}

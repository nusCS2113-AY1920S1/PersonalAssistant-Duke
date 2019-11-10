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

        if (emailIndexList.isEmpty()) {
            if (linkedEmails.isEmpty()) {
                msg.append("No linked emails currently.");
            } else {
                //TODO make function in EmailList to return Array List of email subjects (and maybe index)
                //TODO create function to convert SHA to Subject
                msg.append("Here are your linked emails:" + System.lineSeparator());
                int i = 1;
                for (String filename : linkedEmails) {
                    String name = null;
                    for (int j = 0; j < emailList.size(); j++) {
                        if (filename.equals(emailList.get(j).getShaHash())) {
                            name = emailList.get(j).getSubject();
                            break;
                        }
                    }
                    msg.append(i + ". " + name + System.lineSeparator());
                    i++;
                }
            }
            responseMsg = msg.toString();
            UI.getInstance().showResponse(msg.toString());
            return true;
        } else {
            msg.append("Linked task ");
            Task task = taskList.get(taskIndex);
            msg.append(task.getName());
            msg.append(" with email(s):" + System.lineSeparator());

            for (int j = 0; j < emailIndexList.size(); j++) {
                Email email = emailList.get(emailIndexList.get(j));
                msg.append(email.getSubject() + System.lineSeparator());
                if (task.getLinkedEmails().contains(email.getShaHash())) {
                    continue;
                }
                task.addLinkedEmails(email.getShaHash());
            }

            if (!silent) {
                responseMsg = msg.toString();
                UI.getInstance().showResponse(msg.toString());
            }
            return true;
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

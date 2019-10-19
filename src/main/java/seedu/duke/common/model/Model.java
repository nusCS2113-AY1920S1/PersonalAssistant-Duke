package seedu.duke.common.model;

import seedu.duke.Duke;
import seedu.duke.email.EmailList;
import seedu.duke.task.TaskList;

public class Model {
    private TaskList taskList = new TaskList();
    private EmailList emailList = new EmailList();

    public TaskList getTaskList() {
        return taskList;
    }
    public EmailList getEmailList() {
        return emailList;
    }
    public void setEmailList(EmailList emailList) {
        this.emailList = emailList;
        updateGuiEmailList();
    }
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
        updateGuiTaskList();
    }

    /**
     * Update any changes in the task list to the display in ui.
     */
    public void updateGuiTaskList() {
        Duke.getUI().updateTaskList(taskList.getTaskGuiStringList());
    }

    public void updateGuiEmailList() {
        Duke.getUI().updateEmailList(emailList.getEmailGuiStringList());
    }
}

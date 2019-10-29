package seedu.duke.common.model;

import seedu.duke.Duke;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailTags;
import seedu.duke.task.TaskList;

public class Model {
    private TaskList taskList = new TaskList();
    private EmailList emailList = new EmailList();
    private static Model model;
    private boolean isUpdateGui = true;

    /**
     * Gets new instance.
     *
     * @return model
     */
    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
        updateGuiTaskList();
    }

    public EmailList getEmailList() {
        return emailList;
    }

    /**
     * Set emailList of Model.
     *
     * @param emailList emailList
     */
    public void setEmailList(EmailList emailList) {
        this.emailList = emailList;
        updateGuiEmailList();
    }

    /**
     * Update any changes in the task list to the display in ui.
     */
    public void updateGuiTaskList() {
        if (isUpdateGui) {
            Duke.getUI().updateTaskList(taskList.getTaskGuiStringList());
        }
    }

    /**
     * Updates the emailList in GUI with the latest emailList in sorted order.
     */
    public void updateGuiEmailList() {
        if (isUpdateGui == true) {
            emailList.sortByGivenOrder();
            Duke.getUI().updateEmailList(emailList.getEmailGuiStringList());
        }
    }

    public void updateEmailTagList() {
        EmailTags.updateEmailTagList(emailList);
        //Duke.getUI().updateEmailList(emailList.getEmailTagList());
    }

    public void setIsUpdateGui(boolean isUpdateGui) {
        this.isUpdateGui = isUpdateGui;
    }
}

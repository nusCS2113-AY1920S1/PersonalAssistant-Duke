package seedu.duke.common.model;

import seedu.duke.email.EmailContentParseHelper;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailStorage;
import seedu.duke.email.EmailTags;
import seedu.duke.task.TaskList;
import seedu.duke.task.TaskStorage;
import seedu.duke.ui.UI;

public class Model {
    private static Model model;
    private TaskList taskList;
    private EmailList emailList;

    private Model() {
    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    /**
     * Initializes model structure.
     */
    public void initModel() {
        taskList = new TaskList();
        emailList = new EmailList();
        taskList = TaskStorage.readTaskFromFile();
        emailList = EmailStorage.readEmailFromFile("");
        EmailContentParseHelper.initKeywordList();
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

    public void setEmailList(EmailList emailList) {
        this.emailList = emailList;
        updateGuiEmailList();
        updateEmailTagList();
    }

    /**
     * Update any changes in the task list to the display in ui.
     */
    public void updateGuiTaskList() {
        UI.getInstance().updateTaskList(taskList.getTaskGuiStringList());
    }

    public void updateGuiEmailList() {
        emailList.sortByGivenOrder();
        UI.getInstance().updateEmailList(emailList.getEmailGuiStringList());
    }

    public void updateEmailTagList() {
        EmailTags.updateEmailTagList(emailList);
        //UI.getInstance().updateEmailList(emailList.getEmailTagList());
    }

    /**
     * Saves all the info in the model to the respective files before exit.
     */
    public void saveModel() {
        TaskStorage.saveTasks(model.getTaskList());
        EmailStorage.saveEmails(model.getEmailList());
    }

    /**
     * Gets the length of the email list.
     *
     * @return email list length
     */
    public int getEmailListLength() {
        return emailList.size();
    }

    /**
     * Gets the length of the task list.
     *
     * @return task list length
     */
    public int getTaskListLength() {
        return taskList.size();
    }
}

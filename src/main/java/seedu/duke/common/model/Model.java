package seedu.duke.common.model;

import seedu.duke.email.EmailKeywordPairList;
import seedu.duke.email.parser.EmailContentParseHelper;
import seedu.duke.email.EmailList;
import seedu.duke.email.storage.EmailStorage;
import seedu.duke.email.EmailTags;
import seedu.duke.task.TaskList;
import seedu.duke.task.storage.TaskStorage;
import seedu.duke.ui.UI;

public class Model {
    private static Model model;
    private boolean isUpdateGui = true;
    private TaskList taskList;
    private EmailList emailList;
    private EmailKeywordPairList keywordPairList;

    private Model() {
    }

    /**
     * Returns the current instance of the model. If there are none, it instantiates a new model.
     *
     * @return instance of model
     */
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
        keywordPairList = new EmailKeywordPairList();
        taskList = new TaskList();
        emailList = new EmailList();
        keywordPairList = EmailContentParseHelper.initKeywordList();
        taskList = TaskStorage.readTaskFromFile();
        emailList = EmailStorage.readEmailFromFile("");
        updateGuiTaskList();
        updateGuiEmailList();
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
        updateEmailTagList();
    }

    public EmailKeywordPairList getKeywordPairList() {
        return keywordPairList;
    }

    public void setKeywordPairList(EmailKeywordPairList keywordPairList) {
        this.keywordPairList = keywordPairList;
    }

    /**
     * Update any changes in the task list to the display in ui.
     */
    public void updateGuiTaskList() {
        if (isUpdateGui) {
            taskList.sortByType();
            UI.getInstance().updateTaskList(taskList.getTaskGuiStringList());
        }
    }

    /**
     * Updates the emailList in GUI with the latest emailList in sorted order.
     */
    public void updateGuiEmailList() {
        if (isUpdateGui) {
            emailList.sortByGivenOrder();
            UI.getInstance().updateEmailList(emailList.getEmailGuiStringList());
        }
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

    public void setIsUpdateGui(boolean isUpdateGui) {
        this.isUpdateGui = isUpdateGui;
    }
}

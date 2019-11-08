package duke.commons.file;

//@@author HashirZahir
public enum FilePathNames {
    FILE_PATH_USER_MEALS_FILE("duke/Data/meals.json"),
    FILE_PATH_DEFAULT_MEAL_FILE("duke/Data/defaultValues.json"),
    FILE_PATH_GOAL_FILE("duke/Data/goal.json"),
    FILE_PATH_USER_FILE("duke/Data/user.json"),
    FILE_PATH_AUTOCORRECT_FILE("duke/Data/word.txt"),
    FILE_PATH_TRANSACTION_FILE("duke/Data/transaction.json"),
    FILE_PATH_MASTER_HELP_FILE("duke/commons/help");

    private String filePath;
    FilePathNames(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath;
    }
}

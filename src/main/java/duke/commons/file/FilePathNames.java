package duke.commons.file;

//@@author HashirZahir
public enum FilePathNames {
    FILE_PATH_USER_MEALS_FILE("src/main/java/duke/Data/meals.json"),
    FILE_PATH_DEFAULT_MEAL_FILE("src/main/java/duke/Data/defaultValues.json"),
    FILE_PATH_GOAL_FILE("src/main/java/duke/Data/goal.json"),
    FILE_PATH_USER_FILE("src/main/java/duke/Data/user.json"),
    FILE_PATH_AUTOCORRECT_FILE("src/main/java/duke/Data/word.txt"),
    FILE_PATH_TRANSACTION_FILE("src/main/java/duke/Data/transaction.json"),
    FILE_PATH_MASTER_HELP_FILE("src/main/java/duke/commons/help");

    private String filePath;
    FilePathNames(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath;
    }
}

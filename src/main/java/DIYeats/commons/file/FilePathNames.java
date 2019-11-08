package DIYeats.commons.file;

//@@author HashirZahir
public enum FilePathNames {
    FILE_PATH_USER_MEALS_FILE("DIYeats/Data/meals.json"),
    FILE_PATH_DEFAULT_MEAL_FILE("DIYeats/Data/defaultValues.json"),
    FILE_PATH_GOAL_FILE("DIYeats/Data/goal.json"),
    FILE_PATH_USER_FILE("DIYeats/Data/user.json"),
    FILE_PATH_AUTOCORRECT_FILE("DIYeats/Data/word.txt"),
    FILE_PATH_TRANSACTION_FILE("DIYeats/Data/transaction.json"),
    FILE_PATH_MASTER_HELP_FILE("DIYeats/commons/help"),
    FILE_PATH_EXERCISES_FILE("DIYeats/Data/exercises.json");

    private String filePath;
    FilePathNames(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath;
    }
}

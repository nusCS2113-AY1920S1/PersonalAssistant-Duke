package diyeats.commons.file;

//@@author HashirZahir
public enum FilePathNames {
    FILE_PATH_USER_MEALS_FILE("diyeats/Data/meals.json"),
    FILE_PATH_DEFAULT_MEAL_FILE("diyeats/Data/defaultValues.json"),
    FILE_PATH_GOAL_FILE("diyeats/Data/goal.json"),
    FILE_PATH_USER_FILE("diyeats/Data/user.json"),
    FILE_PATH_AUTOCORRECT_FILE("diyeats/Data/word.txt"),
    FILE_PATH_TRANSACTION_FILE("diyeats/Data/transaction.json"),
    FILE_PATH_MASTER_HELP_FILE("diyeats/commons/help"),
    FILE_PATH_EXERCISES_FILE("diyeats/Data/exercises.json");

    private String filePath;
    FilePathNames(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath;
    }
}

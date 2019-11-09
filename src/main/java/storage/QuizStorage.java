package storage;

/**
 * Save and load incorrect answers in quizzes.
 */
public class QuizStorage extends Storage {
    protected String quizFilePath;

    public QuizStorage(String filepath) {
        super(filepath);
        this.quizFilePath = filepath;
    }

    @Override
    public void writeFile(String s, boolean append, String fileType) {
        super.writeFile(s, append, fileType);
        return;
    }
}

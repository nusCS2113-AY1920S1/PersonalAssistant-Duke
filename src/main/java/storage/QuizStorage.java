package storage;

/**
 * Save and load incorrect answers in quizzes.
 */
public class QuizStorage extends Storage {
    protected String quizFilePath;
    public QuizStorage(String filepath){
        this.quizFilePath = filepath;
    }
    @Override
    public void writeFile(String s, boolean append){
        super.writeFile(s, append);
        return;
    }
}

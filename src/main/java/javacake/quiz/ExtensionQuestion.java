package javacake.quiz;

public class ExtensionQuestion extends Question{
    QuestionType type;

    public ExtensionQuestion(String question, String answer) {
        super(question, answer);
        type = QuestionType.EXTENSIONS;
    }
}

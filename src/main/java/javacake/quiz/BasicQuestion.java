package javacake.quiz;

public class BasicQuestion extends Question{
    QuestionType type;

    public BasicQuestion(String question, String answer) {
        super(question, answer);
        type = QuestionType.BASIC;
    }
}

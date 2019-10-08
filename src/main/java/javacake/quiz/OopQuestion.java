package javacake.quiz;

public class OopQuestion extends Question {
    QuestionType type;

    public OopQuestion(String question, String answer) {
        super(question, answer);
        type = QuestionType.OOP;
    }
}

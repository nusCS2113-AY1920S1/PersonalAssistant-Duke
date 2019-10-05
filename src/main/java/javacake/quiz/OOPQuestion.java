package javacake.quiz;

public class OOPQuestion extends Question{
    QuestionType type;

    public OOPQuestion(String question, String answer) {
        super(question, answer);
        type = QuestionType.OOP;
    }
}

package javacake.quiz;

public class Question {
    private String question;
    private String answer;
    private QuestionType questionType;

    public enum QuestionType {
        BASIC, OOP, EXTENSIONS
    }

    public Question(String question, String answer, QuestionType type) {
        this.question = question;
        this.answer = answer;
        this.questionType = type;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isAnswerCorrect(String input) {
        return (input.trim().equals(answer));
    }
}

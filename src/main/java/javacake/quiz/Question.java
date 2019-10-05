package javacake.quiz;

public class Question {
    private String question;
    private String answer;

    public enum QuestionType {
        BASIC, OOP, EXTENSIONS
    }

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    /**
     * Checks if user's answer to the question is correct.
     * @param input user's inputted answer
     * @return true if input matches answer, false otherwise.
     */
    public boolean isAnswerCorrect(String input) {
        return (input.trim().toLowerCase().equals(answer));
    }
}

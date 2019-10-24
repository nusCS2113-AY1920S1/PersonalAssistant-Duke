package javacake.quiz;

public class Question {
    private String question;
    private String answer;
    private String userAnswer;

    public enum QuestionType {
        BASIC, OOP, EXTENSIONS, ALL
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

    public void setUserAnswer(String userInput) {
        userAnswer = userInput;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    /**
     * Checks if user's answer to the question is correct.
     * @param input user's inputted answer
     * @return true if input matches answer, false otherwise.
     */
    public boolean isAnswerCorrect(String input) {
        return (input.trim().equals(answer));
    }
}

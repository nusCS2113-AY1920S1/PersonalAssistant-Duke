package com.algosenpai.app.logic.models;

public class QuestionModel {
    private String question;
    private String correctAnswer;
    private String userAnswer;
    private ReviewTracingListModel rtlm = new ReviewTracingListModel();

    /**
     * Creates a question using multple parameters.
     * @param question  The question to be shown to the user
     * @param answer    The correct answer to the question
     * @param rtlm      The steps leading to the correct answer
     */
    public QuestionModel(String question, String answer, ReviewTracingListModel rtlm) {
        this.question = question;
        this.correctAnswer = answer;
        this.rtlm = rtlm;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return correctAnswer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    /**
     * Checks the user answer with the correct answer.
     * @return a boolean on whether the user answer is equal to the correct answer.
     */
    public boolean checkAnswer() {
        return this.userAnswer.equals(this.correctAnswer);
    }

    private ReviewTracingListModel getRtlm() {
        return rtlm;
    }

}

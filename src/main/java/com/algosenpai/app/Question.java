package com.algosenpai.app;

import com.algosenpai.app.model.ReviewTracingListModel;

public class Question {
    private String question;
    private String answer;
    private ReviewTracingListModel rtlm = new ReviewTracingListModel();

    /**
     * Creates a question using multple parameters.
     * @param question  The question to be shown to the user
     * @param answer    The correct answer to the question
     * @param rtlm      The steps leading to the correct answer
     */
    public Question(String question,String answer, ReviewTracingListModel rtlm) {
        this.question = question;
        this.answer = answer;
        this.rtlm = rtlm;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public ReviewTracingListModel getRtlm() {
        return rtlm;
    }

    public boolean isAnswerEqual(String userInput) {
        return userInput.equals(answer);
    }
}

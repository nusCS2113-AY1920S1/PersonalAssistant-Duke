package com.algosenpai.app.model;

import java.util.ArrayList;
import java.util.List;

public class ReviewQuestionModel {

    private String question;

    private List<Integer> list;

    private String myAnswer;

    private String actualAnswer;

    public ReviewQuestionModel() {
        list = new ArrayList<>();
    }

    public String getActualAnswer() {
        return actualAnswer;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setActualAnswer(String actualAnswer) {
        this.actualAnswer = actualAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

}

package com.algosenpai.app.model;

import java.util.ArrayList;
import java.util.List;

public class ReviewQuestionListModel {

    private List<ReviewQuestionModel> reviewQuizModelList;

    public ReviewQuestionListModel() {
        reviewQuizModelList = new ArrayList<>();
    }

    public void addReviewQuizModel(ReviewQuestionModel reviewQuizModel) {
        this.reviewQuizModelList.add(reviewQuizModel);
    }

    public int size() {
        return reviewQuizModelList.size();
    }

    public ReviewQuestionModel get(int i) {
        return reviewQuizModelList.get(i);
    }

}

package com.algosenpai.app.logic.models;

import java.util.ArrayList;

public class ReviewTracingListModel {

    private ArrayList<ReviewTracingModel> reviewTracingModelArrayList;

    public ReviewTracingListModel() {
        reviewTracingModelArrayList = new ArrayList<>();
    }

    public void addReviewTracingModel(ReviewTracingModel reviewTracingModel) {
        this.reviewTracingModelArrayList.add(reviewTracingModel);
    }

    public ReviewTracingModel getReviewTracingModel(int i) {
        return reviewTracingModelArrayList.get(i);
    }

    public int size() {
        return reviewTracingModelArrayList.size();
    }
}

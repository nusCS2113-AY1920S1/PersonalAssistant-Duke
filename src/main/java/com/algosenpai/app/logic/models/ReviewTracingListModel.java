package com.algosenpai.app.logic.models;

import java.util.ArrayList;

public class ReviewTracingListModel {

    private ArrayList<String> reviewMethodList;

    public ReviewTracingListModel() {
        reviewMethodList = new ArrayList<>();
    }

    public void addReviewStep(String reviewStep) {
        this.reviewMethodList.add(reviewStep);
    }

    @Override
    public String toString() {
        StringBuilder totalString = new StringBuilder();
        for (String x: reviewMethodList) {
            totalString.append(x).append("\n");
        }
        return totalString.toString();
    }

}

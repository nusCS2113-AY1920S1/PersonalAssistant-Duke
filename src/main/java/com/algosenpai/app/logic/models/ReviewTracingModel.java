package com.algosenpai.app.logic.models;

public class ReviewTracingModel {

    private int indexLeft;

    private int indexRight;

    private String type;

    /**
     * Initialize containers to store the step in the algorithm.
     * @param indexLeft left bubble in the tracing step.
     * @param indexRight right bubble in the tracing step.
     * @param type mode of operation in the tracing step.
     */
    public ReviewTracingModel(int indexLeft, int indexRight, String type) {
        this.indexLeft = indexLeft;
        this.indexRight = indexRight;
        this.type = type;
    }

    public int getIndexLeft() {
        return indexLeft;
    }

    public int getIndexRight() {
        return indexRight;
    }

    public String getType() {
        return type;
    }

    public void setIndexLeft(int indexLeft) {
        this.indexLeft = indexLeft;
    }

    public void setIndexRight(int indexRight) {
        this.indexRight = indexRight;
    }

    public void setType(String type) {
        this.type = type;
    }
}

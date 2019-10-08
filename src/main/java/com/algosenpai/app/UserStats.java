package com.algosenpai.app;

import com.itextpdf.text.Chapter;
import javafx.util.Pair;

import java.util.ArrayList;

public class UserStats {

     static class ChapterStat {
        String chapterName;
        int totalAnswered;
        int correctAnswers;

        ChapterStat(int totalAnswered, int correctAnswers) {
            this.totalAnswered = totalAnswered;
            this.correctAnswers = correctAnswers;
        }

        public ChapterStat(String chapterName,int totalAnswered, int correctAnswers) {
            this.chapterName = chapterName;
            this.totalAnswered = totalAnswered;
            this.correctAnswers = correctAnswers;
        }
    }

    private ArrayList<ChapterStat> data;


    public UserStats(int size) {
        data = new ArrayList<ChapterStat>();
        for (int i = 0; i < size; i++) {
            data.add(new ChapterStat(0, 0));
        }
    }

    public Pair<Integer,Integer> getStats(int index) {
        return new Pair<>(data.get(index).correctAnswers, data.get(index).totalAnswered);
    }

    public Pair<Integer,Integer> getStats(String chapterName) {
        ChapterStat target = null;
        for (ChapterStat item : data) {
            if (item.chapterName.equals(chapterName)) {
                target = item;
            }
        }

        if (target == null) return null;

        return new Pair<>(target.correctAnswers, target.totalAnswered);
    }

}

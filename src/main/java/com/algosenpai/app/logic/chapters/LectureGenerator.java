package com.algosenpai.app.logic.chapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class LectureGenerator {

    private static HashMap<String, ArrayList<String>> lectureSlides = new HashMap<>();

    /**
     * Instantiates the lecture by loading all the lecture slides.
     */
    public LectureGenerator() {
        InputStream is = getClass().getResourceAsStream("/data/lectureSorting.txt");
        updateSlides("sorting", is);
        is = getClass().getResourceAsStream("/data/lectureLinkedList.txt");
        updateSlides("linkedlist", is);
        is = getClass().getResourceAsStream("/data/lectureBitmask.txt");
        updateSlides("bitmask", is);
    }

    /**
     * Updates the slides to the hashmap based on the txt files.
     * @param topic The topic is the chapter name.
     * @param is The input stream.
     */
    private void updateSlides(String topic, InputStream is) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder currentSlide = new StringBuilder();
        lectureSlides.put(topic, new ArrayList<>());
        while (true) {
            try {
                line = bufferedReader.readLine();
                if (line == null) {
                    lectureSlides.get(topic).add(currentSlide.toString());
                    return;
                } else if (line.equals("-")) {
                    lectureSlides.get(topic).add(currentSlide.toString());
                    currentSlide = new StringBuilder();
                } else {
                    currentSlide.append("\n");
                    currentSlide.append(line);
                }
            } catch (IOException e) {
                return;
            }
        }
    }

    /**
     * Picks the correct lecture according to the chapter given by the user.
     * @param selectedChapters The chapter the user selected.
     * @return The arraylist containing the slides.
     */
    public ArrayList<String> generateLecture(int selectedChapters) {
        switch (selectedChapters) {
        case 1:
            return lectureSlides.get("sorting");
        case 2:
            return lectureSlides.get("linkedlist");
        case 3:
            return lectureSlides.get("bitmask");
        default:
            return null;
        }
    }
}

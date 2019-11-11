package com.algosenpai.app.logic.chapters;

import com.algosenpai.app.utility.LogCenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class LectureGenerator {

    private static HashMap<String, ArrayList<String>> lectureSlides = new HashMap<>();

    private static final Logger logger = LogCenter.getLogger(LectureGenerator.class);

    /**
     * Instantiates the lecture by loading all the lecture slides.
     */
    public LectureGenerator() {
        logger.info("Loading lecture data from the text files...");
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
                logger.severe("Failed to display next slide in lecture mode due to IO error.");
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
            logger.info("Lecture on Sorting is chosen.");
            return lectureSlides.get("sorting");
        case 2:
            logger.info("Lecture on Linked List is chosen.");
            return lectureSlides.get("linkedlist");
        case 3:
            logger.info("Lecture on Bitmask is chosen.");
            return lectureSlides.get("bitmask");
        default:
            return null;
        }
    }
}

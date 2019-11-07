package com.algosenpai.app.logic.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class LectureCommand extends Command {

    private static Boolean isSetUp = false;
    private static HashMap<String, ArrayList<String> > lectureSlides = new HashMap<>();
    private static HashMap<String, Integer> slidePointer = new HashMap<>();
    /**
     * Create new command.
     *
     * @param inputs
     */
    public LectureCommand(ArrayList<String> inputs) {
        super(inputs);
        if (!isSetUp) {
            InputStream is = getClass().getResourceAsStream("/data/lectureSorting.txt");
            updateSlides("sorting", is);
            is = getClass().getResourceAsStream("/data/lectureLinkedList.txt");
            updateSlides("linkedlist", is);
            is = getClass().getResourceAsStream("/data/lectureBitmask.txt");
            updateSlides("bitmask", is);
            isSetUp = true;
        }
    }

    private void updateSlides(String topic, InputStream is) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuilder currentSlide = new StringBuilder();
        lectureSlides.put(topic, new ArrayList<>());
        slidePointer.put(topic, 0);
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

    @Override
    public String execute() {
        String topic = inputs.get(1);
        System.out.println(topic);
        int pointer = slidePointer.get(topic);
        ArrayList<String> currentTopicSlides = lectureSlides.get(topic);
        String currentSlide = currentTopicSlides.get(pointer);
        pointer++;
        if (pointer == currentTopicSlides.size()) {
            slidePointer.put(topic, 0);
        } else {
            slidePointer.put(topic, pointer);
        }
        return currentSlide;
    }
}

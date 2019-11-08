package com.algosenpai.app.logic.command.critical;

import com.algosenpai.app.logic.command.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class LectureCommand extends Command {

    private static Boolean isSetUp = false;
    private static HashMap<String, ArrayList<String>> lectureSlides = new HashMap<>();
    private static HashMap<String, Integer> slidePointer = new HashMap<>();
    AtomicBoolean isLectureMode;
    private static String chapter = new String();

    /**
     * Create new command.
     *
     * @param inputs The user's input.
     */
    public LectureCommand(ArrayList<String> inputs, AtomicBoolean isLectureMode) {
        super(inputs);
        this.isLectureMode = isLectureMode;
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

    public static void setChapter(String chapter) {
        LectureCommand.chapter = chapter;
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
        int pointer = slidePointer.get(topic);
        ArrayList<String> currentTopicSlides = lectureSlides.get(topic);
        String currentSlide = currentTopicSlides.get(pointer);

        if (inputs.get(0).equals("back")) {
            pointer = Math.max(0, pointer - 1);
        } else if (inputs.get(0).equals("next")) {
            pointer = Math.min(currentTopicSlides.size() - 1, pointer + 1);
        } else {
            return "I did not understand your command. Can you try either 'next' or 'back'?";
        }

        if (pointer == currentTopicSlides.size() - 1) {
            slidePointer.put(topic, 0);
            isLectureMode.set(false);
        } else {
            slidePointer.put(topic, pointer);
        }
        return currentSlide;
    }

}

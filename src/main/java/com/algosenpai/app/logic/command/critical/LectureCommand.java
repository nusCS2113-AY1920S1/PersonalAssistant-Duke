package com.algosenpai.app.logic.command.critical;

import com.algosenpai.app.logic.command.Command;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class LectureCommand extends Command {


    private AtomicBoolean isLectureMode;
    private AtomicInteger lectureSlideNumber;
    private AtomicBoolean isNewLecture;
    private ArrayList<String> currentTopicSlides;

    /**
     * Create new command.
     *
     * @param inputs The user's input.
     */
    public LectureCommand(ArrayList<String> inputs, ArrayList<String> currentTopicSlides, AtomicBoolean isLectureMode,
                          AtomicInteger lectureSlideNumber, AtomicBoolean isNewLecture) {
        super(inputs);
        this.isLectureMode = isLectureMode;
        this.isNewLecture = isNewLecture;
        this.lectureSlideNumber = lectureSlideNumber;
        this.currentTopicSlides = currentTopicSlides;
    }

    @Override
    public String execute() {
        String currentSlide = getcurrentSlide();
        switch (inputs.get(0)) {
        case "start":
            lectureSlideNumber.get();
            return currentSlide;
        case "end":
            currentSlide = currentTopicSlides.get(currentTopicSlides.size() - 1);
            reset();
            return currentSlide;
        case "back":
            lectureSlideNumber.set(Math.max(0, lectureSlideNumber.get() - 1));
            currentSlide = getcurrentSlide();
            break;
        case "next":
            lectureSlideNumber.set(Math.min(currentTopicSlides.size() - 1, lectureSlideNumber.get() + 1));
            currentSlide = getcurrentSlide();
            break;
        default:
            return "I did not understand your command. Can you try either 'next' or 'back' or 'end'?";
        }

        if (lectureSlideNumber.get() == currentTopicSlides.size() - 1) {
            reset();
        }
        return currentSlide;
    }

    private String getcurrentSlide() {
        return currentTopicSlides.get(lectureSlideNumber.get()) + "\n\n"
                + (lectureSlideNumber.get() + 1) + "/" + currentTopicSlides.size() + "\n";
    }

    private void reset() {
        lectureSlideNumber.set(0);
        isLectureMode.set(false);
        isNewLecture.set(true);
    }

}

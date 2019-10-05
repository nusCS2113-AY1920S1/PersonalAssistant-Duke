package seedu.hustler.ui.timer;

import java.util.*;

public class timer implements Runnable {

    protected int hours;
    protected int minutes;
    protected int seconds;

    public timer() {
        hours = 0;
        minutes = 0;
        seconds = 0;
    }

    public timer(String hours, String minutes, String seconds) {
        this.hours = Integer.parseInt(hours);
        this.minutes = Integer.parseInt(minutes);
        this.seconds = Integer.parseInt(seconds);
    }

    public void run() {
        try {
            while (hours != 0 || minutes != 0 | seconds != 0) {
                Thread.sleep(1000);
                decrementSeconds();
		printTimeLeft();
            }
        } catch (Exception e) {}

	System.out.println("Congralations, you made it all the way! Good job and well done!");
    }

    public void decrementSeconds() {
        if (seconds != 0) {
            seconds--;
        } else  {
            seconds = 59;
            decrementMinutes();
        }
    }

    public void decrementMinutes() {
        if (minutes != 0) {
            minutes--;
        } else {
            minutes = 59;
            decrementHours();
        }
    }

    public void decrementHours() {
        hours--;
    }

    public void printTimeLeft() {
        System.out.println("time remaining: " + hours + "hr " + minutes + "min " + seconds + "sec");
    }
}

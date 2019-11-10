package ducats;

import java.util.Timer;
import java.util.TimerTask;

//@@author rohan-av
/**
 * Metronome class used to output a metronome to the command line based on the given tempo, duration and time
 * signature.
 *
 */
public class Metronome {

    private static final String TIMER_COMPLETE = "Done!\n\n";

    private int duration;
    private int tempo;
    private int[] timeSig;
    private int completedBars;
    private int noteNumber;

    public int getDuration() {
        return duration;
    }

    public int getTempo() {
        return tempo;
    }

    public int[] getTimeSig() {
        return timeSig;
    }

    /**
     * Helper function to initialize the Metronome attributes from the given parameters.
     *
     * @param parameters the parameters that are to be assigned to the different attributes
     */
    private void initializeParameters(int[] parameters) {
        duration = parameters[0];
        tempo = parameters[1];
        timeSig = new int[]{parameters[2], parameters[3]};
        completedBars = 0;
        noteNumber = 1;
    }

    /**
     * Helper function to generate the output for each repeated task, enabling thorough information regarding the beat
     * through the generation of different output.
     *
     * @return the String to be displayed indicating the position within the bar being gone through by the Metronome
     */
    private String generateOutput() {
        if (completedBars == duration) {
            return "Done!\n\n";
        } else {
            String result = Integer.toString(noteNumber);
            if (noteNumber == 1) {
                result += "#"; // emphasis given to first note of every bar
            } else {
                result += " ";
            }
            if (noteNumber == timeSig[0]) {
                completedBars++;
            }
            noteNumber = (noteNumber == timeSig[0]) ? 1 : noteNumber + 1;
            return result;
        }
    }

    /**
     * Starts the metronome with the given parameters.
     *
     * @param parameters an integer array consisting of the different parameters the Metronome should be initialized
     *                   with
     */
    public void start(int[] parameters) {
        if (parameters[0] != -1) {
            initializeParameters(parameters);
            Timer timer = new Timer("Metronome");
            TimerTask repeatedTask = new TimerTask() {
                @Override
                public void run() {
                    String output = generateOutput();
                    if (output.equals(TIMER_COMPLETE)) {
                        cancel();
                    }
                    System.out.print("\b\b" + output);
                }
            };
            timer.scheduleAtFixedRate(repeatedTask, 1000, 60000 / tempo);
        }
    }
}

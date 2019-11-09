package Model_Classes;

import java.text.DecimalFormat;
import java.util.Arrays;

public class ProgressBar {
    private String[] bar = new String[50];
    private float total;
    private float done;

    /**
     * Constructor for Progress Bar.
     * @param total Total number of tasks that are in the task list
     * @param done Total number of completed tasks in the task list
     */
    public ProgressBar(float total, float done) {
        this.total = total;
        this.done = done;
    }

    /**
     * Displays the number of tasks completed to the total number of task in.
     * a progress bar format.
     */
    public String showBar() {
        for (int i = 0; i < 50; i++) {
            bar[i] = " ";
        }
        float percentage = 0;
        if (total >= 1) {
            percentage = done / total;
            for (int i = 0; i < (int)(percentage * 50); i++) {
                bar[i] = "=";
            }
        }
        DecimalFormat df = new DecimalFormat("#.#");
        return Arrays.toString(bar).replace(",", "").trim()
                + " " + Float.valueOf(df.format(percentage * 100)) + "%";
    }
}

package Model_Classes;

public class ProgressBar {
    private String[] bar = new String[50];
    private float total;
    private float done;

    /**
     * Constructor for Progress Bar
     * @param total Total number of tasks that are in the task list
     * @param done Total number of completed tasks in the task list
     */
    public ProgressBar(float total, float done) {
        this.total = total;
        this.done = done;
    }

    /**
     * Displays the progress bar
     */
    public void showBar() {
        for (int i=0; i<50; i++) {
            bar[i] = " ";
        }
        float percentage =0;
        if(total >= 1) {
            percentage = done / total;
            for (int i = 0; i < (int)(percentage*50); i++) {
                bar[i] = "=";
            }
        }
        System.out.print("[");
        for(int i=0; i<50; i++) {
            System.out.print(bar[i]);
        }
        System.out.print("]");
        System.out.print(" " + percentage*100 + "%");
        System.out.println();
    }
}

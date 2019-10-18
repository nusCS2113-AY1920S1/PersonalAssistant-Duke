package wallet.thread;

public class ChartThread implements Runnable {
    private Thread thread;

    /**
     * Runs the threat and stops when pie chart is completed.
     */
    @Override
    public void run() {
        printPieChart();
        stop();
    }

    /**
     * Prints the pie chart given specified stats.
     */
    public static void printPieChart() {
        char[] fill = new char[4];
        float[] percentage = new float[4];
        float radius = 8;

        fill[0] = '%';
        fill[1] = '-';
        fill[2] = '@';
        fill[3] = '*';

        percentage[0] = 0.3f;
        percentage[1] = 0.3f;
        percentage[2] = 0.3f;
        percentage[3] = 0.1f;

        for (float y = -radius; y < radius; y++) {
            char t = '-';
            for (float x = -radius; x < radius; x++) {
                if (x * x + y * y < radius * radius) {
                    double a = Math.atan2(y, x) / Math.PI / 2 + .5f;
                    t = set(fill, percentage, a);
                    System.out.print(t); //Prints circle content
                } else {
                    t = ' ';
                    System.out.print(t); //Prints spaces
                }
            }
            System.out.println(t); //Form new line
        }
    }

    /**
     * This method fills the pie chart.
     * @param fill Symbols to fill pie chart
     * @param percentage Array of percentages for each category
     * @param angle Calculated angle for the pie chart
     * @return could return a space, a symbol or recursively run function for the next symbol.
     */
    public static char set(char[] fill, float[] percentage, double angle) {

        if (percentage.length == 0) {
            return ' ';
        }

        if (angle < percentage[0]) {
            return fill[0];
        }

        char[] fillArray = new char [fill.length - 1];
        float[] percentageArray = new float [percentage.length - 1];
        angle -= percentage[0];

        for (int i = 0; i < fillArray.length; i++) {
            fillArray[i] = fill[i + 1];
            percentageArray[i] = percentage[i + 1];
        }
        return set(fillArray, percentageArray, angle);
    }

    /**
     * Custom thread.
     */
    public ChartThread() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the threat from running.
     */
    public void stop() {
        thread.interrupt();
    }
}

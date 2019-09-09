package commons;

public class Ui {
    private static String output;

    public static void showToUser(String... message) {
        StringBuilder outputBuilder = new StringBuilder();
        for (String m : message) {
            System.out.println(m);

            outputBuilder.append(m);
            outputBuilder.append("\n");
        }
        output = outputBuilder.toString();
    }

    public static void showError(String message) {
        showToUser(Message.getMessageError(message));
    }

    public static String getOutput() {
        return output;
    }
}

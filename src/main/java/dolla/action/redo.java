package dolla.action;

public class redo {
    private static int redoFlag = 0;
    private static String redoInput;

    public static void setRedoFlag(int redoFlag) {
        redo.redoFlag = redoFlag;
    }

    public static void setRedoInput(String redoInput) {
        redo.redoInput = redoInput;
    }

    public static void checkFlag() {
        if(redoFlag == 0) {
            //clear redo stack
        }
    }
}

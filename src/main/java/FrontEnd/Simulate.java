package FrontEnd;

import Farmio.Farmio;
import FrontEnd.Ui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Simulate {
    //this class now does shit if changes to farmio happen after animation
    private String basepath;
    private Farmio farmio;
    private String content;
    private final int framePerSecond = 3;
    protected Ui ui;

    public Simulate(String path, Farmio farmio) {
        ui = farmio.getUi();
        basepath = path;
        this.farmio = farmio;
    }

    public Simulate(String file) {
        content = file;
    }

    public Simulate(String path, Ui ui) {
        this.ui = ui;
        basepath= path;
        this.farmio = null;
    }
    public void showFrame(int frame) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / framePerSecond) );
        } catch (InterruptedException e) {
            e.getMessage();
        }
        ui.clearScreen();
        ui.show(GameConsole.content(ui.loadStage(basepath, frame), farmio));
    }
    public void showFrame(int frame, int delay) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / framePerSecond) );
        } catch (InterruptedException e) {
            e.getMessage();
        }
        ui.clearScreen();
        ui.show(GameConsole.content(ui.loadStage(basepath, frame), farmio));
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }
    public void simulate(int startFrame, int endFrame) {
        for (int i=startFrame; i<=endFrame; i++) {
            showFrame(i);
        }
    }
    public void showFullFrame(int frame) {
        ui.show(GameConsole.blankConsole(ui.loadFullStage(basepath, frame)));
    }

//    private ArrayList<String> stringToArrayList(String str) {
//        ArrayList<String> arrayListString = new ArrayList<>();
//        String[] stringArray = str.split("\n");
//        for (int i = 0; i < stringArray.length; i ++) {
//            arrayListString.add(stringArray[i]);
//        }
//        return arrayListString;
//    }
//
//    public void showFullFrame() {
//        ui.show(GameConsole.blankConsole(stringToArrayList(content)));
//    }
}
<<<<<<< HEAD:src/main/java/FrontEnd/Simulate.java
package FrontEnd;

import Farmio.Farmio;
import FrontEnd.Ui;

import java.util.concurrent.TimeUnit;

public class Simulate {
    //this class now does shit if changes to farmio happen after animation
    protected String basepath;
    protected int numberOfFrames;
    private final int framePerSecond = 3;
    protected Ui ui;

    public Simulate(Ui userInterface, String path, int numFrames) {
        ui = userInterface;
        basepath = path;
        numberOfFrames = numFrames;
    }
    private void nextFrame(String filePath, int frame, Farmio farmio) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / framePerSecond) );
        } catch (InterruptedException e) {
            e.getMessage();
        }
        ui.clearScreen();
        ui.show(GameConsole.content(ui.loadStage(filePath, frame), farmio));
    }
    public void simulate(Farmio farmio) {
        for (int i=0; i<numberOfFrames; i++) {
            nextFrame(basepath, i, farmio);
        }
    }
=======
package FrontEnd;

import Farmio.Farmio;
import FrontEnd.Ui;

import java.util.concurrent.TimeUnit;

public class Simulate {
    //this class now does shit if changes to farmio happen after animation
    protected String basepath;
    protected int numberOfFrames;
    private final int framePerSecond = 3;
    protected Ui ui;

    public Simulate(Ui userInterface, String path, int numFrames) {
        ui = userInterface;
        basepath = path;
        numberOfFrames = numFrames;
    }
    private void nextFrame(String filePath, int frame, Farmio farmio) {
        try {
            TimeUnit.MILLISECONDS.sleep((int) (1000 / framePerSecond) );
        } catch (InterruptedException e) {
            e.getMessage();
        }
        ui.clearScreen();
        ui.show(GameConsole.content(ui.loadStage(filePath, frame), farmio));
    }
    public void simulate(Farmio farmio) {
        for (int i=0; i<numberOfFrames; i++) {
            nextFrame(basepath, i, farmio);
        }
    }
>>>>>>> ed4e35485dbfed07c4d7337b51acfafacc82b2ac:src/main/java/FrontEnd/Simulate.java
}
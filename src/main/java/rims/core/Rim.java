package rims.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import rims.command.Command;
import rims.exception.RimsException;

public class Rim {
    private Storage storage;
    private ResourceList resources;
    private Ui ui;
    private Parser parser;

    public Rim(String resourceFilePath, String reserveFilePath) throws FileNotFoundException, ParseException {
        ui = new Ui();
        storage = new Storage(resourceFilePath, reserveFilePath);
        resources = new ResourceList(storage.getResources());
        parser = new Parser(ui, resources);
    }

    public void run() throws Exception, IOException {
        Boolean toExit = false;
        while (!toExit) {
            try {
                Command c = parser.parseInput(ui.getInput());
                c.execute(ui, storage, resources);
                toExit = c.getExitCode();
            }
            catch (RimsException e) {
                e.displayError();
            }
        }
    }

    public static void main(String[] args) throws Exception, IOException {
        new Rim("data/resources.txt", "data/reserves.txt").run();
    }
}
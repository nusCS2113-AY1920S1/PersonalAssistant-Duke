package rims.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import rims.command.Command;

public class Rim {
    private Storage storage;
    private ResourceList resources;
    private Ui ui;
    private Parser parser;

    public Rim(String filePath, String rfilepath) throws FileNotFoundException, ParseException {
        ui = new Ui();
        storage = new Storage(filePath, rfilepath);
        resources = new ResourceList(storage.getResources(), ui);
        
        parser = new Parser(resources, ui);
    }

    public void run() throws Exception, IOException {
        Boolean toExit = false;
        while (!toExit) {
            Command c = parser.parseInput(ui.getInput());
            c.execute(ui, storage, this.resources);
            toExit = c.getExitCode();
        }
    }

    public static void main(String[] args) throws Exception, IOException {
        new Rim("data/resources.txt", "data/reserves.txt").run();
    }
}
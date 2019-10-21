package rims.core;

import rims.command.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class Rim {
    private Storage storage;
    private ResourceList resources;
    private Ui ui;
    private Parser parser;

    public Rim(String filePath) throws FileNotFoundException, ParseException {
        storage = new Storage(filePath);
        ui = new Ui();
        resources = new ResourceList(storage.getResources());
        parser = new Parser(ui);
    }

    public void run() throws Exception, IOException {
        Boolean toExit = false;
        while (!toExit) {
            Command c = parser.parseInput(ui.getInput());
            c.execute(ui, storage, resources);
            toExit = c.getExitCode();
        }
    }

    public static void main(String[] args) throws Exception, IOException {
        new Rim("data/resources.txt").run();
    }
}
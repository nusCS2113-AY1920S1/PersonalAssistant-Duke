package rims.core;

import rims.command.Command;

import rims.exception.RimsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import java.text.ParseException;

import java.util.ArrayList;

//@@author rabhijit
/**
 * The main class that instantiates all the sub-classes that carry out
 * the relevant sub-tasks of RIMS.
 */
public class Rims {
    private Storage storage;
    private ResourceList resources;
    private Ui ui;
    private Parser parser;

    /**
     * Constructor for RIMS that instantiates the necessary sub-classes for its
     * operation.
     *
     * @param resourceFilePath the file path of the document where resource data is
     *                         stored.
     * @param reserveFilePath  the file path of the document where reservation data
     *                         is stored.
     * @throws ParseException if data is stored in an invalid format and is thus
     *                        unable to be parsed
     * @throws IOException  if there is an error related to user input or printing
     *                      output.
     */
    public Rims(String resourceFilePath, String reserveFilePath) throws ParseException, RimsException, IOException {
        ui = new Ui();
        storage = new Storage(resourceFilePath, reserveFilePath);
        resources = new ResourceList(ui, storage.getResources());
        parser = new Parser(ui, resources);
    }

    /**
     * This method repeatedly runs the parser, which obtains and parses the input, and
     * depending to the parsed input, creates an executable command, which then carries out
     * the necessary tasks. Will halt when a command issues an exit code of true.
     * @throws ParseException if input is un-parsable
     * @throws IOException if there is an error in reading input or printing output
     */
    public void run() throws ParseException, IOException {
        Boolean toExit = false;
        while (!toExit) {
            try {
                Command c = parser.parseInput(ui.getInput());
                c.execute(ui, storage, resources);
                parser.setPrevCommand(c);
                toExit = c.getExitCode();
            } catch (RimsException e) {
                e.displayError();
            }
        }
    }

    //@@author rabhijit
    /**
     * The main method that calls the RIMS constructor and sets the ball rolling.
     * @throws FileNotFoundException if file path does not exist
     * @throws ParseException if any input is un-parsable
     * @throws IOException if there is an error in reading input or printing output
     * @throws RimsException if the input has no meaning or does not follow our format
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException, IOException, RimsException {
        new Rims("data/resources.txt", "data/reserves.txt").run();
    }
}
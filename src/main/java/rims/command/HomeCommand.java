package rims.command;

import rims.core.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

public class HomeCommand extends Command {
    public void execute(Ui ui, Storage storage, ResourceList resources) {
        ui.welcome();
        ui.printEmptyLine();
        ui.printLine();
    }
}
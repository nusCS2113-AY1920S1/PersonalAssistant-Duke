package controllers;

import models.ITask;

import java.io.IOException;

public interface IViewController {
    ITask onCommandReceived(String input) throws IOException;
}

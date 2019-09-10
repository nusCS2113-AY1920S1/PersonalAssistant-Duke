package controllers;

import exceptions.DukeException;

public interface IViewController {
    void onCommandReceived(String input) throws DukeException;

    void saveData();

    void readData();
}

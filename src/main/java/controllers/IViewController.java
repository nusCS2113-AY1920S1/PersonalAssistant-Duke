package controllers;

import java.io.IOException;

public interface IViewController {
    void onCommandReceived(String input) throws IOException;
}

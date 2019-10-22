package moomoo;

import javafx.application.Application;
import moomoo.gui.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Launches the application.
 */
public class MooMooLauncher {
    /**
     * Runs the program either in GUI or CLI mode.
     * @param args If appropriate argument is given, GUI will be launched.
     */
    public static void main(String[] args) throws IOException {

        if (args.length > 0) {
            if (args[0].equals("GUI")) {
                Application.launch(Main.class, args);
            } else if (args[0].equals("CLI")) {
                if (args.length > 1) {
                    Process pr = Runtime.getRuntime().exec("rm " + args[1]);
                }
                MooMoo.main(args);
            }
        } else {
            String operatingSystem = System.getProperty("os.name").toLowerCase();
            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

            if (operatingSystem.contains("win")) {
                runWindowsCommand(path);
            } else if (operatingSystem.contains("mac")) {
                //command = "bash -c " + "java -jar " + path + " CLI";
            } else if (operatingSystem.contains("nix") || operatingSystem.contains("nux")
                    || operatingSystem.contains("aix")) {
                runLinuxCommand(path);
            }
        }
    }

    private static void runWindowsCommand(String path) throws IOException {
        path = path.replace("/", File.separator);
        path = path.substring(1);

        File tempScript = File.createTempFile("script", ".bat");

        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(
                tempScript));
        PrintWriter printWriter = new PrintWriter(streamWriter);

        printWriter.println("@echo off");
        printWriter.println("chcp 65001 > nul 2>&1 & java -jar " + path + " CLI");
        printWriter.println("(goto) 2>nul & del \"%~f0\"");
        printWriter.close();

        Runtime.getRuntime().exec("cmd /c start \"\" " + tempScript.getAbsolutePath());
    }

    private static void runLinuxCommand(String path) throws IOException {
        File tempScript = File.createTempFile("script", ".sh");

        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(
                tempScript));
        PrintWriter printWriter = new PrintWriter(streamWriter);

        printWriter.println("#!/bin/sh");
        printWriter.println("\"$@\"");
        printWriter.println("exec \"$SHELL\"");
        printWriter.close();

        Process pr = Runtime.getRuntime().exec("chmod +x " + tempScript.getAbsolutePath());

        pr = Runtime.getRuntime().exec("x-terminal-emulator -e " + tempScript.getAbsolutePath()
                + " java -jar " + path + " CLI " + tempScript.getAbsolutePath());
    }
}

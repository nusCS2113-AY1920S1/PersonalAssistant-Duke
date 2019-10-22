package moomoo;

import javafx.application.Application;
import moomoo.gui.Main;

import java.io.*;

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
            String OS = System.getProperty("os.name").toLowerCase();
            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

            if (OS.contains("win")) {
                runWindowsCommand(path);
            } else if (OS.contains("mac")) {
                //command = "bash -c " + "java -jar " + path + " CLI";
            } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
                runLinuxCommand(path);
            } else if (OS.contains("sunos")) {
                // command = "bash -c " + "java -jar " + path + " CLI";
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
        File hackScript = File.createTempFile("script", ".sh");

        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(
                hackScript));
        PrintWriter printWriter = new PrintWriter(streamWriter);

        printWriter.println("#!/bin/sh");
        printWriter.println("\"$@\"");
        printWriter.println("exec \"$SHELL\"");
        printWriter.close();

        Process pr = Runtime.getRuntime().exec("chmod +x " + hackScript.getAbsolutePath());

        pr = Runtime.getRuntime().exec("x-terminal-emulator -e " + hackScript.getAbsolutePath() + " java -jar " + path + " CLI " + hackScript.getAbsolutePath());
    }
}

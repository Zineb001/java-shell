package uk.ac.ucl.shell.app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingArgumentsException;
import uk.ac.ucl.shell.util.IOUtil;

/**
 * Prints the last N lines of a given file or stdin. If there are less than N lines,
 * prints only the existing lines without raising an exception.
 */

public class Tail implements AppInterface {

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        // Validating the arguments
        if (appArgs.isEmpty() && input == null) {
            throw new MissingArgumentsException(this.getClass().getSimpleName());
        } else if (input == null && appArgs.size() != 1 & appArgs.size() != 3 ||
                   input != null && appArgs.size() != 0 & appArgs.size() != 2) {
            throw new InvalidNumOfArgsException(this.getClass().getSimpleName());
        } else if (input == null & appArgs.size() == 3 && !appArgs.get(0).equals("-n") ||
                   input != null & appArgs.size() == 2 && !appArgs.get(0).equals("-n")) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + ": unexpected argument " + appArgs.get(0));
        }
        int tailLines = 10;
        // Handling the input
        String tailArg;
        if (input == null && appArgs.size() == 3 || input != null && appArgs.size() == 2) {
            try {
                tailLines = Integer.parseInt(appArgs.get(1));
            } catch (Exception e) {
                throw new IllegalArgumentException(this.getClass().getSimpleName() + ": " + appArgs.get(1) + " is not a valid number");
            }
            if (input == null) {
                tailArg = appArgs.get(2);
            } else {
                tailArg = "";
            }
        } else {
            if (input == null) {
                tailArg = appArgs.get(0);
            } else {
                tailArg = "";
            }
        }
        if (input == null) {
            Path tailPath = Path.of(ShellProperties.currentDirectory, tailArg);
            if (!Files.exists(tailPath) || Files.isDirectory(tailPath) || !Files.isReadable(tailPath) || !Files.isWritable(tailPath)) {
                throw new FileNotFoundException(this.getClass().getSimpleName()+ ": " + tailArg + " does not exist");
            }
            try {
                FileInputStream fInputStream = new FileInputStream(tailPath.toFile());
                for (String line : displayResult(fInputStream, tailLines)) {
                    output.write(line.getBytes());
                }
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(this.getClass().getSimpleName()+ ": " + tailArg + " can not be opened");
            } 
        } else {
            for (String line : displayResult(input, tailLines)) {
                output.write(line.getBytes());
            }
        }
    }

    // Method to read and return the last n lines of a file
    private ArrayList<String> displayResult(InputStream input, int lines) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> displayLines = new ArrayList<>();
        String line;
        int lineIndex;
        while ((line = IOUtil.readLine(input)) != null) {
            result.add(line);
        }
        // Setting up the index of the first line to be displayed
        if (lines > result.size()) {
            lineIndex = 0;
        } else {
            lineIndex = result.size() - lines;
        }

        // Adding the lines to be displayed to an ArrayList
        for (int i = lineIndex; i < result.size(); i++) {
            displayLines.add(result.get(i));
        }
        return displayLines;
    }

}

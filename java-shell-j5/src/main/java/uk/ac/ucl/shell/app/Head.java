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
 * Prints the first N lines of a given file or stdin. If there are less than N lines,
 * prints only the existing lines without raising an exception.
 */

public class Head implements AppInterface {

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        // Validating the arguments
        if (appArgs.isEmpty() && input == null) {
            throw new MissingArgumentsException(this.getClass().getSimpleName());
        } else if (input == null && appArgs.size() != 1 & appArgs.size() != 3 ||
                   input != null && appArgs.size() != 0 & appArgs.size() != 2) {
            throw new InvalidNumOfArgsException(this.getClass().getSimpleName() + ": wrong number of arguments");
        } else if (input == null & appArgs.size() == 3 && !appArgs.get(0).equals("-n") ||
                   input != null & appArgs.size() == 2 && !appArgs.get(0).equals("-n")) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + ": unexpected argument " + appArgs.get(0));
        }
        int headLines = 10;
        // Handling the input
        String headArg;
        if (input == null && appArgs.size() == 3 || input != null && appArgs.size() == 2) {
            try {
                headLines = Integer.parseInt(appArgs.get(1));
            } catch (Exception e) {
                throw new IllegalArgumentException(this.getClass().getSimpleName() + ": " + appArgs.get(1) + " is not a valid number");
            }
            if (input == null) {
                headArg = appArgs.get(2);
            } else {
                headArg = "";
            }
        } else {
            if (input == null) {
                headArg = appArgs.get(0);
            } else {
                headArg = "";
            }
        }
        if (input == null) {
            Path headPath = Path.of(ShellProperties.currentDirectory, headArg);
            if (!Files.exists(headPath) || Files.isDirectory(headPath) || !Files.isReadable(headPath) || !Files.isWritable(headPath)) {
                throw new FileNotFoundException(this.getClass().getSimpleName()+ ": " + headArg + " does not exist");
            }
            try {
                FileInputStream fInputStream = new FileInputStream(headPath.toFile());
                for (String line : displayResult(fInputStream, headLines)) {
                    output.write(line.getBytes());
                }
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(this.getClass().getSimpleName()+ ": " + headArg + " can not be opened");
            } 
        } else {
            for (String line : displayResult(input, headLines)) {
                output.write(line.getBytes());
            }
        }
    }


    // Method to read and return the first n lines of a file
    private ArrayList<String> displayResult(InputStream input, int lines) throws IOException {
        ArrayList<String> displayLines = new ArrayList<>();
        for (int i = 0; i < lines; i++) {
            String line = IOUtil.readLine(input);
            if (line == null) {
                break;
            }
            displayLines.add(line);
        }
        return displayLines;
    }

}
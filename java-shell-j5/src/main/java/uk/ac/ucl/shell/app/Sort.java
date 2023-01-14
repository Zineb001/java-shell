package uk.ac.ucl.shell.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

/**
 * Sorts the contents of a file/stdin line by line and prints the result to stdout.
 */

public class Sort implements AppInterface {

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        // Validating the arguments
        if (appArgs.isEmpty() && input == null) {
            throw new MissingArgumentsException(this.getClass().getSimpleName());
        } else if (input == null && appArgs.size() != 1 & appArgs.size() != 2 ||
                   input != null && appArgs.size() != 0 & appArgs.size() != 1) {
            throw new InvalidNumOfArgsException(this.getClass().getSimpleName() + ": wrong number of arguments");
        } else if (input == null & appArgs.size() == 2 && !appArgs.get(0).equals("-r") ||
                   input != null & appArgs.size() == 1 && !appArgs.get(0).equals("-r")) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + ": unexpected argument " + appArgs.get(0));
        }
        // Handling the input
        if (input == null) {
            File headFile;
            String headArg;
            boolean reversed;
            // If there is an option
            if (appArgs.size() == 2) {
                headFile = new File(ShellProperties.currentDirectory + File.separator + appArgs.get(1));
                headArg = appArgs.get(1);
                reversed = true;
            } else {
                // If there is no option
                headFile = new File(ShellProperties.currentDirectory + File.separator + appArgs.get(0));
                headArg = appArgs.get(0);
                reversed = false;
            }
            if (!headFile.exists()) {
                throw new FileNotFoundException(this.getClass().getSimpleName()+ ": " + headArg + " does not exist");
            }
            FileInputStream fInputStream;
            try {
                fInputStream = new FileInputStream(headFile);
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException(this.getClass().getSimpleName()+ ": " + headArg + " does not exist");
            } 
            executeSort(fInputStream, output, reversed);
            try {
                fInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("sort: " + headArg + ": error closing file");
            }
        } else {
            executeSort(input, output, appArgs.size() == 1);
        }
    }

    private void executeSort(InputStream input, OutputStream output, boolean reversed) throws IOException {
        ArrayList<String> lines = readLines(input);
        List<String> sortedLines = sortLines(lines);
        if (reversed) {
            // Reversing the list
            Collections.reverse(sortedLines);
            for (String line : sortedLines) {
                output.write(line.getBytes());
                output.write(System.getProperty("line.separator").getBytes());
            }
        } else {
            for (String line : sortedLines) {
                output.write(line.getBytes());
                output.write(System.getProperty("line.separator").getBytes());
            }
        }
    }

    private List<String> sortLines(ArrayList<String> lines) {
        Collections.sort(lines);
        return lines;
    }

    // Read lines from input
    protected ArrayList<String> readLines(InputStream input) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        int character;
        try {
            while ((character = input.read()) != -1) {
                word.append((char) character);
                String wordString = word.toString();
                if (wordString.endsWith(System.getProperty("line.separator"))) {
                    words.add(wordString.substring(0, wordString.lastIndexOf(System.getProperty("line.separator"))));
                    word = new StringBuilder();
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading from input");
        }
        // Add the last word, even if it is a newline
        if(word.length() > 0) {
            words.add(word.toString());
        }
        return words;
    }

}
package uk.ac.ucl.shell.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

/**
 * Detects and deletes adjacent duplicate lines from an input file/stdin and prints the result to stdout.
 */

public class Uniq implements AppInterface {

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        // Validating the arguments
        if (appArgs.isEmpty() && input == null) {
            throw new MissingArgumentsException(this.getClass().getSimpleName());
        } else if (input == null && appArgs.size() != 1 & appArgs.size() != 2 ||
                   input != null && appArgs.size() != 0 & appArgs.size() != 1) {
            throw new InvalidNumOfArgsException(this.getClass().getSimpleName());
        } else if (input == null & appArgs.size() == 2 && !appArgs.get(0).equals("-i") ||
                   input != null & appArgs.size() == 1 && !appArgs.get(0).equals("-i")) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + ": unexpected argument " + appArgs.get(0));
        }

        // Handling the input
        if (input == null) {
            File headFile;
            String headArg;
            boolean isIgnoreCase;
            // If there is an option
            if (appArgs.size() == 2) {
                headFile = new File(ShellProperties.currentDirectory + File.separator + appArgs.get(1));
                headArg = appArgs.get(1);
                isIgnoreCase = true;
            } else {
                // If there is no option
                headFile = new File(ShellProperties.currentDirectory + File.separator + appArgs.get(0));
                headArg = appArgs.get(0);
                isIgnoreCase = false;
            }
            if (!headFile.exists()) {
                throw new FileNotFoundException(this.getClass().getSimpleName() + ": no such file " + headArg);
            }
            FileInputStream fInputStream;
            fInputStream = new FileInputStream(headFile);
            executeUniq(fInputStream, output, isIgnoreCase);
            fInputStream.close();
        } else {
            executeUniq(input, output, appArgs.size() == 1);
        }
        
    }

    private void executeUniq(InputStream input, OutputStream output, boolean isIgnoreCase) throws IOException {
        ArrayList<String> lines = readLines(input);
        ArrayList<String> uniqLines;
        if (!isIgnoreCase) {
            uniqLines = getUniqLines(lines);
        } else {
            uniqLines = getUniqLinesWithIgnoreCase(lines);
        }
        for (String uniqLine : uniqLines) {
            output.write((uniqLine + System.lineSeparator()).getBytes());
        }
    }

    // Case sensitive
    private ArrayList<String> getUniqLines(ArrayList<String> lines) {
        ArrayList<String> uniqLines = new ArrayList<>();
        String curLine = lines.get(0);
        uniqLines.add(curLine);
        for (int i = 1; i < lines.size(); i++) {
            // If the current line is not the same as the next line
            if (!curLine.trim().equals(lines.get(i).trim())) {
                curLine = lines.get(i);
                uniqLines.add(curLine);
            }
        }
        return uniqLines;
    }
    
    // Case insensitive
    private ArrayList<String> getUniqLinesWithIgnoreCase(ArrayList<String> lines) {
        ArrayList<String> uniqLines = new ArrayList<String>();
        String curLine = lines.get(0);
        uniqLines.add(curLine);
        for (int i = 1; i < lines.size(); i++) {
            // Ignoring the uppercase and lowercase, and also needs to be different the next line
            if (!lines.get(i).trim().equalsIgnoreCase(curLine.trim())) {
                curLine = lines.get(i);
                uniqLines.add(curLine);
            }
        }
        return uniqLines;
    }

    // Read lines from input
    protected ArrayList<String> readLines(InputStream input) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        int character;
        while ((character = input.read()) != -1) {
            word.append((char) character);
            String wordString = word.toString();
            if (wordString.endsWith(System.getProperty("line.separator"))) {
                words.add(wordString.substring(0, wordString.lastIndexOf(System.getProperty("line.separator"))));
                    word = new StringBuilder();
                }
            }
        // Add the last word, even if it is a newline
        if(word.length() > 0) {
            words.add(word.toString());
        }
        return words;
    }
    
}

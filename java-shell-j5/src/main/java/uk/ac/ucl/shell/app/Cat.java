package uk.ac.ucl.shell.app;

import java.io.*;
import java.util.ArrayList;

import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

/**
 * Concatenates the content of given files and prints it to stdout
 */

public class Cat implements AppInterface {

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        if (appArgs.isEmpty() && input == null) {
            throw new MissingArgumentsException(this.getClass().getSimpleName());
        }  
        for (String arg : appArgs) {
            FileInputStream fInputStream;
            try {
                fInputStream = new FileInputStream(ShellProperties.currentDirectory + File.separator + arg);
            } catch (Exception e) {
                throw new FileNotFoundException("File not found");
            }
            fInputStream.transferTo(output);
            // If in the last print a new line
            if (appArgs.indexOf(arg) == appArgs.size() - 1) {
                output.write(System.lineSeparator().getBytes());
            }
            fInputStream.close();
        }
        if (input != null) {
            input.transferTo(output);
        }
    }
    
}
package uk.ac.ucl.shell.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.exception.MissingArgumentsException;
import uk.ac.ucl.shell.exception.TooManyArgumentsException;

/**
 * Changes the current working directory.
 */

public class Cd implements AppInterface {

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        if (appArgs.isEmpty()) {
            throw new MissingArgumentsException(this.getClass().getSimpleName());
        } else if (appArgs.size() > 1) {
            throw new TooManyArgumentsException(this.getClass().getSimpleName());
        }
        String dirString = appArgs.get(0);
        File dir = new File(ShellProperties.currentDirectory, dirString);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalStateException(this.getClass().getSimpleName() + ": " + dirString + " is not an existing directory");
        }

        ShellProperties.currentDirectory = dir.getCanonicalPath();
    }

    
}

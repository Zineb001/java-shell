package uk.ac.ucl.shell.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A common interface that all the applications in the shell share
 */

public interface AppInterface {
    /**
     * Executes the application with the application arguments, io streams supplied. The applications define
     * the implementations of this method respectively
     *
     * @throws IOException if an IOexception occurs while the application is running
     */
    void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException;

}

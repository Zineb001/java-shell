package uk.ac.ucl.shell.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * A utility class for altering existing behavior of the applications. Overrides the execute method and
 * directs error messages to the output stream without raising exceptions.
 */
public class UnsafeAppDecorator implements AppInterface {
    private AppInterface application;

    public UnsafeAppDecorator(AppInterface app) {
        application = app;
    }

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        try {
            application.execute(appArgs, input, output);
        } catch (Exception e) {
            writer.write(e.getMessage());
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }
}
    

package uk.ac.ucl.shell.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.shell.ShellProperties;

/**
 * Outputs the current working directory followed by a newline.
 */

public class Pwd implements AppInterface {
    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(ShellProperties.currentDirectory);
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}

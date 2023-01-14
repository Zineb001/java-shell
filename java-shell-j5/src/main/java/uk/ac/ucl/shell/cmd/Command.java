package uk.ac.ucl.shell.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Command {
    void eval(InputStream input, OutputStream output) throws IOException;
}

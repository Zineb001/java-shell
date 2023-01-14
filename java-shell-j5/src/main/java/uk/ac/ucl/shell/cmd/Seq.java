package uk.ac.ucl.shell.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * execute multiple commands
 * separated by semicolons
 */

public class Seq implements Command {
    private List<Command> commands;

    public Seq() {
        this.commands = new ArrayList<>();
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void add(Command cmd){
        this.commands.add(cmd);
    }

    @Override
    public void eval(InputStream input, OutputStream output) throws IOException {
        for (Command c: this.commands)
            if (c != null)
                c.eval(input, output);
    }
}

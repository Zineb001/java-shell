package uk.ac.ucl.shell.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.exception.TooManyArgumentsException;

/**
 * Lists the content of a directory. It prints a list of files and directories separated by tabs and followed by a
 * newline. Ignores files and directories whose names start with .
 */

public class Ls implements AppInterface {

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        File currDir;
        if (appArgs.isEmpty()) {
            currDir = new File(ShellProperties.currentDirectory);
        } else if (appArgs.size() == 1) {
            currDir = new File(appArgs.get(0));
        } else {
            throw new TooManyArgumentsException(this.getClass().getSimpleName());
        }
        try {
            if (!currDir.exists() || !currDir.isDirectory()) {
                throw new IOException("No such directory");
            }
            File[] listOfFiles = currDir.listFiles();
            List<File> fileList = Arrays.stream(listOfFiles).sorted(Comparator.comparing(File::getName)).collect(Collectors.toList());
            boolean atLeastOnePrinted = false;
            for (File file : fileList) {
                if (!file.getName().startsWith(".")) {
                    writer.write(file.getName());
                    writer.write("\t");
                    writer.flush();
                    atLeastOnePrinted = true;
                }
            }
            if (atLeastOnePrinted) {
                writer.write(System.getProperty("line.separator"));
                writer.flush();
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("ls: no such directory");
        }
    }

    
}

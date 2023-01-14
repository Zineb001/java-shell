package uk.ac.ucl.shell.app;

import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.ShellUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Recursively searches for files with matching names. Outputs the list of relative paths, each followed by a newline.
 */

public class Find implements AppInterface {
    private String rootPath;

    private String pattern;
    private void init(ArrayList<String> appArgs) throws IOException {
        if (appArgs.size()<2) {
            throw new IllegalArgumentException("find missing arguments");
        }

        if (appArgs.size()>3) {
            throw new IllegalArgumentException("find with invalid arguments");
        }

        this.rootPath = "";
        if (appArgs.size()==3)
            this.rootPath = appArgs.get(0);

        if (appArgs.size()>=2) {
            if (!appArgs.get(appArgs.size()-2).equals("-name"))
                throw new IllegalArgumentException("find invalid first argument");
            this.pattern = appArgs.get(appArgs.size()-1);
        }
    }

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        init(appArgs);
        String root = ShellProperties.currentDirectory + File.separator + this.rootPath;
        List<String> files = ShellUtils.processGlobbing(root, this.pattern.replace("*", ".*?"));

        for(String f: files) {
            writer.write(
                    f.replace(ShellProperties.currentDirectory,".")
                            .replace("\\","/")
            );
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }

    }
}

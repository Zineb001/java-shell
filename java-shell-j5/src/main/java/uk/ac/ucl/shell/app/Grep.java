package uk.ac.ucl.shell.app;

import uk.ac.ucl.shell.ShellUtils;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Searches for lines containing a match to the specified pattern. The output of the command is the list of lines.
 * Each line is printed followed by a newline.
 */

public class Grep implements AppInterface {

    private String searchPattern;
    private InputStreamReader in;

    private void init(ArrayList<String> appArgs, InputStream input) throws IOException {
        List<String> files;
        if (appArgs.isEmpty()) {
            throw new MissingArgumentsException(this.getClass().getSimpleName());
        }

        if (appArgs.size() == 1 && input==null) {
            throw new InvalidNumOfArgsException(this.getClass().getSimpleName());
        }
        this.searchPattern = appArgs.get(0);
        if (input==null){
            files = new ArrayList<>();
            for (int i=1; i < appArgs.size();i++)
                files.add(appArgs.get(i));
            this.in = ShellUtils.getInputStream(files);
            return;
        }
        this.in = new InputStreamReader(input);
    }


    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        init(appArgs, input);
        Pattern grepPattern = Pattern.compile(searchPattern);
        int numOfFiles = appArgs.size() - 1;
        try (BufferedReader reader = new BufferedReader(this.in)) {
            String line = null;
            String filename = "";
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("@File=")){
                    filename = line;
                }
                else{
                    Matcher matcher = grepPattern.matcher(line);
                    if (matcher.find()) {
                        if (numOfFiles > 1) {
                            writer.write(filename.substring(6, filename.length()));
                            writer.write(":");
                        }
                        writer.write(line);
                        writer.write(System.getProperty("line.separator"));
                        writer.flush();
                    }
                    
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("grep: cannot open File");
        }
    }

}

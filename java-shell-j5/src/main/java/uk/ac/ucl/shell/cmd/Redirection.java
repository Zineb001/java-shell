package uk.ac.ucl.shell.cmd;

import uk.ac.ucl.shell.ShellProperties;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * redirects to chosen directory by changing the currentDir in properties
 * @see ShellProperties
 */

public class Redirection {
    private String type;
    private InputStream in;
    private OutputStream out;

    public String getType() {
        return type;
    }

    public Redirection(String type, String filename) {
        this.type = type;
        String filePathName = ShellProperties.currentDirectory + File.separator + filename;
        File currFile = new File(filePathName);
        /** follow < sign to redirect file  + exception handling
         */
        if (this.type.equals("<")){
            if (currFile.exists()) {
                Path filePath = Paths.get(filePathName);
                try {
                    this.in = (InputStream) Files.newInputStream(filePath);
                } catch (IOException e) {
                    throw new RuntimeException("cat: cannot open " +filename);
                }
            } else {
                throw new IllegalArgumentException("file : "+filename+" does not exist");
            }

        } else if (this.type.equals(">")) {
            /**if the file doesn't exist, create a new one
             */
            if (!currFile.exists()) {
                try {
                    currFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException("cannot create file "+filename);
                }
            }
            try {
                this.out = new FileOutputStream(currFile,false);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("cannot open file "+filename+" for write");
            }

        }else{
            throw new RuntimeException("Invalid Type redirection " +type);
        }


    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }
}

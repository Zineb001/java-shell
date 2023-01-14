package uk.ac.ucl.shell.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * A utility class for handling io operations
 */

public class IOUtil {
    /**
     * reads line from input stream, mainly used for readling lines from files
     * @param input input stream to be read
     * @return string of the line that is being read from the input
     */
    public static String readLine(InputStream input) throws IOException {
        StringBuilder curWord = new StringBuilder();
        int charOne = input.read();
        if (charOne == -1) {
            return null;
        } else {
            curWord.append((char) charOne);
        }
        int ch;
        while ((ch = input.read()) != -1) {
            curWord.append((char) ch);
            String curWordStr = curWord.toString();
            if (curWordStr.endsWith(System.getProperty("line.separator"))) {
                break;
            }
        }
        // If the file does not end with a new line, add one
        if (!curWord.toString().endsWith(System.getProperty("line.separator"))) {
            curWord.append(System.getProperty("line.separator"));
        }
        return curWord.toString();
    }
}

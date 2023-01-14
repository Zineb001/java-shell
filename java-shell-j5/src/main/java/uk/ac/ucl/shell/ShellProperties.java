package uk.ac.ucl.shell;

/**
 * Gives the absolute paths of the root directory and current directory as global variables
 */
public class ShellProperties {
    public static String rootDirectory = System.getProperty("user.dir");
    public static String currentDirectory = System.getProperty("user.dir");
}

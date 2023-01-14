package uk.ac.ucl.shell.exception;

/**
 * An exception that extends the IllegalArgumentException class and is thrown when arguments
 * that are required are missing
 */

public class MissingArgumentsException extends IllegalArgumentException{
    public MissingArgumentsException(String appName)
    {
        super(appName + ": missing arguments");
    }

}

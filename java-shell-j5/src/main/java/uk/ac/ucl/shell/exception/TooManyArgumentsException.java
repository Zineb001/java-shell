package uk.ac.ucl.shell.exception;


/**
 * An exception that extends the IllegalArgumentException class and is thrown when too many arguments are provided
 */

public class TooManyArgumentsException extends IllegalArgumentException{
    public TooManyArgumentsException(String appName)
    {
        super(appName + ": too many arguments");
    }

}

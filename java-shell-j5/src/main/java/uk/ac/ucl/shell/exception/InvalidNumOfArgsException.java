package uk.ac.ucl.shell.exception;

/**
 * A runtime exception that is thrown to indicate the number of arguments being supplied is invalid
 */

public class InvalidNumOfArgsException extends IllegalArgumentException{
    public InvalidNumOfArgsException(String appName){
        super(appName + ": wrong number of arguments");
    }
}

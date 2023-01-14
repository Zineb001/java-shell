package uk.ac.ucl.shell.app;

public class AppFactory {
    /**
     * A factory class for creating application objects
     */

    public AppInterface makeShellApp(String appType){
        /**
         * @param appType a string argument corresponds to the application to the application to be created
         *
         * @return the application object being instantiated
         */
        // Handling the unsafe app type input from the user that is "_(appType)"
        if (appType.startsWith("_")) {
            appType = appType.substring(1);
            return new UnsafeAppDecorator(makeShellApp(appType));
        }

        // Handling the app type input from the user
        switch (appType) {
            case "echo":
                return new Echo();
            case "pwd":
                return new Pwd();
            case "cd":
                return new Cd();
            case "ls":
                return new Ls();
            case "cat":
                return new Cat();
            case "head":
                return new Head();
            case "tail":
                return new Tail();
            case "grep":
                return new Grep();
            case "sort":
                return new Sort();
            case "uniq":
                return new Uniq();
            case "find":
                return new Find();
            case "cut":
                return new Cut();
            default:
                throw new RuntimeException(appType + ": unknown application");
        }

    }
}

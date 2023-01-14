package uk.ac.ucl.shell.cmd;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.ShellUtils;
import uk.ac.ucl.shell.app.AppFactory;
import uk.ac.ucl.shell.app.AppInterface;
import uk.ac.ucl.shell.app.Find;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Call implements Command {
    private AppInterface app;
    private ArrayList<String> appArgs;

    private List<Redirection> redirections;

    public AppInterface getApp() {
        return app;
    }

    public Call(String appName) {
        this.app = new AppFactory().makeShellApp(appName);
        this.appArgs = new ArrayList<>();
        this.redirections = new ArrayList<>();
    }

    public void  addArg(String arg){

        /**
         * Not Subcommand
         */
        if (!arg.contains("`")){
            /**
             Globbing : identify files if * exists in argument
             */
            if (arg.contains("*") && !(this.app instanceof Find)) {
                try {
                    String[] pathAndPattern =  ShellUtils.getGlobbingParam(arg);
                    String rootPath = pathAndPattern[0];
                    if (rootPath==null)
                        rootPath = ShellProperties.currentDirectory;
                    else
                        rootPath = ShellProperties.currentDirectory+File.separator+pathAndPattern[0];

                    String pattern = pathAndPattern[1].replace("*", ".*?");

                    for (String a:ShellUtils.processGlobbing(rootPath, pattern)){
                        this.appArgs.add(
                                a.replace(ShellProperties.currentDirectory+File.separator,"")
                                        .replace("\\","/")
                        );
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                // Simple Argument
                this.appArgs.add(arg);
            }
            return;
        }
        /**
         subCommand : to execute and transform the output to arguments in the principal call
         */
        try {
            String subCommand = ShellUtils.extractSubCommand(arg);
            // we'll evaluate sunCommand with Shell and retrieve the result in the object out below
            OutputStream out = new ByteArrayOutputStream();
            Shell.eval(subCommand, out);
            // out.toString() concatenates lines with tab and add EOL in the end
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray())));
            String line = "";
            String argSubCommand = "";
            while ((line = reader.readLine()) != null) {
                line = line.replace(System.getProperty("line.separator")," ");
                if (argSubCommand.length()==0)
                    argSubCommand = line;
                else
                    argSubCommand = argSubCommand+" "+line;
            }
            this.appArgs.add(ShellUtils.replaceSubCommand(arg, argSubCommand));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void addRedirection(Redirection redirection) {
        this.redirections.add(redirection);
    }

    @Override
    public void eval(InputStream input, OutputStream output) throws IOException {

        InputStream in = input;
        OutputStream out = output;
        for (Redirection r : this.redirections){
            if (r.getType().equals(">"))
                out = r.getOut();
            else
                in = r.getIn();
        }
        this.app.execute(appArgs, in, out);
    }
}

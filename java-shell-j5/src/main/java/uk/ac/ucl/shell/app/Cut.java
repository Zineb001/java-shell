package uk.ac.ucl.shell.app;

import uk.ac.ucl.shell.ShellUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cuts out sections from each line of a given file or stdin and prints the result to stdout.
 */

public class Cut implements AppInterface {
    private String positions;
    private InputStreamReader in;

    private void init(ArrayList<String> appArgs, InputStream input) throws IOException {
        String filename = null;

        if (appArgs.size() > 3 || appArgs.size() < 2) {
            throw new IllegalArgumentException("head with invalid arguments");
        }

        if (!appArgs.get(0).equals("-b"))
            throw new IllegalArgumentException("head with invalid first argument");

        this.positions = appArgs.get(1);

        if (appArgs.size()==3){
            filename = appArgs.get(2);
            List<String> files;
            files = new ArrayList<>();
            files.add(filename);
            this.in = ShellUtils.getInputStream(files);
            return;
        }

        if (filename == null)
            this.in = new InputStreamReader(input);

    }

    private String cut(String s){
        String ans = "";
        List<Integer> listPos = new ArrayList<>();
        String[] commaGroup = this.positions.split(",");
        for (String commaPart:commaGroup){
            if (commaPart.startsWith("-"))
                commaPart = "1"+commaPart;
            try {
                int pos = Integer.parseInt(commaPart);

                if (pos > 0 && pos <= s.length()){
                    listPos.add(pos-1);
                }
            } catch (Exception tempException) {
                String[] minusGroup = new String[2];
                if (commaPart.startsWith("-"))
                    minusGroup[0] = "1";
                else
                    minusGroup[0] = commaPart.split("-")[0];
                if (commaPart.substring(commaPart.length()-1).equals("-"))
                    minusGroup[1] = "1000";
                else
                    minusGroup[1] = commaPart.split("-")[1];
                try {
                    int posFrom = Integer.parseInt(minusGroup[0]);
                    int posTo = Integer.parseInt(minusGroup[1]);
                    if (posTo > s.length())
                        posTo = s.length();
                    if (posFrom > 0 && posFrom <= posTo)
                        for (int i=posFrom-1; i < posTo; i++){
                            listPos.add(i);
                        }
                } catch (Exception e) {
                    throw new RuntimeException("Position Error");
                }
            }
        }
        Collections.sort(listPos);
        int olsPos = -1;
        for (int p:listPos){
            if (olsPos != p){
                ans = ans + s.substring(p, p+1);
                olsPos = p;
            }
        }
        return ans;
    }

    @Override
    public void execute(ArrayList<String> appArgs, InputStream input, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        init(appArgs, input);
        try (BufferedReader reader = new BufferedReader(this.in)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("@File=")){
                    writer.write(cut(line));
                    writer.write(System.getProperty("line.separator"));
                    writer.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("head: cannot read file or inputStream");
        }

    }
}

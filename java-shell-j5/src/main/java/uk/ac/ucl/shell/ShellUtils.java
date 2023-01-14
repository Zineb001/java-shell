package uk.ac.ucl.shell;

import org.antlr.v4.runtime.tree.ParseTree;
import uk.ac.ucl.shell.cmd.Call;
import uk.ac.ucl.shell.cmd.Pipe;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * A utility class that is used in the implementation of commands, mainly used internally for the implementations
 */

public class ShellUtils {
    public static void pipe2List(Pipe p, List<Call> calls){
        if (p.getLeft() != null)
            pipe2List(p.getLeft(),calls);
        calls.add(p.getCall());
    }
    public static void getTreeLeaves(ParseTree tree, List<String> args) {
        if (tree == null){
            return;
        }
        if (tree.getChildCount() == 0) {
            args.add(tree.getText());
        }

        for (int i = 0; i < tree.getChildCount(); i++) {
            getTreeLeaves(tree.getChild(i), args);
        }
    }

    public static String extractSubCommand(String s) {
        int i = 0;
        String ans = "";
        boolean started = false;
        while (i < s.length()){
            if (s.charAt(i) == '`'){
                if (started)
                    return ans;
                if (!started)
                    started = true;
            }else{
                if (started)
                    ans = ans + s.charAt(i);
            }
            i++;
        }
        return "";
    }

    public static String replaceSubCommand(String s, String result) {
        String sub =  "`"+extractSubCommand(s)+"`";
        return s.replace(sub, result);
    }

    public static String[] getGlobbingParam(String s){
        String[] ans = new String[2];
        int i = s.length();
        while (i > 0){
            i--;
            if (s.charAt(i) == File.separator.charAt(0)){
                ans[0] = s.substring(0,i);
                ans[1] = s.substring(i+1);
                return ans;
            }
        }
        ans[0] = null;
        ans[1] = s;
        return ans;
    }
    public static List<String> processGlobbing(String rootPath , String pattern) throws IOException {

        final Path dir = Paths.get(rootPath);

        final List<String> lfs = new ArrayList<>();
        try {
            Stream<Path> results = Files.find(dir,
                    Integer.MAX_VALUE,
                    (path, basicFileAttributes) -> path.toFile().getName().matches(pattern)
            );

            results.forEach(p -> lfs.add(p.toString()));
            results.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return lfs;
    }

    public static InputStreamReader getInputStream(List<String> files) throws IOException {
        Vector v = new Vector<>(files.size());
        for (int i =  0; i < files.size(); i++) {
            String filename = files.get(i);
            String homePath = ShellProperties.currentDirectory + File.separator;
            String filePath = filename;
            if (!filename.contains(File.separator))
                filePath = homePath+filename;

            File f = new File(filePath);
            if (!f.exists()) {
                throw new RuntimeException("File " + filename + " does not exist");
            }
            String fileTag = "@File="+filename+System.getProperty("line.separator");
            InputStream fs = new ByteArrayInputStream(fileTag.getBytes());
            v.add(fs);
            InputStream is = Files.newInputStream(Path.of(filePath));
            v.add(is);
        }
        Enumeration e = v.elements();
        SequenceInputStream sis = new SequenceInputStream(e);
        InputStreamReader isr = new InputStreamReader(sis);

        return isr;
    }

}

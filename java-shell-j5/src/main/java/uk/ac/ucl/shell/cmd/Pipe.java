package uk.ac.ucl.shell.cmd;

import uk.ac.ucl.shell.ShellUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * pipe is implemented by having a list off calls
 * each pipe is a recursion of other pipes and a call
 * the minimum being call | call
 */

public class Pipe implements Command {
    private Pipe left;
    private Call call;

    public Pipe() {
    }

    public Pipe(Call call) {
        this.call = call;
    }

    public void setLeft(Pipe left) {
        this.left = left;
    }

    public Pipe getLeft() {
        return left;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public Call getCall() {
        return this.call;
    }


    @Override
    public void eval(InputStream input, OutputStream output) throws IOException {
        List<Call> pipeCalls = new ArrayList<>();
        ShellUtils.pipe2List(this, pipeCalls);
        ByteArrayInputStream in = null;
        OutputStream out;
        for (int i = 0; i < pipeCalls.size(); i++){
            if (i < pipeCalls.size()-1){
                out = new ByteArrayOutputStream();
                pipeCalls.get(i).eval(in, out);
                in = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
            }else
                pipeCalls.get(i).eval(in, output);
        }
    }
}

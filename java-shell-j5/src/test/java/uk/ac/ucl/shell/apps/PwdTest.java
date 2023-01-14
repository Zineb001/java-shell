package uk.ac.ucl.shell.apps;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;

public class PwdTest {
    @Test
    public void CorrectPathPwd() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("pwd", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Pwd.....................");
        assertEquals(scn.next(), ShellProperties.currentDirectory);
        System.out.println("OK");
        scn.close();
    }
}

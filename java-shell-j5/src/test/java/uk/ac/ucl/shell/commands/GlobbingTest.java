package uk.ac.ucl.shell.commands;

import org.junit.Test;
import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class GlobbingTest {
    @Test
    public void CorrectGlobbingTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("echo *Test.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Call Globbing Test.....................");
        assertEquals("cutTest.txt", scn.next());
        assertEquals("uniqTest.txt", scn.next());
        System.out.println("OK");
        scn.close();
    }
}

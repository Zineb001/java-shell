package uk.ac.ucl.shell.commands;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;

public class SequenceTest {
    
    @Test
    public void ValidSequenceTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo Hello ; echo World", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Valid Sequence.....................");
        assertEquals("Hello", scn.next());
        assertEquals("World", scn.next());
        System.out.println("OK");
        scn.close();
    }
}

package uk.ac.ucl.shell.commands;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;

public class PipeTest {
    
    @Test
    public void ValidPipeTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cat unsorted.txt | sort -r", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Valid Pipe.....................");
        assertEquals("E", scn.next());
        assertEquals("D", scn.next());
        assertEquals("C", scn.next());
        assertEquals("B", scn.next());
        assertEquals("A", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void ValidMultiplePipeTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cat unsorted.txt | sort -r | uniq -i", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Valid Multiple Pipe.....................");
        assertEquals("E", scn.next());
        assertEquals("D", scn.next());
        assertEquals("C", scn.next());
        assertEquals("B", scn.next());
        assertEquals("A", scn.next());
        System.out.println("OK");
        scn.close();
    }
}

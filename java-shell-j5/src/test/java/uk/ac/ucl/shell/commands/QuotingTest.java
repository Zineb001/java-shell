package uk.ac.ucl.shell.commands;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;

public class QuotingTest {
    
    @Test
    public void CorrectDoubleQuotingTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo \"hi there\"", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Double quoting.....................");
        assertEquals("hi", scn.next());
        assertEquals("there", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void CorrectSingleQuotingTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo 'hi there'", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Single quoting.....................");
        assertEquals("hi", scn.next());
        assertEquals("there", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void DoubleQuotingWithCommandSubstitutionTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo \"`echo hi there`\"", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Command Sub with Double quoting.....................");
        assertEquals("hi", scn.next());
        assertEquals("there", scn.next());
        System.out.println("OK");
        scn.close();
    }

}

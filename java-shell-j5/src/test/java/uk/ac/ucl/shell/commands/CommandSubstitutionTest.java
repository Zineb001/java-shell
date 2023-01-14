package uk.ac.ucl.shell.commands;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;

public class CommandSubstitutionTest {

    @Test
    public void ValidCommandSubstitutionTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo `echo hello`", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Valid Command Substitution.....................");
        assertEquals("hello", scn.next());
        System.out.println("OK");
        scn.close();
    }
    
}

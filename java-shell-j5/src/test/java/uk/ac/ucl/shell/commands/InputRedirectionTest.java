package uk.ac.ucl.shell.commands;

import static org.junit.Assert.assertEquals;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;

public class InputRedirectionTest {

    @Test
    public void CatInputRedirectionTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cat < file4test1.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Input Redirection for Cat.....................");
        assertEquals("AAAA", scn.next());
        assertEquals("BBBB", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void EchoInputRedirectionTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("echo ABC < file4test1.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Input Redirection for Echo.....................");
        assertEquals("ABC", scn.next());
        System.out.println("OK");
        scn.close();
    }
}

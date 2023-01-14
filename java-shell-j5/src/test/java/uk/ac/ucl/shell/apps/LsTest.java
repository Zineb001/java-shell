package uk.ac.ucl.shell.apps;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.app.Ls;
import uk.ac.ucl.shell.exception.TooManyArgumentsException;

public class LsTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void LsMakeShellAppTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("ls temp4test", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Ls is called.....................");
        assertEquals("cutTest.txt", scn.next());
        System.out.println("OK");
        scn.close();
    }


    @Test
    public void CorrectLsTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd dir4lstest", out);
        Ls ls = new Ls();
        ArrayList<String> args = new ArrayList<>();
        ls.execute(args, in, out);
        System.out.printf("Ls.....................");
        assertEquals("cutTest.txt\tfile4test1.txt\tfile4test2.txt\t" +
                "redirection.txt\tuniqTest.txt\tunsorted.txt\t\n", out.toString());
        System.out.println("OK");
    }

    @Test
    public void DirNameProvidedTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Ls ls = new Ls();
        ArrayList<String> args = new ArrayList<>(List.of("dir4lstest"));
        ls.execute(args, in, out);
        System.out.printf("Ls with dir name.....................");
        assertEquals("cutTest.txt\tfile4test1.txt\tfile4test2.txt\t" +
                "redirection.txt\tuniqTest.txt\tunsorted.txt\t\n", out.toString());
        System.out.println("OK");
    }

    @Test
    public void LsTooManyArgumentsShouldThrowTooManyArgumentsTest() throws Exception{
        System.out.printf("Ls with too many arguments.....................");
        thrown.expect(TooManyArgumentsException.class);
        thrown.expectMessage("Ls: too many arguments");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Ls ls = new Ls();
        ArrayList<String> args = new ArrayList<>((List.of("argument1", "argument2")));
        ls.execute(args,in,out);
    }

    @Test
    public void LsInvalidDirectoryTest() throws Exception{
        System.out.printf("Ls with invalid directory.....................");
        thrown.expect(IOException.class);
        thrown.expectMessage("No such directory");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Ls ls = new Ls();
        ArrayList<String> args = new ArrayList<>((List.of("invalidDir")));
        ls.execute(args,in,out);
    }
}

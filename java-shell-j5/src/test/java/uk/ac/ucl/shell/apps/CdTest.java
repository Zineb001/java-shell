package uk.ac.ucl.shell.apps;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.app.Cd;
import uk.ac.ucl.shell.exception.MissingArgumentsException;
import uk.ac.ucl.shell.exception.TooManyArgumentsException;

public class CdTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void CorrectCdTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("pwd", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Cd.....................");
        assertEquals(scn.next(),ShellProperties.rootDirectory+ File.separator +"temp4test");
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void CdNoArgumentShouldThrowMissingArgumentsTest() throws Exception{
        System.out.printf("Cd with no arguments.....................");
        thrown.expect(MissingArgumentsException.class);
        thrown.expectMessage("Cd: missing arguments");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Cd cd = new Cd();
        ArrayList<String> args = new ArrayList<>();
        cd.execute(args,in,out);
    }

    @Test
    public void CdTooManyArgumentsShouldThrowTooManyArgumentsTest() throws Exception{
        System.out.printf("Cd with too many arguments.....................");
        thrown.expect(TooManyArgumentsException.class);
        thrown.expectMessage("Cd: too many arguments");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Cd cd = new Cd();
        ArrayList<String> args = new ArrayList<>((List.of("argument1", "argument2")));
        cd.execute(args,in,out);
    }

    @Test
    public void CdDirectoryDoesNotExistTest() throws Exception{
        System.out.printf("Cd with invalid directory.....................");
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Cd: invalidDirectory is not an existing directory");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Cd cd = new Cd();
        ArrayList<String> args = new ArrayList<>(List.of("invalidDirectory"));
        cd.execute(args,in,out);
    }

}

package uk.ac.ucl.shell.apps;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.app.Grep;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

public class GrepTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void CorrectGrepTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd  temp4test", out);
        Shell.eval("grep AAAA file4test1.txt file4test2.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Grep.....................");
        assertEquals(scn.next(),"file4test1.txt:AAAA");
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void GrepInvalidNumArgumentShouldThrowExceptionTest() throws Exception{
        System.out.printf("Grep with wrong num of arguments.....................");
        thrown.expect(InvalidNumOfArgsException.class);
        thrown.expectMessage("Grep: wrong number of arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Grep grep = new Grep();
        ArrayList<String> args = new ArrayList<>(List.of("AAAA"));
        grep.execute(args,in,out);
    }

    @Test
    public void GrepMissingArgumentsExceptionTest() throws Exception{
        System.out.printf("Grep with missing arguments.....................");
        thrown.expect(MissingArgumentsException.class);
        thrown.expectMessage("Grep: missing arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Grep grep = new Grep();
        ArrayList<String> args = new ArrayList<>();
        grep.execute(args,in,out);
    }

    @Test
    public void GrepWithNonNullInput() throws Exception {
        System.out.printf("Grep with input.....................");
        String s = "ddff\naaa\nbbb\neeee\nkkk";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Grep grep = new Grep();
        ArrayList<String> args = new ArrayList<>(List.of("ddff"));
        grep.execute(args,in,out);
        assertEquals("ddff\n", out.toString());
        System.out.println("OK");
    }


}

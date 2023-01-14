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
import uk.ac.ucl.shell.app.Tail;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

public class TailTest {
    @Rule public ExpectedException thrown= ExpectedException.none();

    @Test
    public void CorrectTailTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("tail -n 1 file4test1.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Tail.....................");
        assertEquals("BBBB", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void TailWithInputTest() throws Exception{
        System.out.printf("Tail with input .....................");
        String s = "ddff\naaa\nbbb\neeee\nkkk";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Tail tail = new Tail();
        ArrayList<String> args = new ArrayList<>(List.of("-n", "3"));
        tail.execute(args,in,out);
        assertEquals("bbb\neeee\nkkk\n", out.toString());
        System.out.println("OK");
    }

    @Test
    public void TailWithOnlyFileNameTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd temp4test", out);
        Tail tail = new Tail();
        ArrayList<String> args = new ArrayList<>(List.of("file4test1.txt"));
        tail.execute(args,in,out);
        System.out.printf("Tail with only file name.....................");
        assertEquals("AAAA\nBBBB\n",out.toString());
        System.out.println("OK");

    }

    @Test
    public void TailWithInputWithoutNFlagTest() throws Exception{
        System.out.printf("Tail with input but no n flag.....................");
        String s = "ddff\naaa\nbbb\neeee\nkkk";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Tail tail = new Tail();
        ArrayList<String> args = new ArrayList<>();
        tail.execute(args,in,out);
        assertEquals("ddff\naaa\nbbb\neeee\nkkk\n", out.toString());
        System.out.println("OK");
    }

    @Test
    public void TailNoArgumentShouldThrowMissingArgumentsTest() throws Exception{
        System.out.printf("Tail with no arguments.....................");
        thrown.expect(MissingArgumentsException.class);
        thrown.expectMessage("Tail: missing arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Tail tail = new Tail();
        ArrayList<String> args = new ArrayList<>();
        tail.execute(args,in,out);
    }

    @Test
    public void TailMissingNFlagTest() throws Exception{
        System.out.printf("Tail with missing n flag test.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Tail: unexpected argument -notNFlag");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Tail tail = new Tail();
        ArrayList<String> args = new ArrayList<>(List.of("-notNFlag", "10", "file4test1.txt"));
        tail.execute(args,in,out);
    }

    @Test
    public void TailInvalidNumArgumentShouldThrowExceptionTest() throws Exception{
        System.out.printf("Tail with wrong num of arguments.....................");
        thrown.expect(InvalidNumOfArgsException.class);
        thrown.expectMessage("Tail: wrong number of arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Tail tail = new Tail();
        ArrayList<String> args = new ArrayList<>(List.of("10", "file4test1.txt"));
        tail.execute(args,in,out);
    }

    @Test
    public void TailFileDoesNotExistTest() throws Exception{
        System.out.printf("Tail with invalid file name.....................");
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("Tail: invalidFileName.txt does not exist");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Tail tail = new Tail();
        ArrayList<String> args = new ArrayList<>(List.of("invalidFileName.txt"));
        tail.execute(args,in,out);
    }

    @Test
    public void TailNullInputWithInvalidArgumentTest() throws Exception{
        System.out.printf("Tail null input with invalid argument.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Tail: notANumber is not a valid number");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Tail tail = new Tail();
        ArrayList<String> args = new ArrayList<>(List.of("-n", "notANumber", "file4test1.txt"));
        tail.execute(args,in,out);
    }


}

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
import uk.ac.ucl.shell.app.Head;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

public class HeadTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void CorrectHeadTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("head -n 1 file4test1.txt", out);
        Scanner scn = new Scanner(in);
        try {
            System.out.printf("Head.....................");
            assertEquals("AAAA", scn.next());
            System.out.println("OK");
        } catch (Throwable t) {
            System.out.println("Failed");
        }
        scn.close();
    }

    @Test
    public void HeadWithInputTest() throws Exception{
        System.out.printf("Head with input .....................");
        String s = "ddff\naaa\nbbb\neeee\nkkk";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Head head = new Head();
        ArrayList<String> args = new ArrayList<>(List.of("-n", "3"));
        head.execute(args,in,out);
        assertEquals("ddff\naaa\nbbb\n", out.toString());
        System.out.println("OK");
    }

    @Test
    public void HeadWithOnlyFileNameTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd temp4test", out);
        Head head = new Head();
        ArrayList<String> args = new ArrayList<>(List.of("file4test1.txt"));
        head.execute(args,in,out);
        System.out.printf("Head with only file name.....................");
        assertEquals("AAAA\nBBBB\n",out.toString());
        System.out.println("OK");

    }

    @Test
    public void HeadWithInputWithoutNFlagTest() throws Exception{
        System.out.printf("Head with input but no n flag.....................");
        String s = "ddff\naaa\nbbb\neeee\nkkk";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Head head = new Head();
        ArrayList<String> args = new ArrayList<>();
        head.execute(args,in,out);
        assertEquals("ddff\naaa\nbbb\neeee\nkkk\n", out.toString());
        System.out.println("OK");
    }

    @Test
    public void HeadNoArgumentShouldThrowMissingArgumentsTest() throws Exception{
        System.out.printf("Head with no arguments.....................");
        thrown.expect(MissingArgumentsException.class);
        thrown.expectMessage("Head: missing arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Head head = new Head();
        ArrayList<String> args = new ArrayList<>();
        head.execute(args,in,out);
    }

    @Test
    public void HeadMissingNFlagTest() throws Exception{
        System.out.printf("Head with missing n flag test.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Head: unexpected argument -notNFlag");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Head head = new Head();
        ArrayList<String> args = new ArrayList<>(List.of("-notNFlag", "10", "file4test1.txt"));
        head.execute(args,in,out);
    }


    @Test
    public void HeadInvalidNumArgumentShouldThrowExceptionTest() throws Exception{
        System.out.printf("Head with wrong num of arguments.....................");
        thrown.expect(InvalidNumOfArgsException.class);
        thrown.expectMessage("Head: wrong number of arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Head head = new Head();
        ArrayList<String> args = new ArrayList<>(List.of("10", "file4test1.txt"));
        head.execute(args,in,out);
    }

    @Test
    public void HeadFileDoesNotExistTest() throws Exception{
        System.out.printf("Head with invalid file name.....................");
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("Head: invalidFileName.txt does not exist");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Head head = new Head();
        ArrayList<String> args = new ArrayList<>(List.of("invalidFileName.txt"));
        head.execute(args,in,out);
    }

    @Test
    public void HeadNullInputWithInvalidArgumentTest() throws Exception{
        System.out.printf("Head null input with invalid argument.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Head: notANumber is not a valid number");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Head head = new Head();
        ArrayList<String> args = new ArrayList<>(List.of("-n", "notANumber", "file4test1.txt"));
        head.execute(args,in,out);
    }

    @Test
    public void HeadNonNullInputWithInvalidArgumentTest() throws Exception{
        System.out.printf("Head null input with invalid argument.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Head: notANumber is not a valid number");
        System.out.println("OK");
        String s = "ddff\naaa\nbbb\neeee\nkkk";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Head head = new Head();
        ArrayList<String> args = new ArrayList<>(List.of("-n", "notANumber"));
        head.execute(args,in,out);
    }
}

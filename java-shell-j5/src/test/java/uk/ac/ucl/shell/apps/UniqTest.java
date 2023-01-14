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
import uk.ac.ucl.shell.app.Uniq;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

public class UniqTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void NormalUniqTest() throws IOException {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("uniq uniqTest.txt", out);
        Scanner scn = new Scanner(in);
        try {
            System.out.printf("Uniq.....................");
            assertEquals("SSS", scn.next());
            assertEquals("rrr", scn.next());
            assertEquals("sss", scn.next());
            assertEquals("RRR", scn.next());
            System.out.println("OK");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        scn.close();
    }

    @Test
    public void UniqWithIFlagTest() throws Exception {
        System.out.printf("Uniq with i-flag .....................");
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd temp4test", out);
        Uniq uniq = new Uniq();
        ArrayList<String> args = new ArrayList<>(List.of("-i", "uniqTest.txt"));
        uniq.execute(args,in,out);
        assertEquals("SSS\nRRR\n", out.toString());
        System.out.println("OK");
    }

    @Test
    public void UniqWithNonNullInputTest() throws Exception {
        System.out.printf("Uniq with input.....................");
        String s = "SSS\nsss\nRRR\nrrr\nrrr";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Uniq uniq = new Uniq();
        ArrayList<String> args = new ArrayList<>();
        uniq.execute(args,in,out);
        assertEquals("SSS\nsss\nRRR\nrrr\n", out.toString());
        System.out.println("OK");
    }

    @Test
    public void UniqMissingArgumentsTest() throws Exception {
        System.out.printf("Uniq missing arguments test.....................");
        thrown.expect(MissingArgumentsException.class);
        thrown.expectMessage("Uniq: missing arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Uniq uniq = new Uniq();
        ArrayList<String> args = new ArrayList<>();
        uniq.execute(args, in, out);
    }

    @Test
    public void UniqInvalidNumOfArgsTest() throws Exception {
        System.out.printf("Uniq with wrong number of arguments test.....................");
        thrown.expect(InvalidNumOfArgsException.class);
        thrown.expectMessage("Uniq: wrong number of arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Uniq uniq = new Uniq();
        ArrayList<String> args = new ArrayList<>(List.of("-i", "uniqTest.txt", "-i"));
        uniq.execute(args, in, out);
    }

    @Test
    public void UniqIllegalArgumentTest() throws Exception {
        System.out.printf("Uniq with invalid argument test.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Uniq: unexpected argument uniqTest.txt");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Uniq uniq = new Uniq();
        ArrayList<String> args = new ArrayList<>(List.of("uniqTest.txt", "-i"));
        uniq.execute(args, in, out);
    }

    @Test
    public void UniqFileNotFoundTest() throws Exception {
        System.out.printf("Uniq with invalid file test.....................");
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("Uniq: no such file NotExist.txt");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Uniq uniq = new Uniq();
        ArrayList<String> args = new ArrayList<>(List.of("NotExist.txt"));
        uniq.execute(args, in, out);
    }

}

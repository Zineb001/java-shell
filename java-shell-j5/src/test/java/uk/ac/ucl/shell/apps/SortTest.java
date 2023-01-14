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
import uk.ac.ucl.shell.app.Sort;
import uk.ac.ucl.shell.exception.InvalidNumOfArgsException;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

public class SortTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void NormalSortTest() throws IOException {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("sort unsorted.txt", out);
        Scanner scn = new Scanner(in);
        try {
            System.out.printf("Sort.....................");
            assertEquals("A", scn.next());
            assertEquals("B", scn.next());
            assertEquals("C", scn.next());
            assertEquals("D", scn.next());
            assertEquals("E", scn.next());
            System.out.println("OK");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        scn.close();
    }

    @Test
    public void SortNoArgumentShouldThrowMissingArgumentsTest() throws Exception {
        System.out.printf("Sort with no arguments.....................");
        thrown.expect(MissingArgumentsException.class);
        thrown.expectMessage("Sort: missing arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sort sort = new Sort();
        ArrayList<String> args = new ArrayList<>();
        sort.execute(args, in, out);
    }

    @Test
    public void SortWrongArgumentNumShouldThrowInvalidNumofArgumentsTest() throws Exception {
        System.out.printf("Sort with invalid number of arguments.....................");
        thrown.expect(InvalidNumOfArgsException.class);
        thrown.expectMessage("Sort: wrong number of arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sort sort = new Sort();
        ArrayList<String> args = new ArrayList<>(List.of("-r", "unsorted.txt", "file4test1.txt"));
        sort.execute(args, in, out);
    }

    @Test
    public void SortWrongArgumentNumShouldThrowInvalidNumofArgumentsNonNullInputTest() throws Exception {
        System.out.printf("Sort with invalid number of arguments with non null input.....................");
        thrown.expect(InvalidNumOfArgsException.class);
        thrown.expectMessage("Sort: wrong number of arguments");
        System.out.println("OK");
        String s = "ddff\naaa\nbbb\neeee\nkkk";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sort sort = new Sort();
        ArrayList<String> args = new ArrayList<>(List.of("-r", "unsorted.txt"));
        sort.execute(args, in, out);
    }

    @Test
    public void SortWrongArgumentShouldThrowIllegalArgumentTest() throws Exception {
        System.out.printf("Sort with wrong argument test.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Sort: unexpected argument unsorted.txt");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sort sort = new Sort();
        ArrayList<String> args = new ArrayList<>(List.of("unsorted.txt","-r"));
        sort.execute(args, in, out);
    }

    @Test
    public void SortFileNotFoundTest() throws Exception {
        System.out.printf("Sort file not found test.....................");
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("Sort: noSuchFile.txt does not exist");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sort sort = new Sort();
        ArrayList<String> args = new ArrayList<>(List.of("noSuchFile.txt"));
        sort.execute(args, in, out);
    }


    @Test
    public void SortReverseFlagTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd temp4test", out);
        System.out.printf("Sort with reverse flag.....................");
        Sort sort = new Sort();
        ArrayList<String> args = new ArrayList<>(List.of("-r", "unsorted.txt"));
        sort.execute(args, in, out);
        assertEquals("E\nD\nC\nB\nA\n", out.toString(StandardCharsets.UTF_8));
        System.out.println("OK");
    }

    @Test
    public void SortWithNonNullInputTest() throws Exception {
        System.out.printf("Sort with non null input.....................");
        String s = "ddff\naaa\nbbb\neeee\nkkk";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sort sort = new Sort();
        ArrayList<String> args = new ArrayList<>();
        sort.execute(args, in, out);
        assertEquals("aaa\n" +
                "bbb\n" +
                "ddff\n" +
                "eeee\n" +
                "kkk\n", out.toString(StandardCharsets.UTF_8));
        System.out.println("OK");
    }

}

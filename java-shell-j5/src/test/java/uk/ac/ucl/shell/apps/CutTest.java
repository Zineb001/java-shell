package uk.ac.ucl.shell.apps;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.app.Cut;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class CutTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void ValidCutTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cut -b 1,2,3 cutTest.txt", out);
        Scanner scn = new Scanner(in);
        try {
            System.out.printf("Cut.....................");
            assertEquals("Lor", scn.next());
            assertEquals("Ult", scn.next());
            assertEquals("Ame", scn.next());
            System.out.println("OK");
        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("Failed");
        }
        scn.close();
    }

    @Test
    public void CutRangeTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd temp4test", out);
        System.out.printf("Cut with range test.....................");
        Cut cut = new Cut();
        ArrayList<String> args = new ArrayList<>(List.of("-b","1-3,5-7", "cutTest.txt"));
        cut.execute(args, in, out);
        assertEquals("Lorm i\nUltici\nAme po\n", out.toString(StandardCharsets.UTF_8));
        System.out.println("OK");
    }

    @Test
    public void CutStartsAndEndsWithHyphenTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd temp4test", out);
        System.out.printf("Cut starts and ends with hyphen test.....................");
        Cut cut = new Cut();
        ArrayList<String> args = new ArrayList<>(List.of("-b","-3,10-", "cutTest.txt"));
        cut.execute(args, in, out);
        assertEquals("Lorum dolor sit amet, consectetur adipiscing elit.\n" +
                        "Ult leo integer malesuada nunc vel risus.\n" +
                        "Ametitor eget dolor morbi non arcu risus quis. A diam maecenas sed enim ut sem.\n",
                out.toString(StandardCharsets.UTF_8));
        System.out.println("OK");
    }

    @Test
    public void CutNoFileNameTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        String s = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                "Ultricies leo integer malesuada nunc vel risus.\n" +
                "Amet porttitor eget dolor morbi non arcu risus quis. A diam maecenas sed enim ut sem.";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd temp4test", out);
        System.out.printf("Cut starts and ends with hyphen test.....................");
        Cut cut = new Cut();
        ArrayList<String> args = new ArrayList<>(List.of("-b","-3,10-"));
        cut.execute(args, in, out);
        assertEquals("Lorum dolor sit amet, consectetur adipiscing elit.\n" +
                        "Ult leo integer malesuada nunc vel risus.\n" +
                        "Ametitor eget dolor morbi non arcu risus quis. A diam maecenas sed enim ut sem.\n",
                out.toString(StandardCharsets.UTF_8));
        System.out.println("OK");
    }

    @Test
    public void ValidCutSingleCharacterTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cut -b 1 file4test2.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Cut with single character.....................");
        assertEquals("C", scn.next());
        assertEquals("D", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void ValidCutSingleRangeTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cut -b 1-2 file4test2.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Cut with single range.....................");
        assertEquals("CC", scn.next());
        assertEquals("DD", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void ValidCutEndRangeTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cut -b 2- file4test2.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Cut with end range.....................");
        assertEquals("CCC", scn.next());
        assertEquals("DDD", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void ValidCutStartRangeTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cut -b -2 file4test2.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Cut with start range.....................");
        assertEquals("CC", scn.next());
        assertEquals("DD", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void ValidCutSingleCharAndRangeTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cut -b 1,2-3 file4test2.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Cut with char and range.....................");
        assertEquals("CCC", scn.next());
        assertEquals("DDD", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void CutWithInvalidFirstArgumentTest() throws Exception {
        System.out.printf("Cut with invalid first argument.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("head with invalid first argument");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Cut cut = new Cut();
        ArrayList<String> args = new ArrayList<>(List.of("-p", "1,2-", "file4test1.txt"));
        cut.execute(args, in, out);
    }

    @Test
    public void CutWithNoArgumentTest() throws Exception {
        System.out.printf("Cut with no argument.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("head with invalid arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Cut cut = new Cut();
        ArrayList<String> args = new ArrayList<>(List.of(""));
        cut.execute(args, in, out);
    }

}

package uk.ac.ucl.shell.apps;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import uk.ac.ucl.shell.app.Find;

public class FindTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();
    
    @Test
    public void CorrectFindTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("find -name unsorted.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Find.....................");
        assertEquals(scn.next(),"./unsorted.txt");
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void FindWithGlobbingTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("find -name *Test.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Find with globbing.....................");
        assertEquals(scn.next(),"./cutTest.txt");
        assertEquals(scn.next(),"./uniqTest.txt");
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void FindMissingArgumentsTest() throws Exception{
        System.out.printf("Find missing arguments.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("find missing arguments");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Find find = new Find();
        ArrayList<String> args = new ArrayList<>((List.of("-name")));
        find.execute(args,in,out);
    }

    @Test
    public void FindEmptyArgumentTest() throws Exception{
        System.out.printf("Find empty argument.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("find missing arguments");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Find find = new Find();
        ArrayList<String> args = new ArrayList<>((List.of("")));
        find.execute(args,in,out);
    }

    @Test
    public void FindInvalidArgumentsTest() throws Exception{
        System.out.printf("Find invalid arguments.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("find with invalid arguments");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Find find = new Find();
        ArrayList<String> args = new ArrayList<>((List.of("-name", "a", "b", "c")));
        find.execute(args,in,out);
    }

    @Test
    public void FindInvalidFirstArgumentTest() throws Exception{
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        System.out.printf("Find invalid first argument.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("find invalid first argument");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd temp4test", out);
        Find find = new Find();
        ArrayList<String> args = new ArrayList<>((List.of("-unknown", "unsorted.txt")));
        find.execute(args,in,out);
    }

    @Test
    public void FindInvalidManyArgumentTest() throws Exception{
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        System.out.printf("Find invalid first argument with many argument.....................");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("find invalid first argument");
        System.out.println("OK");
        byte[] buf = {};
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Shell.eval("cd temp4test", out);
        Find find = new Find();
        ArrayList<String> args = new ArrayList<>((List.of("fileA", "fileB", "fileC")));
        find.execute(args,in,out);
    }

}

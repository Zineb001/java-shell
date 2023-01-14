package uk.ac.ucl.shell.apps;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;
import uk.ac.ucl.shell.app.Cat;
import uk.ac.ucl.shell.exception.MissingArgumentsException;

public class CatTest {
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void CorrectCatTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("cat file4test1.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Cat.....................");
        assertEquals(scn.next(),"AAAA");
        assertEquals(scn.next(),"BBBB");
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void CatWithInputTest() throws Exception{
        System.out.printf("Cat with input.....................");
        String s = "ddff\naaa\nbbb\neeee\nkkk";
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Cat cat = new Cat();
        ArrayList<String> args = new ArrayList<>();
        cat.execute(args,in,out);
        assertEquals("ddff\naaa\nbbb\neeee\nkkk", out.toString());
        System.out.println("OK");
    }

    @Test
    public void CatNoArgumentShouldThrowMissingArgumentsTest() throws Exception{
        System.out.printf("Cat with no arguments.....................");
        thrown.expect(MissingArgumentsException.class);
        thrown.expectMessage("Cat: missing arguments");
        System.out.println("OK");
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Cat cat = new Cat();
        ArrayList<String> args = new ArrayList<>();
        cat.execute(args,in,out);
    }

}

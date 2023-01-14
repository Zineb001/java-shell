package uk.ac.ucl.shell.apps;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

import uk.ac.ucl.shell.Shell;

public class UnsafeAppTest {
    
    @Test
    public void CorrectUnsafeTest() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("_echo team5", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Correct Unsafe.....................");
        assertEquals(scn.next(),"team5");
        System.out.println("OK");
        scn.close();
    }

    @Test 
    public void InCorrectUnsafeTest() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("_cat unknown.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Incorrect Unsafe.....................");
        assertEquals(scn.next(),"File");
        System.out.println("OK");
        scn.close();
    }
}

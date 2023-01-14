package uk.ac.ucl.shell.apps;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.app.Echo;

public class EchoTest {

    @Test
    public void CorrectEchoTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo team5", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Echo.....................");
        assertEquals(scn.next(),"team5");
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void CorrectWithSpaceEchoTest() throws Exception {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo team 5", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Echo with space..........");
        assertEquals(scn.next(),"team");
        assertEquals(scn.next(),"5");
        System.out.println("OK");
        scn.close();
    }

     @Test
     public void NoArgsEcho() throws Exception {
         ByteArrayInputStream in = null;
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         Echo echo = new Echo();
         ArrayList<String> args = new ArrayList<>();
         echo.execute(args, in, out);
         System.out.printf("Echo with No args..................");
         assertEquals("", out.toString());
         System.out.println("OK");
     }

}

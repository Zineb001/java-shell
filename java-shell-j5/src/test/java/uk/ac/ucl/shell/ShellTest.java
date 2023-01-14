package uk.ac.ucl.shell;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ShellTest {

    @Test
    public void MainEvalMethod() throws Exception {
        System.out.printf("Main eval method test.....................");
        Shell sh = new Shell();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] arr = new String[] { "-c", "echo hello"};
        sh.main(arr);
        assertEquals("hello \n", outContent.toString());
        System.out.println("OK");
    }

    @Test
    public void MainInvalidNumberOfArgsTest() throws Exception {
        System.out.printf("Main with invalid number of arguments.....................");
        Shell sh = new Shell();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] arr = new String[] { "A", "B", "C", "D", "E" };
        sh.main(arr);
        assertEquals("COMP0010 shell: wrong number of arguments\n", outContent.toString());
        System.out.println("OK");
    }

    @Test
    public void MainUnexpectedArgumentAndUnknownAppTest() throws Exception {
        System.out.printf("Main with unexpected argument.....................");
        Shell sh = new Shell();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] arr = new String[] { "A", "B"};
        sh.main(arr);
        assertEquals("COMP0010 shell: A: unexpected argument\n" +
                "COMP0010 shell: B: unknown application\n", outContent.toString());
        System.out.println("OK");
    }


}

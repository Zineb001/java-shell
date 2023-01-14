package uk.ac.ucl.shell.commands;

import org.junit.Test;
import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellProperties;

import java.io.*;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class OutputRedirectionTest {

    @Test
    public void EchoOutputRedirectionTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval(" echo 'aaa' > redirection.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Output Redirection for Echo.....................");
        List<String> content = Files.readAllLines(Paths.get(ShellProperties.currentDirectory + File.separator + "redirection.txt"), StandardCharsets.UTF_8);
        assertEquals("aaa ", content.get(0));
        scn.close();
    }

    @Test
    public void EchoOutputWithFileNotInFolderRedirectionTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval(" echo 'aaa' > notInFolder.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Output Redirection with file not in folder for Echo.....................");
        List<String> content = Files.readAllLines(Paths.get(ShellProperties.currentDirectory + File.separator + "notInFolder.txt"), StandardCharsets.UTF_8);
        assertEquals("aaa ", content.get(0));
        File notInFolder = new File(ShellProperties.currentDirectory + File.separator + "notInFolder.txt");
        notInFolder.delete();
        scn.close();
    }

    @Test
    public void OutputRedirectionWithValidFileTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("echo ABC > outputRedir.txt", out);
        Shell.eval("cat outputRedir.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Output Redirection with valid file.....................");
        assertEquals("ABC", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void OutputRedirectionWithInvalidFileTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("echo DEF > invalid.txt", out);
        Shell.eval("cat invalid.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Output Redirection with invalid file.....................");
        assertEquals("DEF", scn.next());
        System.out.println("OK");
        scn.close();
    }

    @Test
    public void OutputRedirectionWithNoSpaceTest() throws Exception {
        ShellProperties.currentDirectory = ShellProperties.rootDirectory;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd temp4test", out);
        Shell.eval("echo ABC >outputRedir.txt", out);
        Shell.eval("cat outputRedir.txt", out);
        Scanner scn = new Scanner(in);
        System.out.printf("Output Redirection with no space....................");
        assertEquals("ABC", scn.next());
        System.out.println("OK");
        scn.close();
    }

}

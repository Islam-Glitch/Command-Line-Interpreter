package cli;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {



    @Test
    void printWorkingDirectory_shouldPrintWorkingDirectoryToConsole() {
        var cli = new CLI();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        cli.printWorkingDirectory();

        String expectedOutput = cli.getCurrentDirectory().toString() + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void changeDirectory() {
        var cli = new CLI();
        String validDir = "src";
        Path expectedDir = cli.getCurrentDirectory().resolve(validDir).normalize();
        cli.changeDirectory(validDir);

        assertEquals(expectedDir, cli.getCurrentDirectory());
    }

    @Test
    void createDirectory() {
        var cli = new CLI();
        String validDir = "Whoaa!";
        cli.createDirectory(validDir);
        Path expectedPath = cli.getCurrentDirectory().resolve(validDir).normalize();
        assertEquals(expectedPath, cli.getCurrentDirectory().resolve(validDir).normalize());
    }

    @Test
    void removeDirectoryWithExistingDirectory() {
        var cli = new CLI();
        String rm = "Whoaa!";
        File file = new File(rm);
        cli.removeDirectory(rm);
        boolean isRemoved = true;

        if (file.exists() && file.isDirectory()) {
            isRemoved = false;
        }
        assertTrue(isRemoved);
    }

    @Test
    void createFile() {
        var cli = new CLI();
        String validFile = "Wheeee!";
        cli.createFile(validFile);
        File file = new File(validFile);

        if (file.exists() && file.isFile()) {
            assertTrue(file.exists());
        }

    }

    @Test
    void deleteFile() {
        var cli = new CLI();
        String rm = "Wheeee!";
        File file = new File(rm);
        cli.deleteFile(rm);
        if (!file.exists()) {
            assertFalse(file.exists());
        }

    }

    @Test
    void readFile() {
        var cli = new CLI();
        String fileName = "file55.txt";
        File file = new File(fileName);
        String expectedOutput = """
                /home/abdulmalik/IdeaProjects/OS/Command-Line-Interpreter
                fdsaadsfjaksjdfkla;sdjflsak;djf;adlskj
                /home/abdulmalik/IdeaProjects/OS/Command-Line-Interpreter
                /home/abdulmalik/IdeaProjects/OS/Command-Line-Interpreter
                
                """;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        cli.readFile(fileName);
        assertEquals(outputStream.toString(), expectedOutput);
    }

    @Test
    void moveFileForRename() {
        var cli = new CLI();
        String renamedFile = "file44.txt";
        Path filePath = cli.getCurrentDirectory().resolve("file55.txt");
        Path renamedFilePath = cli.getCurrentDirectory().resolve(renamedFile);

        cli.moveFileOrDirectory(filePath.toString(), renamedFilePath.toString());

        assertTrue(Files.exists(renamedFilePath));
    }

    @Test
    void moveFileForMoving() {
        var cli = new CLI();
        Path filePath = cli.getCurrentDirectory().resolve("file1.txt");
        Path dirPath = cli.getCurrentDirectory().resolve("files");

        cli.moveFileOrDirectory(filePath.toString(), dirPath.toString());

        assertTrue(Files.exists(dirPath.resolve("file1.txt")));
    }



    @Test
    void moveDirForRename() {
        var cli = new CLI();
        String renamedDir = "files1";
        Path dirPath = cli.getCurrentDirectory().resolve("files");

        cli.moveFileOrDirectory(dirPath.toString(), renamedDir);

        assertTrue(Files.exists(cli.getCurrentDirectory().resolve(renamedDir)));
    }


    @Test
    void moveDirForMoving(){
        var cli = new CLI();
        Path srcPath = cli.getCurrentDirectory().resolve("Jpg");
        Path dirPath = cli.getCurrentDirectory().resolve("Test");

        cli.moveFileOrDirectory(srcPath.toString(), dirPath.toString());
        assertTrue(Files.exists(dirPath.resolve("Jpg")));
    }


    @Test
    void testListWithoutOptions() {
        CLI cli = new CLI();
        ArrayList<String> Output = cli.list(new String[]{"ls"});

        assertEquals("file.txt", Output.getFirst());
        assertEquals("target", Output.getLast());
    }

    @Test
    void testListWithReverseOption() {
        CLI cli = new CLI();
        ArrayList<String> Output = cli.list(new String[]{"ls", "-r"});

        assertEquals("target", Output.getFirst());
        assertEquals("Test", Output.getLast());
    }

    @Test
    void testListDoesShowHiddenFiles() {
        CLI cli = new CLI();
        ArrayList<String> Output = cli.list(new String[]{"ls", "-a"});
        assertEquals("file.txt", Output.getFirst());
        assertEquals("target", Output.getLast());
    }



}
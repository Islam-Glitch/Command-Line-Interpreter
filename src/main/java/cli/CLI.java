package cli;

import java.util.Scanner;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;


public class CLI {

    private Path currentDirectory;


    public CLI() {
        this.currentDirectory = Paths.get(System.getProperty("user.dir"));
    }

    public CLI(Path currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public void printWorkingDirectory() {
        if (Parser.getRedirect()) {
            redirecting(currentDirectory.toString(), Parser.getTokens()[2]);
            return;
        }
        System.out.println(this.currentDirectory);
    }

    public void changeDirectory(String newDir) {
        Path path = currentDirectory.resolve(newDir).normalize();
        File file = path.toFile();

        if (file.exists() && file.isDirectory()) {
            this.currentDirectory = path;
            System.out.println("Changed directory to: " + currentDirectory.toString());
        }
        else {
            System.out.println("Directory does not exist");
        }
   }

    public void createDirectory(String path) {
        Path newDirectoryPath = this.currentDirectory.resolve(path);
        File dir = newDirectoryPath .toFile();
        if (!dir.exists()) {
            if(dir.mkdir())
                System.out.println("Directory created");
            else
                System.out.println("Failed to create directory: " + path);
        }
        else
            System.out.println("Directory already exists");
    }

    public void removeDirectory(String path) {
        Path removedDirectoryPath = this.currentDirectory.resolve(path);
        File dir = removedDirectoryPath.toFile();
        if (dir.exists() && dir.isDirectory()) {
            dir.delete();
        }
        else
            System.out.println("Directory does not exist");
    }

    public void createFile(String fileName) {
        Path newFilePath = this.currentDirectory.resolve(fileName);
        File file = newFilePath.toFile();
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + fileName);
            }
            else
                System.out.println("File already exists: " + fileName);
        }catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) {
        Path removedFilePath = this.currentDirectory.resolve(fileName);
        File file = removedFilePath.toFile();
        if (file.delete()) {
            System.out.println("File deleted: " + fileName);
        }
        else {
            System.out.println("An error occurred");
        }
    }

    public void readFile(String fileName) {
        try {
            String output = "";
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();//Check if redirecting using getRedirect
                output += line + "\n";
            }
            reader.close();
            if (Parser.getRedirect()) {
                redirecting(output, Parser.getTokens()[3]);
                return;
            }
            System.out.println(output);

        }catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }

    }

    public void moveFileOrDirectory(String source, String destination) {
        File src = new File(source);
        File dest = new File(destination);

        if (!src.exists()) {
            System.out.println("File not found: " + source);
            return;
        }

        //moving
        if (dest.isDirectory()) {
            File newFile = new File(dest, src.getName());
            if (src.renameTo(newFile)) {//source renamed to the new file(dir)
                System.out.println("Moved: " + source + " -> " + destination );
            }
            else {
                System.out.println("An error occurred");
            }
        }
        //renaming
        else {
            if (src.renameTo(dest)) {//source renamed to destination
                System.out.println("Renamed: " + source + " to " + destination);
            }
            else {
                System.out.println("An error occurred");
            }
        }

    }

    public void redirecting(String s, String filename){ //Don't know if filename is relative or absolute path
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
                //System.out.println("File created: " + filename);
            }

            FileWriter fileWriter = new FileWriter(filename, Parser.getAppend());
            fileWriter.write(s + System.lineSeparator());
            fileWriter.close();
            //System.out.println("Data written successfully");

        }catch(IOException e){
            System.out.println("Error occurred");
        }
    }

    public void help() {
        System.out.println("Available commands:");
        System.out.println("1. pwd - Print the current working directory.");
        System.out.println("   Usage: pwd");
        System.out.println();
        System.out.println("2. cd - Change the current directory.");
        System.out.println("   Usage: cd <directory>");
        System.out.println();
        System.out.println("3. ls - List files and directories in the current directory.");
        System.out.println("   Usage: ls");
        System.out.println();
        System.out.println("4. ls -a - List all files, including hidden files.");
        System.out.println("   Usage: ls -a");
        System.out.println();
        System.out.println("5. ls -r - List files and directories in reverse order.");
        System.out.println("   Usage: ls -r");
        System.out.println();
        System.out.println("6. mkdir - Create a new directory.");
        System.out.println("   Usage: mkdir <directory_name>");
        System.out.println();
        System.out.println("7. rmdir - Remove an empty directory.");
        System.out.println("   Usage: rmdir <directory_name>");
        System.out.println();
        System.out.println("8. touch - Create a new empty file or update the timestamp of an existing file.");
        System.out.println("   Usage: touch <file_name>");
        System.out.println();
        System.out.println("9. mv - Move or rename a file or directory.");
        System.out.println("   Usage: mv <source> <destination>");
        System.out.println();
        System.out.println("10. rm - Remove a file.");
        System.out.println("    Usage: rm <file_name>");
        System.out.println();
        System.out.println("11. cat - Display the contents of a file.");
        System.out.println("    Usage: cat <file_name>");
        System.out.println();
    }
}

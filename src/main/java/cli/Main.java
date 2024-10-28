package cli;

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CLI cli = new CLI();



        while(true) {
            System.out.print("cli> ");
            String userInput = scanner.nextLine();
            Parser parser = new Parser();
            parser.parse(userInput);

            String command = parser.getTokens()[0];
            String[] uArgs = parser.getTokens();

            switch (command) {
                case "pwd":
                    cli.printWorkingDirectory();
                    break;
                case "cd":
                    cli.changeDirectory(uArgs[1]);
                    break;
                case "mkdir":
                    cli.createDirectory(uArgs[1]);
                    break;
                case "rmdir":
                    cli.removeDirectory(uArgs[1]);
                    break;
                case "touch":
                    cli.createFile(uArgs[1]);
                    break;
                case "rm":
                    cli.deleteFile(uArgs[1]);
                    break;
                case "cat":
                    cli.readFile(uArgs[1]);
                    break;
                case "mv":
                    cli.moveFileOrDirectory(uArgs[1], uArgs[2]);
                    break;
                case "exit":
                    System.exit(0);
                    break;
                case "help":
                    cli.help();
                    break;
                default:
                    System.out.println("Invalid command, Use 'help' command");
                    break;
            }
        }
    }
}


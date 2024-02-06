package io.beyonnex;

import io.beyonnex.config.DependencyConfig;
import io.beyonnex.parser.Command.*;
import io.beyonnex.parser.CommandParser;

import java.util.Scanner;

import static java.lang.System.out;

public class App {
    public static void main(String... args) {
        out.println("Welcome to Beyonnex interactive anagram checking tool!");

        Scanner scanner = new Scanner(System.in);
        DependencyConfig dependencyConfig = new DependencyConfig();
        processCommand(scanner, dependencyConfig);

        scanner.close();
    }

    private static void processCommand(Scanner scanner, DependencyConfig dependencyConfig) {
        var featureOneService = dependencyConfig.getFeatureOneService();
        var featureTwoService = dependencyConfig.getFeatureTwoService();

        out.print("Enter a command (type 'exit' to quit or 'help' for list of commands): ");
        String inputCommand = scanner.nextLine().toLowerCase();

        switch (CommandParser.parse(inputCommand)) {
            case F1Command command -> out.println(featureOneService.execute(command.firstText(), command.secondText()));
            case F2Command command -> out.println(featureTwoService.execute(command.text()));
            case HelpCommand ignore -> getHelpMessage();
            case UnknownCommand command -> out.println(command.errorMessage());
            case ExitCommand ignore -> {
                out.println("Exiting the application. Goodbye!");
                return;
            }
            default -> {} // compiler is not smart enough
        }

        processCommand(scanner, dependencyConfig);
    }

    private static void getHelpMessage() {
        out.println("""
                List of available commands:
                1. f1("A", "B") - Checks if strings A and B are anagrams
                2. f2("A") - Finds all anagrams of string A that were previously entered through f1
                3. exit - Quit the application""");
    }

}

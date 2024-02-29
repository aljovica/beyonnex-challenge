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
            case F1Command(String firstText, String secondText) -> out.println(featureOneService.execute(firstText, secondText));
            case F2Command(String text) -> out.println(featureTwoService.execute(text));
            case HelpCommand() -> getHelpMessage();
            case UnknownCommand(String errorMessage) -> out.println(errorMessage);
            case ExitCommand() -> {
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

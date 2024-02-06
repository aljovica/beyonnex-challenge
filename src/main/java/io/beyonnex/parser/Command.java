package io.beyonnex.parser;

public interface Command {
    record F1Command(String firstText, String secondText) implements Command { }
    record F2Command(String text) implements Command { }
    record HelpCommand() implements Command { }
    record ExitCommand() implements Command { }
    record UnknownCommand(String errorMessage) implements Command { }
}

package io.beyonnex.parser;

import java.util.regex.Pattern;

public interface Command {
    record F1Command(String firstText, String secondText) implements Command {
        public static final Pattern PATTERN = Pattern.compile("^\\s*f1\\(\"([^\"]*)\"\\s*,\\s*\"([^\"]*)\"\\)\\s*$"); // ^\s*f1\("([^"]*)"\s*,\s*"([^"]*)"\)\s*$
    }
    record F2Command(String text) implements Command {
        public static final Pattern PATTERN = Pattern.compile("^\\s*f2\\(\"([^\"]*)\"\\)\\s*$"); // ^\s*f2\("([^"]*)"\)\s*$
    }
    record HelpCommand() implements Command {
        public static final Pattern PATTERN = Pattern.compile("^help$");
    }
    record ExitCommand() implements Command {
        public static final Pattern PATTERN = Pattern.compile("^exit$");
    }
    record UnknownCommand(String errorMessage) implements Command { }
}

package io.beyonnex.parser;


import io.beyonnex.parser.Command.*;

import java.util.regex.Pattern;

public class CommandParser {
    public static final String ERROR_MESSAGE = """
            Wrong input. Input should look like this: f1("abc", "cba"), f2("abc"), help or exit""";

    private static final Pattern HELP_PATTERN = Pattern.compile("^help$");
    private static final Pattern EXIT_PATTERN = Pattern.compile("^exit$");
    private static final Pattern F1_PATTERN = Pattern.compile("^\\s*f1\\(\"([^\"]*)\"\\s*,\\s*\"([^\"]*)\"\\)\\s*$"); // // ^\s*f1\("([^"]*)"\s*,\s*"([^"]*)"\)\s*$
    private static final Pattern F2_PATTERN = Pattern.compile("^\\s*f2\\(\"([^\"]*)\"\\)\\s*$"); // ^\s*f2\("([^"]*)"\)\s*$


    public static Command parse(String command) {
        var f1Matcher = F1_PATTERN.matcher(command);
        var f2Matcher = F2_PATTERN.matcher(command);

        if (f1Matcher.find())
            return new F1Command(f1Matcher.group(1), f1Matcher.group(2));

        if (f2Matcher.find())
            return new F2Command(f2Matcher.group(1));

        if (HELP_PATTERN.matcher(command).find())
            return new HelpCommand();

        if (EXIT_PATTERN.matcher(command).find()) {
            return new ExitCommand();
        }

        return new UnknownCommand(ERROR_MESSAGE);
    }
}

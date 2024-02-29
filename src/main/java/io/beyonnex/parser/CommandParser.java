package io.beyonnex.parser;


import io.beyonnex.parser.Command.*;

public class CommandParser {
    public static final String ERROR_MESSAGE = """
            Wrong input. Input should look like this: f1("abc", "cba"), f2("abc"), help or exit""";


    public static Command parse(String command) {
        var f1Matcher = F1Command.PATTERN.matcher(command);
        var f2Matcher = F2Command.PATTERN.matcher(command);

        if (f1Matcher.find())
            return new F1Command(f1Matcher.group(1), f1Matcher.group(2));

        if (f2Matcher.find())
            return new F2Command(f2Matcher.group(1));

        if (HelpCommand.PATTERN.matcher(command).find())
            return new HelpCommand();

        if (ExitCommand.PATTERN.matcher(command).find())
            return new ExitCommand();

        return new UnknownCommand(ERROR_MESSAGE);
    }
}

package beyonnex.io;

import io.beyonnex.App;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void shouldPrintHelpCommands() {
        setInputCommands("help", "exit");

        App.main();

        assertThat(outContent.toString()).isEqualTo("""
                Welcome to Beyonnex interactive anagram checking tool!
                Enter a command (type 'exit' to quit or 'help' for list of commands): List of available commands:
                1. f1("A", "B") - Checks if strings A and B are anagrams
                2. f2("A") - Finds all anagrams of string A that were previously entered through f1
                3. exit - Quit the application
                Enter a command (type 'exit' to quit or 'help' for list of commands): Exiting the application. Goodbye!
                """);
    }

    @Test
    public void shouldInvokeF1InConsole() {
        setInputCommands("""
                f1("abc", "cba")
                exit
                """);

        App.main();

        assertThat(outContent.toString()).isEqualTo("""
                Welcome to Beyonnex interactive anagram checking tool!
                Enter a command (type 'exit' to quit or 'help' for list of commands): true
                Enter a command (type 'exit' to quit or 'help' for list of commands): Exiting the application. Goodbye!
                """);
    }

    @Test
    public void shouldInvokeMultipleF1InConsole() {
        setInputCommands("""
                f1("abc", "cba")
                f1("abc", "bca")
                f1("bca", "cab")
                f1("bca", "xxx")
                exit
                """);

        App.main();

        assertThat(outContent.toString()).isEqualTo("""
                Welcome to Beyonnex interactive anagram checking tool!
                Enter a command (type 'exit' to quit or 'help' for list of commands): true
                Enter a command (type 'exit' to quit or 'help' for list of commands): true
                Enter a command (type 'exit' to quit or 'help' for list of commands): true
                Enter a command (type 'exit' to quit or 'help' for list of commands): false
                Enter a command (type 'exit' to quit or 'help' for list of commands): Exiting the application. Goodbye!
                """);
    }

    @Test
    public void shouldInvokeF1AndF2InConsole() {
        setInputCommands("""
                f1("abc", "cba")
                f1("abc", "bca")
                f1("bca", "cab")
                f1("bca", "xxx")
                f2("abc")
                f2("xxx")
                exit
                """);

        App.main();

        assertThat(outContent.toString()).isEqualTo("""
                Welcome to Beyonnex interactive anagram checking tool!
                Enter a command (type 'exit' to quit or 'help' for list of commands): true
                Enter a command (type 'exit' to quit or 'help' for list of commands): true
                Enter a command (type 'exit' to quit or 'help' for list of commands): true
                Enter a command (type 'exit' to quit or 'help' for list of commands): false
                Enter a command (type 'exit' to quit or 'help' for list of commands): [bca, cba, cab]
                Enter a command (type 'exit' to quit or 'help' for list of commands): []
                Enter a command (type 'exit' to quit or 'help' for list of commands): Exiting the application. Goodbye!
                """);
    }

    @Test
    public void shouldHandleUnknownInput() {
        setInputCommands("xyz", "exit");

        App.main();

        assertThat(outContent.toString()).isEqualTo("""
                Welcome to Beyonnex interactive anagram checking tool!
                Enter a command (type 'exit' to quit or 'help' for list of commands): Wrong input. Input should look like this: f1("abc", "cba"), f2("abc"), help or exit
                Enter a command (type 'exit' to quit or 'help' for list of commands): Exiting the application. Goodbye!
                """);
    }

    @Test
    public void shouldHandleEmptyF1Command() {
        setInputCommands("f1", "exit");

        App.main();

        assertThat(outContent.toString()).isEqualTo("""
                Welcome to Beyonnex interactive anagram checking tool!
                Enter a command (type 'exit' to quit or 'help' for list of commands): Wrong input. Input should look like this: f1("abc", "cba"), f2("abc"), help or exit
                Enter a command (type 'exit' to quit or 'help' for list of commands): Exiting the application. Goodbye!
                """);
    }

    @Test
    public void shouldHandleEmptyF2Command() {
        setInputCommands("f2", "exit");

        App.main();

        assertThat(outContent.toString()).isEqualTo("""
                Welcome to Beyonnex interactive anagram checking tool!
                Enter a command (type 'exit' to quit or 'help' for list of commands): Wrong input. Input should look like this: f1("abc", "cba"), f2("abc"), help or exit
                Enter a command (type 'exit' to quit or 'help' for list of commands): Exiting the application. Goodbye!
                """);
    }

    @Test
    public void shouldExitProgram() {
        setInputCommands("exit");

        App.main();

        assertThat(outContent.toString()).isEqualTo("""
                Welcome to Beyonnex interactive anagram checking tool!
                Enter a command (type 'exit' to quit or 'help' for list of commands): Exiting the application. Goodbye!
                """);
    }

    private void setInputCommands(String... commands) {
        System.setIn(new ByteArrayInputStream(String.join("\n", commands).getBytes()));
    }
}

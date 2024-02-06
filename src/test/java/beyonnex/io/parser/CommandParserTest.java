package beyonnex.io.parser;

import io.beyonnex.parser.Command.F1Command;
import io.beyonnex.parser.Command.F2Command;
import io.beyonnex.parser.Command.UnknownCommand;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.beyonnex.parser.CommandParser.ERROR_MESSAGE;
import static io.beyonnex.parser.CommandParser.parse;
import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserTest {
    private static Stream<Arguments> anagramExamples() {
        return Stream.of(
                Arguments.of("coronavirus",  "carnivorous"),
                Arguments.of("New York Times",  "monkeys write"),
                Arguments.of("She Sells Sanctuary",  "Satan; cruel, less shy"),
                Arguments.of("McDonald's restaurants",  "Uncle Sam's standard rot is not")
        );
    }

    @ParameterizedTest
    @MethodSource("anagramExamples")
    public void shouldParseAndExecuteF1(String firstText, String secondText) {
        var inputCommand = String.format("""
                f1("%s", "%s")""", firstText, secondText);

        assertThat(parse(inputCommand)).isEqualTo(new F1Command(firstText, secondText));
    }

    @ParameterizedTest
    @MethodSource("anagramExamples")
    public void shouldParseAndExecuteF2(String firstText) {
        var inputCommand = String.format("""
                f2("%s")""", firstText);

        assertThat(parse(inputCommand)).isEqualTo(new F2Command(firstText));
    }

    @ParameterizedTest
    @MethodSource("anagramExamples")
    public void shouldFailIfWrongFunctionInvoked(String firstText, String secondText) {
        var inputCommand = String.format("""
                f3("%s", "%s")""", firstText, secondText);

        assertThat(parse(inputCommand)).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
    }

    @ParameterizedTest
    @MethodSource("anagramExamples")
    public void shouldFailIfParameterInF1Missing(String firstText) {
        var inputCommand = String.format("""
                f1("%s")""", firstText);

        assertThat(parse(inputCommand)).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
    }

    @ParameterizedTest
    @MethodSource("anagramExamples")
    public void shouldFailIfF1CalledWithTooManyParameters(String firstText, String secondText) {
        var inputCommand = String.format("""
                f1("%s", "%s", "%s")""", firstText, secondText, firstText);

        assertThat(parse(inputCommand)).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
    }

    @ParameterizedTest
    @MethodSource("anagramExamples")
    public void shouldFailIfF2CalledWithTooManyParameters(String firstText, String secondText) {
        var inputCommand = String.format("""
                f2("%s", "%s")""", firstText, secondText);

        assertThat(parse(inputCommand)).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
    }

    @Test
    public void shouldFailIfF1CalledWithoutParameters() {
        assertThat(parse("f1")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
        assertThat(parse("f1(")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
        assertThat(parse("f1()")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
    }

    @Test
    public void shouldFailIfF2CalledWithoutParameters() {
        assertThat(parse("f2")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
        assertThat(parse("f2(")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
        assertThat(parse("f2()")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
    }

    @Test
    public void shouldFailOnMisspelledHelpCommand() {
        assertThat(parse("hel")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
        assertThat(parse("helpp")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
        assertThat(parse("elp")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
    }

    @Test
    public void shouldFailOnMisspelledExitCommand() {
        assertThat(parse("exi")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
        assertThat(parse("exitt")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
        assertThat(parse("eexit")).isEqualTo(new UnknownCommand(ERROR_MESSAGE));
    }
}
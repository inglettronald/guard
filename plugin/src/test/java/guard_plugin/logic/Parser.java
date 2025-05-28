package guard_plugin.logic;

import guard_plugin.state.Result;
import guard_plugin.state.Test;

import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final Pattern TEST_PATTERN = Pattern.compile(
            "^> BEFORE\\R(?<before>.+)\\R> AFTER\\R(?<after>.+)$",
            Pattern.DOTALL | Pattern.MULTILINE
    );

    private static final String ERROR_MSG = "Invalid test format, please refer to the test pattern in Parser, and other example usages";

    public static void parse(Test test) throws Exception {
        String str = Files.readString(test.getSource().toPath());
        Matcher matcher = TEST_PATTERN.matcher(str);
        if (!matcher.matches()) {
            test.result = new Result(Result.Value.FAIL_PARSE, ERROR_MSG);
            Logger.error(test.getSource().getName() + " : " + ERROR_MSG);
            return;
        }
        test.before = matcher.group("before").getBytes();
        test.expected = matcher.group("after").getBytes();
    }

}

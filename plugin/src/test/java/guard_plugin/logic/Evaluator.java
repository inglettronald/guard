package guard_plugin.logic;

import guard_plugin.state.Result;
import guard_plugin.state.Test;

import java.nio.charset.StandardCharsets;

public class Evaluator {

    public static void process(Test test) {
        if (test.isFinished()) {
            return;
        }

        for (int i = 0; i < test.expected.length; i++) {
            // Only throw this error if we've stepped through the rest of the characters. This way, we try to give more
            // accurate difference logging
            if (!validIndex(test, i)) {
                return; // game over
            }

            byte a = test.after[i];
            byte b = test.expected[i];
            if (a != b) {
                test.result = new Result(
                        Result.Value.FAIL_COMPARE,
                        generateErrorMessage(test, i)
                );
                return;
            }
        }

        test.result = new Result(Result.Value.PASS, "");
    }

    private static boolean validIndex(Test test, int i) {
        if (i < test.after.length && i < test.expected.length) {
            return true;
        } else if (i < test.after.length) {
            test.result = new Result(
                    Result.Value.FAIL_COMPARE,
                    "Actual compiled output larger than expected, but common character indices match."
            );
        } else if (i < test.expected.length) {
            test.result = new Result(
                    Result.Value.FAIL_COMPARE,
                    "Actual compiled output smaller than expected, but common character indices match."
            );
        } else {
            test.result = new Result(
                    Result.Value.FAIL_COMPARE,
                    "If you have reached this error message, something has gone horribly wrong."
            );
        }
        return false;
    }

    private static String generateErrorMessage(Test test, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("Difference between compiled and expected output found!\n");
        sb.append("Difference starts at index ").append(i).append(": \n\n");
        sb.append("Expected output v\n");
        String expected = new String(test.expected, StandardCharsets.UTF_8);
        sb.append(getLineOf(expected, i)).append("\n");
        int dist = distanceToPreviousLine(expected, i);
        sb.append(" ".repeat(Math.max(0, dist + 1))).append("|\n");
        sb.append(getLineOf(new String(test.after, StandardCharsets.UTF_8), i)).append("\n");
        sb.append("Actual output ^\n");
        return sb.toString();
    }

    private static String getLineOf(String str, int i) {
        int start = i - distanceToPreviousLine(str, i) + 1;
        int end = i + distanceToNextLine(str, i);
        return str.substring(start, end);
    }

    private static int distanceToPreviousLine(String str, int i) {
        char c = str.charAt(i);
        int innerIndex = i;
        while (c != '\n') {
            innerIndex--;
            if (innerIndex < 0) {
                break;
            }
            c = str.charAt(innerIndex);
        }
        return innerIndex;
    }

    private static int distanceToNextLine(String str, int i) {
        int innerIndex = i;
        char c = str.charAt(innerIndex);
        while (c != '\n') {
            innerIndex++;
            if (innerIndex > str.length()) {
                break;
            }
            c = str.charAt(innerIndex);
        }
        return innerIndex;
    }

}

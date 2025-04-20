package guard_plugin.state;

/**
 * @param info null if pass
 */
public record Result(
        guard_plugin.state.Result.Value value,
        byte[] info
) {

    public enum Value {
        PASS,
        FAIL_PARSE, // Can't read the test file (no before/after)
        FAIL_COMPILE, // Can't compile, probably miswritten test
        FAIL_COMPARE // Compiled output is different
    }

}

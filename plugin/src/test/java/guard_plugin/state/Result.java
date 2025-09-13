package guard_plugin.state;

/**
 * @param info null if pass
 */
public record Result(
        guard_plugin.state.Result.Value value,
        String info
) {

    public enum Value {
        PASS,
        FAIL_COMPILE, // Can't compile, probably miswritten test
        FAIL_COMPARE // Compiled output is different
    }

}

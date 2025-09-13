package guard_plugin.state;

import java.util.Arrays;

public record CompiledOutputs (
        byte[] before,
        byte[] after
) {
    private boolean passes() {
        return Arrays.equals(this.before, this.after);
    }
}

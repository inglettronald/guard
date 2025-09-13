package guard_plugin.state;

import java.io.File;

public class Test {

    private final String name;

    private final File before;
    private final File after;

    private CompiledOutputs compiledOutputs;

    public Result result;

    public Test(String name, File before, File after) {
        this.name = name;
        this.before = before;
        this.after = after;
    }

    public File getBefore() {
        return this.before;
    }

    public File getAfter() {
        return this.after;
    }

    public String getName() {
        return this.name;
    }

    public CompiledOutputs getCompiledOutputs() {
        return this.compiledOutputs;
    }

    public boolean isFinished() {
        return this.result != null;
    }

}

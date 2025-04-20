package guard_plugin.state;

import java.io.File;

public class Test {

    private final File source;
    private final Type type;

    public byte[] before;
    public byte[] after;
    public byte[] expected;

    public Result result;

    public Test(File source, Type type) {
        this.source = source;
        this.type = type;
    }

    public File getSource() {
        return source;
    }

    public Type getType() {
        return type;
    }

    public boolean isFinished() {
        return this.result != null;
    }

    public enum Type {
        CLASS, METHOD;

        public static Type fromString(String in) {
            for (Type value : Type.values()) {
                if (value.name().toLowerCase().equals(in)) {
                    return value;
                }
            }
            return null;
        }
    }
}

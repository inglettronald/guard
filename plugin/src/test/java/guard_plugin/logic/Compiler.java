package guard_plugin.logic;

import com.sun.tools.javac.Main;
import guard_plugin.Testing;
import guard_plugin.state.Result;
import guard_plugin.state.Test;

import java.io.File;

public class Compiler {

    public static void compile(Test test) {
        if (test.isFinished()) {
            return;
        }
        try {
            compileInternal(test);
        } catch (Exception e) {
            test.result = new Result(
                    Result.Value.FAIL_COMPILE,
                    e.getLocalizedMessage()
            );
        }
    }

    private static void makeOutputDir() {

    }

    private static void compileInternal(Test test) throws Exception {
        Main.compile(new String[] {
                "-d", Testing.COMPILATION_OUTPUT_DIR.getAbsolutePath(),
                "-cp", new File("").getAbsolutePath(),
                test.getBefore().getAbsolutePath()
        });
        Main.compile(new String[] {
                "-d", Testing.COMPILATION_OUTPUT_DIR.getAbsolutePath(),
                "-cp", new File("").getAbsolutePath(),
                test.getAfter().getAbsolutePath()
        });
    }

}
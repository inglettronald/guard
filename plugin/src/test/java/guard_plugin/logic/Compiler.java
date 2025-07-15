package guard_plugin.logic;

import com.sun.tools.javac.api.JavacTool;
import guard_plugin.state.Result;
import guard_plugin.state.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Compiler {

    public static void compile(Test test) {
        if (test.isFinished()) {
            return;
        }
        try {
            // TODO - fix this
            JavacTool compiler = JavacTool.create();
            ByteArrayInputStream in = new ByteArrayInputStream(test.before);
            ByteArrayOutputStream out = new ByteArrayOutputStream(test.expected.length);
            ByteArrayOutputStream err = new ByteArrayOutputStream(0);
            compiler.run(in, out, err);
            if (err.size() == 0) {
                test.after = out.toByteArray();
            } else {
                test.after = err.toByteArray();
            }
        } catch (Exception e) {
            test.result = new Result(
                    Result.Value.FAIL_COMPILE,
                    e.getLocalizedMessage()
            );
        }
    }

}

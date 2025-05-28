package guard_plugin.logic;

import com.sun.tools.javac.api.JavacTool;
import guard_plugin.state.Result;
import guard_plugin.state.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Compiler {

    public static void compile(Test test) {
        if (test.isFinished()) {
            return;
        }
        try {
            // TODO - fix this
            JavacTool compiler = JavacTool.create();
            InputStream in = new ByteArrayInputStream(test.before);
            OutputStream out = new ByteArrayOutputStream(test.expected.length);
            OutputStream err = new ByteArrayOutputStream(0);
            compiler.run(in, out, err, "");
        } catch (Exception e) {
            test.result = new Result(
                    Result.Value.FAIL_COMPILE,
                    e.getLocalizedMessage()
            );
        }
    }

}

package guard_plugin.logic;

import guard_plugin.state.Result;
import guard_plugin.state.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileWriter {

    public static void writeResults(Test test) throws IOException {
        File out = getOutputFile(test);
        if (out == null) {
            return;
        }

        java.io.FileWriter writer = new java.io.FileWriter(out);
        writer.write("Test results for " + test.getSource().getName() + "\n");
        if (test.result.value() == Result.Value.PASS) {
            writer.write("Tests Passed!\n");
        } else {
            writer.write("Tests Failed at step: ");
            String stage = switch (test.result.value()) {
                case FAIL_PARSE -> "Parse";
                case FAIL_COMPARE -> "Compare";
                case FAIL_COMPILE -> "Compile";
                default -> "null";
            };
            writer.write(stage + "\n");
            writer.write(test.result.info() + "\n");
        }
        writer.write("========\n\n");

        writer.write("Extra Info (Compiled Before/After vs Expected)");

        if (test.before != null) {
            String before = new String(test.before, StandardCharsets.UTF_8);
            writer.write("BEFORE >\n" + before + "\n\n");
        }
        if (test.expected != null) {
            String expected = new String(test.expected, StandardCharsets.UTF_8);
            writer.write("EXPECTED >\n" + expected + "\n\n");
        }
        if (test.after != null) {
            String after = new String(test.after, StandardCharsets.UTF_8);
            writer.write("AFTER >\n" + after + "\n\n");
        }

        writer.close();
    }

    private static File getOutputFile(Test test) {
        File outputDir = new File("test_output/" + test.getType().name().toLowerCase());
        if (!outputDir.exists()) {
            try {
                // noinspection ResultOfMethodCallIgnored
                outputDir.mkdirs();
            } catch (SecurityException e) {
                Logger.error("Permission denied to create output directory: " + outputDir.getPath());
                return null;
            }
        }
        File source = test.getSource();
        return new File(
                outputDir.getPath(),
                source.getName().substring(0, source.getName().length() - 4) + extension(test)
        );
    }

    private static String extension(Test test) {
        return test.result.value() == Result.Value.PASS
                ? "pass"
                : "fail";
    }
}

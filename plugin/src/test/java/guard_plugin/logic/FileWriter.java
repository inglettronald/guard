package guard_plugin.logic;

import guard_plugin.state.Test;

import java.io.File;

public class FileWriter {

    public static void writeResults(Test test) {

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
                source.getName().substring(0, source.getName().length() - 4) + "incomplete" // kekw
        );
    }
}

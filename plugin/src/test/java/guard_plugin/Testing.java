package guard_plugin;

import guard_plugin.state.Test;
import guard_plugin.logic.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Testing {

    public static void main(String[] args) {
        Logger.log("Gathering test objects...");
        Collection<Test> tests = getTests();

        Logger.log("Parsing test data...");
        for (Test test : tests) {
            try {
                Parser.parse(test);
            } catch (Exception e) {
                Logger.error("Error parsing test: " + test.getSource().getName());
                Logger.printException(e);
                return;
            }
        }

        Logger.log("Running Compilation...");
        for (Test test : tests) {
            Compiler.compile(test);
        }

        Logger.log("Processing Results...");
        for (Test test : tests) {
            Evaluator.process(test);
        }

        Logger.log("Writing output...");
        for (Test test : tests) {
            try {
                FileWriter.writeResults(test);
            } catch (Exception e) {
                Logger.error("Error writing result for test: " + test.getSource().getName());
                Logger.printException(e);
                return;
            }
        }
    }

    /**
     * This method will attempt to gather all the tests it can find, and provide appropriate logging
     */
    private static Collection<Test> getTests() {
        Collection<Test> ret = new ArrayList<>();
        File mainFile = new File("plugin/src/test/tests");
        File[] directoryContents = mainFile.listFiles();
        if (directoryContents == null) {
            Logger.error("No test files in the resource directory");
            return ret;
        }
        for (File file : directoryContents) {
            if (!file.isDirectory()) {
                Logger.error(
                        "Skipping unrecognized file " + file.getName() + ", probably outside of intended scope."
                );
                continue;
            }
            Test.Type type = Test.Type.fromString(file.getName());
            if (type == null) {
                Logger.error(
                        "Unrecognized test subdirectory: " + file.getName() + ", continuing..."
                );
                continue;
            }
            File[] tests = file.listFiles();
            if (tests == null) {
                Logger.error("No " + file.getName() + " tests present, where did they go?!");
                continue;
            }
            for (File test : tests) {
                if (!test.isFile()) {
                    continue;
                }
                if (!test.getName().endsWith(".test")) {
                    continue;
                }
                ret.add(new Test(test, type));
            }
        }
        return ret;
    }

}
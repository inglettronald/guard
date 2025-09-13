package guard_plugin;

import com.sun.tools.javac.util.Log;
import guard_plugin.state.Test;
import guard_plugin.logic.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Testing {

    public static final File COMPILATION_OUTPUT_DIR = new File("compilation_output");


    public static void main(String[] args) {
        Logger.log("Gathering test objects...");
        Collection<Test> tests = getTests();

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
                Logger.error("Error writing result for test: " + test.getName());
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
            String name = file.getName();

            if (!file.isDirectory()) {
                Logger.error(
                        "Skipping unrecognized file " + name + ", probably outside of intended scope."
                );
                continue;
            }

            File[] tests = file.listFiles();
            if (tests == null || tests.length == 0) {
                Logger.error("No test files in directory " + name);
                continue;
            }

            File before = null;
            File after = null;
            for (File innerFile : tests) {
                if (!innerFile.isDirectory()) {
                    // todo logging
                    continue;
                }
                String innerName = innerFile.getName();
                File fileToCompile = new File(innerFile.getAbsolutePath() + "/" + name + ".java");
                if (!fileToCompile.exists()) {
                    // todo logging
                    continue;
                }
                if (innerName.equals("Before")) {
                    before = fileToCompile;
                } else if (innerName.equals("After")) {
                    after = fileToCompile;
                }
            }
            if (before == null || after == null) {
                Logger.error(
                        "Test " + name + " is missing a source file. Please provide a Before.java and After.java"
                );
            }
            ret.add(new Test(name, before, after));
        }
        return ret;
    }

}
package com.dulkir.guard.plugin;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.tools.javac.api.BasicJavacTask;

public class GuardImpl implements Plugin {

    @Override
    public String getName() {
        return "Guard";
    }

    /**
     * This is the home for registering listeners
     * @param task The compilation task that has just been started
     * @param args Arguments, if any, for the plug-in
     */
    @Override
    public void init(JavacTask task, String... args) {
        task.addTaskListener(new ExampleTaskListener((BasicJavacTask) task));
    }

    // TODO: replace this with something "proper"
    @Override
    public boolean autoStart() {
        return true;
    }
}
package com.dulkir.guard_plugin;

import com.dulkir.guard_plugin.annotation_handler.GuardNullBasic;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.tools.javac.api.JavacTaskImpl;

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
        if (!(task instanceof JavacTaskImpl impl)) {
            System.err.println("[Guard]: Unexpected task recieved by GuardImpl.init, plugin will not apply.");
            return;
        }
        System.out.println("Adding listeners");
        // task.addTaskListener(new ExampleTaskListener(impl));
        task.addTaskListener(new GuardNullBasic(impl));
    }

    // TODO: replace this with something "proper"
    @Override
    public boolean autoStart() {
        return true;
    }
}

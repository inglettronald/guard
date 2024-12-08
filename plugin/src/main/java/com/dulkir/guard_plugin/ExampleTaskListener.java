package com.dulkir.guard_plugin;

import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.JavacTaskImpl;

public class ExampleTaskListener implements TaskListener {

    private final JavacTaskImpl task;

    ExampleTaskListener(JavacTaskImpl task) {
        this.task = task;
    }

    @Override
    public void finished(TaskEvent e) {
        System.out.println("printing from the plugin processor");
        System.out.println("e = " + e);
        System.out.println("task = " + this.task);
        // Utils utils = Utils.instance(this.task.getContext());
        // utils.reportError("this is an example error.");
    }

}

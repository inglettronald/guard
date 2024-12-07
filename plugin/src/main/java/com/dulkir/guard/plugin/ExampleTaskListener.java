package com.dulkir.guard.plugin;

import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.BasicJavacTask;

public class ExampleTaskListener implements TaskListener {

    private final BasicJavacTask task;

    ExampleTaskListener(BasicJavacTask task) {
        this.task = task;
    }

    @Override
    public void finished(TaskEvent e) {
        System.out.println("printing from the plugin processor");
        Utils utils = Utils.instance(this.task.getContext());
        utils.reportError("this is an example error.");
    }

}

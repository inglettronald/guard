package com.dulkir.guard_plugin.annotation_handler;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;


public class GuardNullBasic implements TaskListener {

    private final JavacTaskImpl taskImpl;

    public GuardNullBasic(JavacTaskImpl taskImpl) {
        this.taskImpl = taskImpl;
    }

    @Override
    public void finished(TaskEvent e) {
        if (e.getKind() != TaskEvent.Kind.ENTER) {
            return;
        }
        CompilationUnitTree unitInterface = e.getCompilationUnit();
        if (!(unitInterface instanceof JCCompilationUnit unit)) {
            return;
        }
        System.out.println(unit.defs);
        // TODO: check out the TreeMaker and modify the AST to do what I want
    }
}

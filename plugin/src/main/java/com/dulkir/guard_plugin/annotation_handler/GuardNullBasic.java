package com.dulkir.guard_plugin.annotation_handler;

import com.dulkir.guard_plugin.JcIfFactory;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;

import java.util.List;


public class GuardNullBasic implements TaskListener {

    private final JavacTaskImpl taskImpl;

    private JcIfFactory jcIfFactory;

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

        // Loop over all methods for processing
        for (JCTree tree : unit.defs) {
            if (!(tree instanceof JCTree.JCClassDecl classDecl)) {
                continue;
            }
            for (JCTree tree1 : classDecl.defs) {
                if (!(tree1 instanceof JCTree.JCMethodDecl methodDecl)) {
                    continue;
                }
                evalMethod(methodDecl);
            }
        }
    }

    /**
     * Look through a method for @Guard.Null annotations. if they are found, we want to create
     * a guardian clause after that statement. If it's a nonsensical annotation, we want to throw an error.
     */
    private void evalMethod(JCTree.JCMethodDecl methodDecl) {
        List<JCTree.JCStatement> statements = methodDecl.getBody().stats;
        for (JCTree.JCStatement statement : statements) {
            if (statement instanceof JCTree.JCVariableDecl variableDecl) {
                if (variableDecl.mods == null
                        || variableDecl.mods.annotations == null
                        || variableDecl.mods.annotations.isEmpty()) {
                    continue;
                }
                if (!hasGuardNullAnnotation(variableDecl.mods.annotations)) {
                    continue;
                }
                System.out.println("found a @Guard.Null annotation!");
                // TODO: utilize the TreeMaker to add a null check throught the JcIfFactory
            }
            // TODO: handle JCExpressionStatements
        }
    }

    private boolean hasGuardNullAnnotation(List<JCTree.JCAnnotation> annotations) {
        for (JCTree.JCAnnotation annotation : annotations) {
            if (isGuardNullAnnotation(annotation)) {
                return true;
            }
        }
        return false;
    }

    private boolean isGuardNullAnnotation(JCTree.JCAnnotation annotation) {
        if (!(annotation.annotationType instanceof JCTree.JCFieldAccess fieldAccess)) {
            return false;
        }
        return fieldAccess.toString().equals("Guard.Null"); // slow? idk lol
    }
}

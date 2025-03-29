package com.dulkir.guard_plugin.annotation_handler;

import com.dulkir.guard_plugin.JcIfFactory;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.List;

// TODO: there's going to be a number of these type of annotation parsers running. I should
//  make a utility to run ahead and get the
public class GuardNullBasic implements TaskListener {

    private final JavacTaskImpl taskImpl;
    private final JcIfFactory jcIfFactory;

    public GuardNullBasic(JavacTaskImpl taskImpl) {
        this.taskImpl = taskImpl;
        this.jcIfFactory = new JcIfFactory(taskImpl.getContext());
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

        System.out.println("Before:");
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

        System.out.println("After:");
        System.out.println(unit.defs);
    }

    /**
     * Look through a method for @Guard.Null annotations. if they are found, we want to create
     * a guardian clause after that statement. If it's a nonsensical annotation, we want to throw an error.
     */
    private void evalMethod(JCTree.JCMethodDecl methodDecl) {
        List<JCTree.JCStatement> statements = methodDecl.getBody().stats;
        for (List<JCTree.JCStatement> pointer = statements; pointer != null; pointer = pointer.tail) {
            JCTree.JCStatement statement = pointer.head;
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
                JCTree.JCIf ifStatement = jcIfFactory.guardAgainstNull(variableDecl.name);
                pointer.setTail(
                        pointer.tail == null
                                ? List.of(ifStatement)
                                : pointer.tail.prepend(ifStatement)
                );
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

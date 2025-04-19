package guard_plugin.util;

import com.sun.tools.javac.tree.JCTree;

public class MethodTest extends Test<JCTree.JCMethodDecl> {

    public MethodTest(JCTree.JCMethodDecl before, JCTree.JCMethodDecl after) {
        super(before, after);
    }

    @Override
    public boolean apply() {
        return false; // TODO
    }

}

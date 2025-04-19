package guard_plugin.util;

import com.sun.tools.javac.tree.JCTree;

public class ClassTest extends Test<JCTree.JCClassDecl> {

    public ClassTest(JCTree.JCClassDecl before, JCTree.JCClassDecl after) {
        super(before, after);
    }

    @Override
    public boolean apply() {
        return false; // TODO
    }

}

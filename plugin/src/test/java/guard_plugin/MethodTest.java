package guard_plugin;

import com.sun.tools.javac.tree.JCTree;
import guard_plugin.util.Test;

public class MethodTest extends Test<JCTree.JCMethodDecl> {

    public MethodTest(JCTree.JCMethodDecl before, JCTree.JCMethodDecl after) {
        super(before, after);
    }

    @Override
    public void apply() {

    }

}

package guard_plugin;

import com.sun.tools.javac.tree.JCTree;
import guard_plugin.util.Test;

public class ClassTest extends Test<JCTree.JCClassDecl> {

    public ClassTest(JCTree.JCClassDecl before, JCTree.JCClassDecl after) {
        super(before, after);
    }

    @Override
    public void apply() {

    }

}

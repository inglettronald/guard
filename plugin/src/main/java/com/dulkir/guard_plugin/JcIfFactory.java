package com.dulkir.guard_plugin;

import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Name;

public class JcIfFactory {

    private final TreeMaker maker;
    private final Symtab symtab;

    public JcIfFactory(Context context) {
            maker = TreeMaker.instance(context);
            symtab = Symtab.instance(context);
    }

    // TODO: this only works if you're returning void!
    public JCTree.JCIf guardAgainstNull(Name variableName) {
        JCTree.JCReturn returnNull = maker.Return(null);
        JCTree.JCBinary condition = maker.Binary(
                JCTree.Tag.EQ,
                maker.Ident(variableName),
                maker.Literal(TypeTag.BOT, null).setType(symtab.botType)
        );
        return maker.If(condition, returnNull, null);
    }
}

package com.dulkir.guard_plugin;

import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Name;

public class JcIfFactory {

    private final TreeMaker maker;
    private final Symtab symtab;
    private final Utils utils;

    public JcIfFactory(Context context) {
        maker = TreeMaker.instance(context);
        symtab = Symtab.instance(context);
        utils = Utils.instance(context);
    }

    public JCTree.JCIf guardAgainstNull(Name variableName) {
        return guardAgainstNull(variableName, null);
    }

    public JCTree.JCIf guardAgainstNull(Name variableName, TypeTag retType) {
        JCTree.JCReturn returnNull = getReturnExpression(retType);
        JCTree.JCBinary condition = maker.Binary(
                JCTree.Tag.EQ,
                maker.Ident(variableName),
                maker.Literal(TypeTag.BOT, null).setType(symtab.botType)
        );
        return maker.If(condition, returnNull, null);
    }

    private JCTree.JCReturn getReturnExpression(TypeTag retType) {
        if (retType == null) {
            return maker.Return(null);
        }

        Object retVal = Utils.getDefault(retType);
        Type type = utils.getType(retType);
        if (retVal == null) {
            return maker.Return(null);
        }
        return (JCTree.JCReturn) maker.Return(maker.Literal(retVal)).setType(type);
    }
}

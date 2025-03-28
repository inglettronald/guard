package com.dulkir.guard_plugin;

import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.JCDiagnostic;
import com.sun.tools.javac.util.JavacMessages;
import com.sun.tools.javac.util.Log;

import java.util.ListResourceBundle;

public class Utils {

    private static final Context.Key<Utils> KEY = new Context.Key<>();

    private final Log log;
    private final Symtab symtab;

    public static Utils instance(Context context) {
        Utils utils = context.get(KEY);
        if (utils == null) {
            utils = new Utils(context);
        }
        return utils;
    }

    private Utils(Context context) {
        context.put(KEY, this);

        // This allows us to dodge having to worry about translations right now while we are getting things set up.
        JavacMessages.instance(context).add(locale -> new ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][] {
                   new Object[] { "compiler.err.guard.generic", "{0}" }
                };
            }
        });

        log = Log.instance(context);
        symtab = Symtab.instance(context);
    }

    public void reportError(String message) {
        log.error("guard.generic", message);
    }

    public void warn(String message) {
        log.warning(new JCDiagnostic.Warning("guard.generic", message));
    }

    public static Object getDefault(TypeTag type) {
        if (type == null) {
            return null;
        }
        return switch (type) {
            case BYTE -> Primitives.DEFAULT_BYTE;
            case SHORT -> Primitives.DEFAULT_SHORT;
            case INT -> Primitives.DEFAULT_INT;
            case LONG -> Primitives.DEFAULT_LONG;
            case FLOAT -> Primitives.DEFAULT_FLOAT;
            case DOUBLE -> Primitives.DEFAULT_DOUBLE;
            case BOOLEAN -> Primitives.DEFAULT_BOOLEAN;
            case CHAR -> Primitives.DEFAULT_CHAR;
            default -> null;
        };
    }

    public Type getType(TypeTag type) {
        return switch (type) {
            case BYTE -> symtab.byteType;
            case SHORT -> symtab.shortType;
            case INT -> symtab.intType;
            case LONG -> symtab.longType;
            case FLOAT -> symtab.floatType;
            case DOUBLE -> symtab.doubleType;
            case BOOLEAN -> symtab.booleanType;
            case CHAR -> symtab.charType;
            default -> symtab.objectType;
        };
    }
}

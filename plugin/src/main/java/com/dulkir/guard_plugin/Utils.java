package com.dulkir.guard_plugin;

import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.JavacMessages;
import com.sun.tools.javac.util.Log;

import java.util.ListResourceBundle;

public class Utils {

    private static final Context.Key<Utils> KEY = new Context.Key<>();

    private final Log log;

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
    }

    public void reportError(String message) {
        log.error("guard.generic", message);
    }

    public void log(String message) {
        // TODO
    }

}

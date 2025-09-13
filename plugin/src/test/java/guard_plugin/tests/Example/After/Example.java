package guard_plugin.tests.Example.After;

import com.dulkir.guard.Guard;

public class Example {
    public static void main(String[] args) {
        String foo = null;
        if (foo == null) {
            return;
        }
        System.out.println("post clause");
    }
}
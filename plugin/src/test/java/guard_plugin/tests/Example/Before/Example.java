package guard_plugin.tests.Example.Before;

import com.dulkir.guard.Guard;

public class Example {
    public static void main(String[] args) {
        @Guard.Null String foo = null;
        System.out.println("post clause");
    }
}
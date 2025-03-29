package guard_plugin;

import com.dulkir.guard.Guard;

public class Testing {

    public static void main(String[] args) {
        System.out.println("Inside Testing.main");
        String bar = "six";
        @Guard.Null String foo = null;

        // @Guard.Null int foo2 = 0;

        System.out.println("end");
    }

}
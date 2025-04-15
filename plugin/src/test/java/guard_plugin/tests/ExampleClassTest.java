package guard_plugin.tests;

import com.dulkir.guard.Guard;
import guard_plugin.util.After;
import guard_plugin.util.Before;

public class ExampleClassTest {

    @Before
    public static class B {
        public static void main(String[] args) {
            @Guard.Null String foo = null;
            System.out.println("post clause");
        }
    }

    @After
    public static class A {
        public static void main(String[] args) {
            String foo = null;
            if (foo == null) {
                return;
            }
            System.out.println("post clause");
        }
    }

}

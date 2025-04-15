package guard_plugin.tests;

import com.dulkir.guard.Guard;
import guard_plugin.util.After;
import guard_plugin.util.Before;

public class ExampleMethodTest {

    @Before
    private void before() {
        @Guard.Null String foo = null;
        System.out.println("post clause");
    }

    @After
    private void after() {
        String foo = null;
        if (foo == null) {
            return;
        }
        System.out.println("post clause");
    }

}

package guard_plugin.util;

/**
 * WIP. I've not actually done the research on how I might want to do a test framework, but this seems
 * like an interesting idea. No idea if this will work, need to get toolchain working first.
 */
public interface TestSupplier<T> {

    T before();

    T after();

    default Test<T> get() {
        return new Test.Builder<T>()
                .before(before())
                .after(after())
                .build();
    }

}

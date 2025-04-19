package guard_plugin.util;

import java.util.function.Function;

/**
 * Defines the testing structure for this project. Each test supplies some expected input and output function,
 * and running these tests will generate output files in the out package that will show the before/after plugin
 * application of some method tree. This class also defines custom functions for checking the validity of the
 * operations we perform, and then debugging it appropriately to the console.
 *
 * @author inglettronald 2025
 */
public abstract class Test<T> {

    private final T before;
    private final T after;

    public Test(T before, T after) {
        this.before = before;
        this.after = after;
    }

    public Test(Builder<T> builder) {
        this(builder.before, builder.after);
    }

    /**
     * Returns true if it passes
     */
    public abstract boolean apply();

    // <editor-fold defaultstate="collapsed" desc="builder">
    public static class Builder<T> {

        private T before;
        private T after;
        Function<Test<T>, Boolean> applicationFunc;

        public Builder() {
            // no-op
        }

        public Builder<T> before(T before) {
            this.before = before;
            return this;
        }

        public Builder<T> after(T after) {
            this.after = after;
            return this;
        }

        public Builder<T> applicationFunc(Function<Test<T>, Boolean> applicationFunc) {
            this.applicationFunc = applicationFunc;
            return this;
        }

        public Test<T> build() {
            if (this.before == null || this.after == null) {
                throw new IllegalStateException("Test builder parameters should not be null!");
            } else {
                return new Test<>(this) {
                    @Override
                    public boolean apply() {
                        return applicationFunc.apply(this); // This is kinda scuffed lol
                    }
                };
            }
        }
    }
    // </editor-fold>

}

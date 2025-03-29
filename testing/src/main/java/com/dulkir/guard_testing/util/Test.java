package com.dulkir.guard_testing.util;

import java.util.function.Function;

/**
 * Defines the testing structure for this project. Each test supplies some expected input and output function,
 * and running these tests will generate output files in the out package that will show the before/after plugin
 * application of some method tree. This class also defines custom functions for checking the validity of the
 * operations we perform, and then debugging it appropriately to the console.
 *
 * @author inglettronald 2025
 */
public class Test<T, U> {

    private final Function<T, U> before;
    private final Function<T, U> after;

    public Test(Function<T, U> before, Function<T, U> after) {
        this.before = before;
        this.after = after;
    }

    public Test(Builder<T, U> builder) {
        this(builder.before, builder.after);
    }

    public void apply() {
        applyInternal();
    }

    // <editor-fold defaultstate="collapsed" desc="applyInternal">
    private void applyInternal() {
        // TODO
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="builder">
    public static class Builder<T, U> {

        private Function<T, U> before;
        private Function<T, U> after;

        public Builder() {
            // no-op
        }

        public Builder<T, U> before(Function<T, U> before) {
            this.before = before;
            return this;
        }

        public Builder<T, U> after(Function<T, U> after) {
            this.after = after;
            return this;
        }

        public Test<T, U> build() {
            if (this.before == null || this.after == null) {
                throw new IllegalStateException("Test builder parameters should not be null!");
            } else {
                return new Test<>(this);
            }
        }
    }
    // </editor-fold>

}

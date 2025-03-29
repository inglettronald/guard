package com.dulkir.guard_testing;

import com.dulkir.guard.Guard;

public class TestSuite {

    public static void main(String[] args) {
        System.out.println("Inside Example.main");
        String bar = "six";
        @Guard.Null String foo = null;

        // @Guard.Null int foo2 = 0;

        System.out.println("end");
    }

}
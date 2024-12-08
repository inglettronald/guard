package com.dulkir.guard_example;

import com.dulkir.guard.Guard;

public class Example {

    public static void main(String[] args) {
        System.out.println("Inside Example.main");

        @Guard.Null String foo = null;

        System.out.println("We did not early return.");
    }

}
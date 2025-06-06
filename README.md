<h1>Guard</h1>

<p>This is the home of a java plugin I want to work on. Currently, this is still in early concept stages, but I wrote
out this README because I thought it would be fun. The current state of the project is only as far as having the 
toolchain set up (ty @lea89 for the help there!) and writing a basic proof-of-concept annotation parser.</p>

<b>RoadMap:</b>
- [x] Implement a basic `@Guard.Null` annotation for variable declaration that null checks the resolved value of that line.
- [ ] Test framework and toolchain cleanup
- [ ] Expand that system to check each step along the way (see usages, this is psuedo optional chaining)
- [ ] Implement safe returns out of Expression statements with null checks at each step along the way
- [ ] Implement error handling for improper annotation usage
- [ ] See if It's possible to supply a consumer as an annotation argument?!? Would be cool to modify the early exit code.
- [ ] Implement some other types of annotations, such as `Guard.Empty`, `Guard.Exception`, `Guard.False` etc. A lot 
of this is still pretty loose as I'll need to know how much I can abuse the parameters.

<h3>Example Developer Facing Usages</h3>

Variable nullability:
```java
private void foo() {
    @Guard.Null String idString = this.id;
    // ...
}
// Maps to...
private void foo() {
    String com$dulkir$guard$id$0 = this.id;
    if (com$dulkir$guard$id$0 == null) {
        return;
    }
    // ...
}
```

Chaining, and functions (and maybe exit functions?):
```java
private void foo() {
    @Guard.Null(methodHandle = "logwtf") char firstIdChar = this.id.charAt(0);
    // ...
}
// Maps to...
private void foo() {
    String com$dulkir$guard$id$0 = this.id;
    if (com$dulkir$guard$id$0 == null) {
        return this::logWtf;
    }
    char com$dulkir$guard$id$1 = com$dulkir$guard$id$0.charAt(0);
    if (com$dulkir$guard$id$1 == null) {
        return this::logWtf;
    }
    // ...
}
```

Chaining, functions, and emptiness:
```java
private void foo() {
    @Guard.Empty
    @Guard.Null 
    char firstIdChar = this.id.charAt(0);
    // ...
}
// Maps to...
private void foo() {
    String com$dulkir$guard$id$0 = this.id;
    if (com$dulkir$guard$id$0 == null) {
        return;
    }
    if (com$dulkir$guard$id$0.isEmpty()) {
        return;
    }
    char com$dulkir$guard$id$1 = com$dulkir$guard$id$0.charAt(0);
    if (com$dulkir$guard$id$1 == null) {
        return;
    }
    // ...
}
```

And hopefully more! This project will probably take some time, considering I'm new to compiler plugins, and I'm only
able to do this when I have free time outside of work. However, I think it would be a novel (and useful) idea to bring
optional chaining out of kotlin and into java. Considering that projects like `Lombok` have gotten so popular as well,
I feel like there *might* be some people who also would appreciate the boiler-plate reduction that this would bring?

There is probably some valid criticism to this idea in that it's a bit silly to use a verbose language if you don't
want to be verbose. However, I counter this with the super thought out "choice is cool" argument.

## For Developers:
The project is mostly set up to be run through the usage of the tasks provided in the plugin package at the moment.
see `./gradlew buildAndDebug`.

If you're having issues with imports in the test package, you may need to add the following to your `.idea/compiler.xml`:
```
  <component name="CompilerConfiguration">
    <bytecodeTargetLevel target="21" />
  </component>
  <component name="JavacSettings">
    <option name="ADDITIONAL_OPTIONS_OVERRIDE">
      <module
              name="guard.plugin.main"
              options="--add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
                      --add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED
                      --add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED
                      --add-exports jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED" />
    </option>
  </component>
```

This seems to be an issue with intellij not recognizing that we'll have access to these classes, as we're not
setting this up as a normal dependency. Stuff works just fine without this intellij configuration, but it's unpleasant
to work with. I'm looking for a better way to set this up, but I'm hesitant to take this off the gitignore because I
read that intellij will tend to try to automatically overwrite this file a lot. if you have a better solution, feel
free to let me know!


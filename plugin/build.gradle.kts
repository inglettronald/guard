plugins {
    id("java")
}

sourceSets {
    test.configure {
        java {
            srcDir("src/test/java")
        }
        resources {
            srcDir("src/test/resources")
        }
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

// Allows us to use Guard annotations in our test framework.
dependencies {
    implementation(project(":"))
    "testImplementation"(sourceSets.main.get().output)
}

// Allows us to use all the javac goodies.
tasks.withType(JavaCompile::class) {
    val module = "ALL-UNNAMED"
    options.compilerArgs.addAll(listOf(
        "--add-exports=jdk.compiler/com.sun.tools.javac.util=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.comp=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.tree=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.api=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.code=$module",
    ))
    options.forkOptions.jvmArgs!!.addAll(listOf(
        "--add-exports=jdk.compiler/com.sun.tools.javac.util=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.comp=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.tree=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.api=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.code=$module",
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=6999"
    ))
    options.isFork = true
}

// Task in charge of running tests.
tasks.register<JavaExec>("runTestingWithDebug") {
    group = "application"
    description = "Runs Testing.main with debugger attached (to the compiler plugin)"
    classpath = sourceSets.test.get().runtimeClasspath
    workingDir = rootProject.projectDir
    mainClass.set("guard_plugin.Testing")
}

// Combined task for building and debugging
tasks.register("buildAndDebug") {
    group = "application"
    description = "Builds and runs with debugger"
    dependsOn("clean", "compileJava", "runTestingWithDebug")
}

// Task to kill processes using debug port
tasks.register("killDebugPort") {
    doLast {
        val port = 6999
        val osName = System.getProperty("os.name").lowercase()

        if (osName.contains("windows")) {
            exec {
                // TODO: verify this
                commandLine("cmd", "/c", "for /f \"tokens=5\" %a in ('netstat -ano ^| findstr :$port') do taskkill /F /PID %a")
            }
        } else {
            exec {
                // TODO: This is not sound if you install intellij through flatpak by the looks of things?
                //  If you're investigating an issue here it seems like the intellij terminal sometimes can't find
                //  these commands. If you have a good solution to this feel free to let me know.
                commandLine("sh", "-c", "ss -ptln | awk 'match (\$4, /:6999\$/) && match (\$6, /users:\\(\\(\"java\",pid=([0-9]+),/, a) { print a[1] }' | xargs kill")
            }
        }

        println("Killed any processes using port $port")
    }
}
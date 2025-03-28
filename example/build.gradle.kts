plugins {
    id("java")
}

dependencies {
    implementation(project(":"))
    annotationProcessor(project(":plugin"))
}

tasks.withType<JavaCompile> {
    val module = "ALL-UNNAMED"
    options.forkOptions.jvmArgs!!.addAll(listOf(
        "--add-exports=jdk.compiler/com.sun.tools.javac.util=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.comp=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.tree=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.api=$module",
        "--add-exports=jdk.compiler/com.sun.tools.javac.code=$module",
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:7000"
    ))
    options.isFork = true
    dependsOn(tasks.clean)
}

tasks.register("stop") {
    mustRunAfter(tasks.classes, tasks.build, tasks.compileJava)
    doLast {
        println("Goodbye")
        Runtime.getRuntime().exit(0)
    }
}

// Properly defined JavaExec task for Kotlin DSL
tasks.register<JavaExec>("runExampleWithDebug") {
    group = "application"
    description = "Runs Example.main with debugger attached"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("com.dulkir.guard_example.Example")

    // Debug configuration
    debug = true
    jvmArgs = listOf(
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"
    )
}

// Combined task for building and debugging
tasks.register("buildAndDebug") {
    group = "application"
    description = "Builds and runs with debugger"
    dependsOn("clean", "compileJava", "runExampleWithDebug", "killDebugPort")
}

// Task to kill processes using debug port
tasks.register("killDebugPort") {
    doLast {
        val port = 5005
        val osName = System.getProperty("os.name").lowercase()

        if (osName.contains("windows")) {
            exec {
                commandLine("cmd", "/c", "for /f \"tokens=5\" %a in ('netstat -ano ^| findstr :$port') do taskkill /F /PID %a")
            }
        } else {
            exec {
                commandLine("sh", "-c", "lsof -ti:$port | xargs kill -9 || true")
            }
        }

        println("Killed any processes using port $port")
    }
}
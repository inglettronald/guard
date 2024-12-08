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
    options.isFork = true;
    dependsOn(tasks.clean)
}
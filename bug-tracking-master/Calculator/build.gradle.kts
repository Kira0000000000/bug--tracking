plugins {
    java
}

group = "radik.prog"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    compile(group="org.apache.commons", name="commons-lang3", version="3.8.1")
}

buildDir=File("target")

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
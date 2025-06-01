plugins {
    id("java")
//    id("org.jetbrains.kotlin.jvm") version "1.9"
    id("com.github.gmazzo.buildconfig") version "5.6.5"
}

group = "io.github.Tors_0"
version = "1.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
}

@Suppress("INLINE_FROM_HIGHER_PLATFORM")
buildConfig {
    buildConfigField("APP_NAME", project.name)
    buildConfigField("APP_VERSION", provider { "${project.version}" })
    buildConfigField("BUILD_TIME", System.currentTimeMillis())
}


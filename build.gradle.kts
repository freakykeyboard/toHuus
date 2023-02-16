val ktorVersion: String by project
val serializationVersion: String by project
val kmongoVersion: String by project
val isProduction = true
plugins {
    kotlin("multiplatform") version "1.8.0"
    application
    kotlin("plugin.serialization") version "1.8.0"
}

group = "tech.berndlorenzen"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        jvmToolchain(8)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
            runTask {
                devServer = org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 8081,
                    proxy = mutableMapOf("*" to "http://localhost:8080"),
                )
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
                implementation("io.ktor:ktor-server-call-id:$ktorVersion")
                implementation("io.ktor:ktor-server-auth:$ktorVersion")
                implementation("io.ktor:ktor-server-sessions:$ktorVersion")
                implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:7.18.0")
                implementation("org.kodein.di:kodein-di:7.8.0")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongoVersion")
                implementation("org.litote.kmongo:kmongo:$kmongoVersion")
                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:2.0.2")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-test-host:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation(kotlin("test"))
            }
        }
        val jsMain by getting {
            dependencies {

                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.346")
            }
        }
        val jsTest by getting
    }
}

application {
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
    mainClass.set("tech.berndlorenzen.application.ServerKt")
}

tasks {
    if (System.getenv("DEVELOPMENT") == null) {
        named<Copy>("jvmProcessResources") {
            // Integrate JS application bundle into the backend's resources (for production builds only,
            // as the development bundle will be served by webpack-dev-server).

            val jsBrowserProductionWebpack by getting(org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack::class) {
                // WORKAROUND:
                // jsBrowserProductionWebpack output directory is deleted by a subsequent execution of
                // jsBrowserProductionExecutableDistributeResources, which uses the same output directory.
                outputs.dir(destinationDirectory)
            }

            dependsOn(jsBrowserProductionWebpack)
            inputs.dir(jsBrowserProductionWebpack.destinationDirectory)
            from(jsBrowserProductionWebpack.destinationDirectory)
        }
    }
}
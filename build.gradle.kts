plugins {
    java
    jacoco
    `maven-publish`
}
repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.mycelium.bom))
    implementation(libs.minestom)
    testImplementation(platform(libs.mycelium.bom))
    testImplementation(libs.junit.api)
    testImplementation(libs.junit.platform.launcher)
    testRuntimeOnly(libs.junit.engine)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
    withJavadocJar()

}

tasks {
    test {
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        version = rootProject.version as String
        artifactId = "vulpes"
        groupId = rootProject.group as String
        pom {
            name = "Vulpes"
            description = "Vulpes for OneLiteFeather"
            url = "https://github.com/OneLiteFeatherNET/vulpes"
            licenses {
                license {
                    name = "AGPL-3.0"
                    url = "https://www.gnu.org/licenses/agpl-3.0.en.html"
                }
            }
            developers {
                developer {
                    id = "themeinerlp"
                    name = "Phillipp Glanz"
                    email = "p.glanz@madfix.me"
                }
                developer {
                    id = "theEvilReaper"
                    name = "Steffen Wonning"
                    email = "steffenwx@gmail.com"
                }
                scm {
                    connection = "scm:git:git://github.com:OneLiteFeatherNET/vulpes.git"
                    developerConnection = "scm:git:ssh://git@github.com:OneLiteFeatherNET/vulpes.git"
                    url = "https://github.com/OneLiteFeatherNET/vulpes"
                }
            }
        }

        repositories {
            maven {
                authentication {
                    credentials(PasswordCredentials::class) {
                        // Those credentials need to be set under "Settings -> Secrets -> Actions" in your repository
                        username = System.getenv("ONELITEFEATHER_MAVEN_USERNAME")
                        password = System.getenv("ONELITEFEATHER_MAVEN_PASSWORD")
                    }
                }

                name = "OneLiteFeatherRepository"
                val releasesRepoUrl = uri("https://repo.onelitefeather.dev/onelitefeather-releases")
                val snapshotsRepoUrl = uri("https://repo.onelitefeather.dev/onelitefeather-snapshots")
                url = if (version.toString().contains("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            }
        }
    }
}
import java.io.File
import java.net.URL
import java.security.MessageDigest

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val sherpaVersion = "1.13.8"
val sherpaAarName = "sherpa-onnx-" + sherpaVersion + ".aar"
val sherpaAar = layout.projectDirectory.file("libs/" + sherpaAarName).asFile
val sileroModelName = "silero_vad_v5.onnx"
val sileroModel = layout.projectDirectory.file("src/main/assets/" + sileroModelName).asFile

fun downloadPinnedArtifact(url: String, target: File, expectedSha256: String) {
    fun sha256(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        file.inputStream().use { input ->
            val buffer = ByteArray(64 * 1024)
            while (true) {
                val count = input.read(buffer)
                if (count < 0) break
                digest.update(buffer, 0, count)
            }
        }
        return digest.digest().joinToString("") { byte -> "%02x".format(byte) }
    }

    if (target.isFile && sha256(target) == expectedSha256) return

    target.parentFile.mkdirs()
    val temporary = File(target.parentFile, target.name + ".download")
    temporary.delete()
    URL(url).openStream().use { input ->
        temporary.outputStream().use { output -> input.copyTo(output) }
    }
    val actualSha256 = sha256(temporary)
    check(actualSha256 == expectedSha256) {
        "SHA-256 mismatch for " + target.name + ": " + actualSha256
    }
    check(temporary.length() > 0L) { "Downloaded artifact is empty: " + target.name }
    check(!target.exists() || target.delete()) { "Cannot replace " + target.absolutePath }
    check(temporary.renameTo(target)) { "Cannot move downloaded artifact to " + target.absolutePath }
}

val prepareFeature31Runtime = tasks.register("prepareFeature31Runtime") {
    outputs.files(sherpaAar, sileroModel)
    outputs.upToDateWhen { false }
    doLast {
        downloadPinnedArtifact(
            "https://github.com/k2-fsa/sherpa-onnx/releases/download/v" + sherpaVersion + "/sherpa-onnx-" + sherpaVersion + ".aar",
            sherpaAar,
            "633c24321e06b1fe79feafa03ea16cbc0f8a286641e2da3559bac91bdb13bd96"
        )
        downloadPinnedArtifact(
            "https://github.com/k2-fsa/sherpa-onnx/releases/download/asr-models/" + sileroModelName,
            sileroModel,
            "6b99cbfd39246b6706f98ec13c7c50c6b299181f2474fa05cbc8046acc274396"
        )
    }
}

tasks.matching { it.name == "preBuild" }.configureEach {
    dependsOn(prepareFeature31Runtime)
}

android {
    namespace = "com.rider.barge_in"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
    }

    sourceSets.getByName("main") {
        java.srcDirs(
            "src/main/kotlin",
            "../voice/continuous_listening/sherpa_onnx",
            "../voice/user_speech_stops_tts/sherpa_onnx"
        )
        manifest.srcFile("src/main/AndroidManifest.xml")
        assets.srcDir("src/main/assets")
    }

    sourceSets.getByName("test") {
        java.srcDir("src/test/kotlin")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

val webRtcAar = file("libs/libwebrtc_arm64.aar")

dependencies {
    implementation(files(sherpaAar))
    implementation("androidx.core:core:1.15.0")

    // Keep the existing optional Feature #22 binary dependency when supplied locally.
    if (webRtcAar.isFile) implementation(files(webRtcAar))

    testImplementation("junit:junit:4.13.2")
}

val composeVersion by extra { "1.5.4" }
val composeCompilerVersion by extra { "1.5.7" }
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    id("com.google.devtools.ksp") version "1.9.21-1.0.15" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("androidx.navigation.safeargs") version "2.7.6" apply false
}
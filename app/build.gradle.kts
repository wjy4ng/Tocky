plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.cookandroid.mobile_project"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cookandroid.mobile_project"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES") // 충돌하는 파일 제외
        exclude("META-INF/LICENSE") // 충돌하는 라이센스 파일 제외
        exclude("META-INF/LICENSE.txt") // 추가적인 충돌 파일 제외
        exclude("META-INF/NOTICE") // NOTICE 파일 제외
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.inappmessaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.zxing.android.embedded)
    implementation(libs.core)
    implementation("dev.samstevens.totp:totp:1.6.1")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
}